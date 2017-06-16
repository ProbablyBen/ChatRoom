package com.ben.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<ClientHandler> clients;
    public static ArrayList<String> names;
    private static Scanner in;
    private static PrintStream printStream;

    static {
        names = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            System.out.println("[Server] Initializing.");
            int port = 8443;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Listening for sockets on port " + port + ".");
            while (true) {
                System.out.println();

                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted a new connection.");

                in = new Scanner(clientSocket.getInputStream());
                printStream = new PrintStream(clientSocket.getOutputStream());

                if (in.nextLine().equals(Code.AcknowledgeMessage.toString())) {
                    System.out.println("Client Details: ");
                    System.out.format("\tInternet Address: %s:%s\n", clientSocket.getInetAddress(), clientSocket.getPort());
                    ClientHandler handler = new ClientHandler(clientSocket);
                    clients.add(handler);
                    Thread t = new Thread(handler);
                    t.start();
                } else {
                    printStream.println("http/1.1 403 forbidden"); // This stops most people from a web browser from connecting.
                    clientSocket.close();
                    System.out.println("Closed. Not on official client.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void broadcastMessageAllRegisteredClients(String msg) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.isRegistered) {
                clientHandler.write(msg);
            }
        }
    }

    public static boolean registerName(String name) {
        name = name.trim();
        if(name.length() > 15 || name.length() < 1){
            return false;
        }
        for (String s : names) {
            s = s.trim();
            if (s.equalsIgnoreCase(name)) {
                return false;
            }
        }
        names.add(name);
        return true;
    }

}
