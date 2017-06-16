package com.ben.server;


import java.io.PrintStream;
import java.util.ArrayList;

public class CommandHandler {

    private static ArrayList<String> commands;
    private static ArrayList<String> syntax;
    static{
        commands = new ArrayList<>();
        syntax = new ArrayList<>();

        commands.add("/list");
        syntax.add("Lists all clients currently connected to the server.");

        commands.add("/help");
        syntax.add("Displays this command.");

        commands.add("/exit");
        syntax.add("Disconnected you from the server.");

    }

    public static void handleCommand(String username, PrintStream printStream, String nextLine) {

        switch (nextLine) {
            case "/list": {
                String[] clients = getClients();
                printStream.println("[Server] Clients Connected:");
                for (int i = 1; i <= clients.length; i++) {
                    printStream.println("\t" + i + ") " + clients[i - 1]);
                }
                break;
            }
            case "/help": {
                for(int i = 0; i < commands.size(); i++){
                    printStream.println("Command: " + commands.get(i) + " - " + syntax.get(i));
                }
                break;
            }
            default:{
                printStream.println("[Server] Unknown command.");
            }
        }
    }

    private static String[] getClients() {
        String[] clientArr = new String[Main.clients.size()];
        for (int i = 0; i < clientArr.length; i++) {
            clientArr[i] = String.valueOf(Main.clients.get(i).username);
        }
        return clientArr;
    }

}
