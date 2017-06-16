package com.ben.client;

import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ben on 5/9/17.
 */
public class Listener {

    private Scanner inputStream;
    private Socket s;
    private String username;

    public Listener(Socket s, Scanner inputStream) {
        this.s = s;
        this.inputStream = inputStream;
        listen();
    }

    public void listen() {

        while (true) {
            try {
                String output;
                if (inputStream.hasNextLine()){
                    output = inputStream.nextLine();

                    if(output.equals(Code.MessageTooLong.toString())){
                        Main.getGUI().getChatLog().append("[Client] Sorry, your message exceeded the maximum character limit of 500 characters.");
                    } else {
                        Main.getGUI().getChatLog().append(output + "\n");
                    }


                }
            } catch (Exception e) {
                Main.getGUI().getChatLog().append("[Client] An exception was thrown in the listener.\nDisconnecting.\n");
            }
        }
    }


}
