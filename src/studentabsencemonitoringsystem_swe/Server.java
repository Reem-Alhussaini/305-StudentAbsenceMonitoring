package studentabsencemonitoringsystem_swe;

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
            boolean transmissionEnded = false; // Flag to track whether transmission has ended
            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                while (!transmissionEnded) {
                    try {
                        int rating = in.readInt();
                        serverGUI.appendLog("Received rating from " + clientSocket.getInetAddress().getHostAddress() + ": " + rating);
                        out.writeObject("Rating received successfully!");
                        out.flush();

                        String suggestion = (String) in.readObject();
                        serverGUI.appendLog("Received suggestion from " + clientSocket.getInetAddress().getHostAddress() + ": " + suggestion);
                        out.writeObject("Suggestion received successfully!");
                        out.flush();

                        // Check for the "END" message to close the connection
                        String endMessage = (String) in.readObject();
                        if (endMessage.equals("END")) {
                            serverGUI.appendLog("End of transmission received from " + clientSocket.getInetAddress().getHostAddress());
                            transmissionEnded = true; // Set the flag to indicate end of transmission
                        }
                    } catch (SocketException e) {
                        serverGUI.appendLog("SocketException: Client disconnected unexpectedly: " + clientSocket.getInetAddress().getHostAddress());
                        break; // Exit the loop to stop processing the client connection
                    } catch (IOException | ClassNotFoundException e) {
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

