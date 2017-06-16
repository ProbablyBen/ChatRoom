package com.ben.client;

import java.io.*;

public class Config {

    public static String ip;
    public static int port;

    static {
        ip = null;
        port = 1500;
    }

    public static void loadConfig() throws IOException {
        FileReader fileReader;
        try {

            fileReader = new FileReader(new File("config.ini"));
            BufferedReader in = new BufferedReader(fileReader);
            ip = in.readLine();
            port = Integer.parseInt(in.readLine());

        } catch (FileNotFoundException e) {
            Main.getGUI().getChatLog().append("[Client] Unable to locate the configuration file.\n");
            createDefaultConfigFile();
            System.err.println(e.getMessage());
        } catch (IOException e) {
            Main.getGUI().getChatLog().append("[Client] Unable to read the configuration file.\n");
            createDefaultConfigFile();
        } catch (NumberFormatException e) {
            Main.getGUI().getChatLog().append("[Client] The configuration file provided an invalid port.\n");
            createDefaultConfigFile();
        }
    }

    public static void createDefaultConfigFile() throws IOException {
        Main.getGUI().getChatLog().append("[Client] Creating a default configuration file.");
        File configFile = new File("config.ini");
        BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
        writer.write("127.0.0.1");
        writer.newLine();
        writer.write("8443");
        writer.newLine();
        writer.close();
    }
}
