package client;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class DictionaryClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int serverPort = 1234;

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Example commands to test the server
            out.println("ADD hello world");
            System.out.println(in.readLine());

            out.println("QUERY hello");
            System.out.println(in.readLine());

            out.println("REMOVE hello");
            System.out.println(in.readLine());

            out.println("QUERY hello");
            System.out.println(in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

