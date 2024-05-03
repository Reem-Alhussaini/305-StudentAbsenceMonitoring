/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentabsencemonitoringsystem_swe;

/**
 *
 * @author a
 */
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server implements Runnable {
    private ServerSocket serverSocket;
    private GUI_Server serverGUI;

    public Server(GUI_Server serverGUI) {
        this.serverGUI = serverGUI;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(8189);
            serverGUI.appendLog("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                serverGUI.appendLog("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread for each client connection
                Thread clientThread = new Thread(new ClientHandler(clientSocket, serverGUI));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }

    // Nested class for handling client connections
    class ClientHandler implements Runnable {
        private Socket clientSocket;
        private GUI_Server serverGUI;

        public ClientHandler(Socket socket, GUI_Server serverGUI) {
            this.clientSocket = socket;
            this.serverGUI = serverGUI;
        }

        
        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {
                    try {
                        int rating = in.readInt();
                        serverGUI.appendLog("Received rating from " + clientSocket.getInetAddress().getHostAddress() + ": " + rating);
                        out.writeObject("Rating received successfully!");
                        out.flush();

                        String suggestion = (String) in.readObject();
                        serverGUI.appendLog("Received suggestion from " + clientSocket.getInetAddress().getHostAddress() + ": " + suggestion);
                        out.writeObject("Suggestion received successfully!");
                        out.flush();
                    } catch (EOFException | SocketException e) {
                        // Handle the EOFException (client closed the connection unexpectedly)
                        serverGUI.appendLog("Client disconnected unexpectedly: " + clientSocket.getInetAddress().getHostAddress());
                        break; // Exit the loop to stop processing the client connection
                    } catch (IOException e) {
                        // Handle other IOExceptions
                        e.printStackTrace();
                        break; // Exit the loop to stop processing the client connection
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break; // Exit the loop to stop processing the client connection
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    serverGUI.appendLog("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

