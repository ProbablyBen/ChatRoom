package com.ben.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static Socket client;
    private static Scanner reader;
    private static Scanner inputStream;

    private static PrintStream printStream;
    public static PrintStream getPrintStream(){
        return printStream;
    }
    private static GUI gui;

    public static GUI getGUI(){
        return gui;
    }


    public static void main(String[] args) {

        createGUI();
        setupConfig();
        setupClient();

        String statusCode;

        do {
            getGUI().getChatLog().append("[Client] Enter your username:\n");
            // Wait for a response code indicating whether
            // our username was not taken.
            // Server will respond with a response code once
            // the user sends a message with the username that
            // they desire.
            statusCode = inputStream.nextLine();

        } while (!statusCode.equals(Code.SuccessfulRegistration.toString()));

        new Thread(() -> new Listener(client, inputStream)).start();
    }

    public static void setupConfig() {
        try {

            getGUI().getChatLog().append("[Client] Loading config...\n");
            Config.loadConfig();
            getGUI().getChatLog().append("[Client] Config loaded.\n");

        } catch (Exception e) {
            getGUI().getChatLog().append("[Client] Failed to load config.\n");
            getGUI().getChatLog().append("[Client] Exiting.\n");
            System.exit(-1);
        }

    }

    public static void setupClient() {
        try {
            client = new Socket(Config.ip, Config.port);
            reader = new Scanner(System.in);
            inputStream = new Scanner(client.getInputStream());
            printStream = new PrintStream(client.getOutputStream());
            // Send ack message to server to verify that we are the client.
            printStream.println(Code.AcknowledgeMessage.toString());
        } catch (IOException e) {
            getGUI().getChatLog().append("[Client] Failed to setup client.\n");
            getGUI().getChatLog().append("[Client] Exiting in 5 seconds...");
            try{
                Thread.sleep(5000);
                System.exit(-1);
            } catch (InterruptedException ev){
                getGUI().getChatLog().append("[Client] Was not able to exit, please manually exit.");
            }
            e.printStackTrace();
        }
    }

    public static void createGUI(){
        gui = new GUI();
    }

    public static void reconnect(){
        setupClient();
        setupClient();
        String statusCode;
        do {
            getGUI().getChatLog().append("[Client] Enter your username:\n");
            // Wait for a response code indicating whether
            // our username was not taken.
            // Server will respond with a response code once
            // the user sends a message with the username that
            // they desire.
            statusCode = inputStream.nextLine();

        } while (!statusCode.equals(Code.SuccessfulRegistration.toString()));

        new Thread(() -> new Listener(client, inputStream)).start();
    }
}
