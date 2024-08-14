package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import shared.Dictionary;
import shared.MessageProtocol;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Dictionary dictionary;

    public ClientHandler(Socket socket, Dictionary dictionary) {
        this.clientSocket = socket;
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String response = MessageProtocol.processRequest(request, dictionary);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
