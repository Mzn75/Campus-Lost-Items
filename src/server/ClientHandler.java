package server;

//Import Classes
import models.CampusItem;
import models.NetworkRequest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    //Instance Variables
    private Socket clientSocket;
    private ArrayList<CampusItem> lostItems;
    private ArrayList<CampusItem> foundItems;

    //Constructor
    public ClientHandler(Socket clientSocket, ArrayList<CampusItem> lostItems, ArrayList<CampusItem> foundItems) {

        this.clientSocket = clientSocket;
        this.lostItems = lostItems;
        this.foundItems = foundItems;

    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Reading Request from client...");

            //Opening
            NetworkRequest request = (NetworkRequest) in.readObject();
            String action = request.getAction();
            CampusItem item = request.getData();

            System.out.println("Received Action: " + action);

            //Generating an ID
            int newId = generateNextId();
            item.setItemID(newId);
            System.out.println("Assigned new ID: " + newId);

            //Processing
            if (action.equalsIgnoreCase("REPORT_LOST")) {

                lostItems.add(item);

                DatabaseManager.saveToFile(lostItems, "lost_database.dat");

                // Check for matches
                String responseMessage = checkForMatches(item, foundItems, "Found");
                out.writeObject(responseMessage);

            } else if (action.equalsIgnoreCase("REPORT_FOUND")) {

                foundItems.add(item);

                DatabaseManager.saveToFile(foundItems, "found_database.dat");

                // Check for matches
                String responseMessage = checkForMatches(item, lostItems, "Lost");
                out.writeObject(responseMessage);

            } else {
                out.writeObject("Error: Unknown command.");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client connection error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Loop through a list to see if there are any matches
    private String checkForMatches(CampusItem newItem, ArrayList<CampusItem> listToCheck, String listType) {

        for (CampusItem existingItem : listToCheck) {

            int score = newItem.calculateMatchScore(existingItem);
            System.out.println("📊 Server evaluated a Match Score of: " + score);
            if (score >= 80) {
                return "MATCH FOUND! An item matching your description is in the " + listType + " database. \n Contact: " + existingItem.getContactPhone();
            }
        }
        return "Item successfully saved to the database.";
    }

    private int generateNextId() {
        int maxId = 99; //First item ID is 100

        // Check the Lost database
        for (CampusItem item : lostItems) {
            if (item.getItemID() > maxId) {
                maxId = item.getItemID();
            }
        }

        // Check the Found database
        for (CampusItem item : foundItems) {
            if (item.getItemID() > maxId) {
                maxId = item.getItemID();
            }
        }

        // Return the highest number found + 1
        return maxId + 1;
    }

}