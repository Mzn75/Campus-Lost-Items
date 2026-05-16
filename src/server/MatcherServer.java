package server;

import models.CampusItem;

import java.util.Collections;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MatcherServer {

    // Instead of starting with empty lists, we ask the DatabaseManager to load the files!
    public static ArrayList<CampusItem> lostItems = new ArrayList<>(Collections.synchronizedList(DatabaseManager.loadFromFile("lost_database.dat")));

    public static ArrayList<CampusItem> foundItems = new ArrayList<>(Collections.synchronizedList(DatabaseManager.loadFromFile("found_database.dat")));

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        int port = 7500;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is online, Port: " + port + "...");

            do {

                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                ClientHandler clientWorker = new ClientHandler(clientSocket, lostItems, foundItems);

                new Thread(clientWorker).start();

            } while (true);

        } catch (IOException e) {
            System.err.println("SERVER ERROR: " + e.getMessage());
        }
    }

}
