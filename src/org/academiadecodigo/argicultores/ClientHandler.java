package org.academiadecodigo.argicultores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private String name;
    private PrintWriter output;
    private BufferedReader input;
    private LinkedList<ClientHandler> list;


    public ClientHandler(Socket clientSocket, LinkedList list){
        this.clientSocket = clientSocket;
        this.list = list;
        name = "user " + list.size() + " is connected";

        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(name + " is thread number " + Thread.currentThread().getName());
        while(true){
            try {
                String line = input.readLine();
                sendMessageToAll(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message){
        output.println(message);
        output.flush();
    }
    private void sendMessageToAll(String message){

        for(ClientHandler c : list){
            if(!c.equals(this)) {
                c.sendMessage(message);
            }
        }
    }

}
