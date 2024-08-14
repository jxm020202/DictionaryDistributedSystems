package server;


import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import shared.Dictionary;


public class DictionaryServer {
    private static final int PORT = 1234;
    private Dictionary dictionary;
    private ExecutorService pool;

    public DictionaryServer() {
        dictionary = new Dictionary();
        pool = Executors.newFixedThreadPool(4);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Dictionary Server is running on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket, dictionary));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DictionaryServer server = new DictionaryServer();
        server.start();
    }
}

