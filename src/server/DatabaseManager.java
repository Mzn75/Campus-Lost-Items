package server;

import models.CampusItem;
import java.io.*;
import java.util.ArrayList;

public class DatabaseManager {

    // Saves the ArrayList of item's data directly into a file
    public static void saveToFile(ArrayList<CampusItem> list, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {

            out.writeObject(list);
            System.out.println("Data successfully saved to " + filename);

        } catch (IOException e) {

            System.err.println("Failed to save to file: " + e.getMessage());

        }
    }

    // Loads the file back into an ArrayList
    @SuppressWarnings("unchecked")
    public static ArrayList<CampusItem> loadFromFile(String filename) {
        File file = new File(filename);

        // If the server is running for the first time
        if (!file.exists()) {

            System.out.println(filename + " not found. Creating...");
            return new ArrayList<>();

        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

            System.out.println("Successfully loaded data from " + filename);
            return (ArrayList<CampusItem>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("Failed to load from file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list so the server doesn't crash

        }
    }

}
