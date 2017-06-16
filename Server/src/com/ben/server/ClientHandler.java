package com.ben.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    public String username;
    private Socket clientSocket;
    private Scanner in;
    private PrintStream printStream;
    private Date lastMessageSentTime;


    public boolean isRegistered;

    public ClientHandler(Socket clientSocket) {
        Date date = new Date();
        date.setTime(date.getTime() - 1000L);
        this.lastMessageSentTime = date;

        this.username = "";
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            in = new Scanner(clientSocket.getInputStream());
            printStream = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nextLine;
        try {
            do {
                // Client will prompt for a username
                username = in.nextLine();
                isRegistered = Main.registerName(username);
                log(isRegistered ? ("Username " + username + " was registered.") : ("Attempted user registration of \"" + username + "\" failed because user is already logged in."));
                if(!isRegistered)
                    printStream.println(Code.UnsuccessfulRegistration.toString());

            } while (!isRegistered);

            printStream.println(Code.SuccessfulRegistration.toString()); // Username successfully registered.

            printStream.println("[Server] Welcome to Ben's chat server.");
            printStream.println("[Server] Type \"/exit\" to exit.");
            Main.broadcastMessageAllRegisteredClients("[Server] " + username + " has joined the server.");

            boolean needsBroadcast;

            while (true) {
                needsBroadcast = true;
                nextLine = in.nextLine();
                log(username + " : " + nextLine);
                if (nextLine.equalsIgnoreCase("/exit")) {
                    printStream.println("[Server] " + username + " has disconnected from the server.");
                    clientSocket.close();
                    break;
                } else if(nextLine.startsWith("/")){
                    CommandHandler.handleCommand(username, printStream, nextLine);
                    needsBroadcast = false;
                }
                if(needsBroadcast){
                    Date currentMessageTime = new Date();
                    currentMessageTime.setTime(new Date().getTime() - 500L); // .5 second interval between messages per user.
                    if(nextLine.length() > 500){
                        printStream.println(Code.MessageTooLong.toString());
                    } else if (lastMessageSentTime.after(currentMessageTime)){
                        log(username + " is sending messages too quickly!");
                        printStream.println("[Server] Hey, it appears you are sending messages too quickly. Please wait half a second(500ms) before sending another message.");
                    }
                    else {
                        Main.broadcastMessageAllRegisteredClients("[" + username + "] > " + nextLine);
                        lastMessageSentTime = new Date();
                    }
                }
            }

        } catch (NoSuchElementException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Main.names.remove(username);
            Main.clients.remove(this);
            Main.broadcastMessageAllRegisteredClients("[Server] " + username + " has disconnected from the server.");
            log("Client " + username + " has disconnected.");
        }
    }

    public void write(String str) {
        printStream.println(str);
    }

    public void log(String msg) {
        System.out.println("[Logger] [" + clientSocket.getInetAddress()+ "] " + msg);
    }


}

