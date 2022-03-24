package org.academiadecodigo.argicultores;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
    }

    private ServerSocket serverSocket;
    private ClientHandler clientHandler;
    private LinkedList<ClientHandler> list;

    public Server(){
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list = new LinkedList<>();
        start();
    }

    private void start(){
        System.out.println("TEST START SERVER");
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                clientHandler = new ClientHandler(clientSocket, list);
                list.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
