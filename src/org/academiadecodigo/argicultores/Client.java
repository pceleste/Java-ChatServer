package org.academiadecodigo.argicultores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        Client client = new Client();

    }

    private Socket clientSocket = null;
    private PrintWriter output;
    private BufferedReader input;

    public Client(){
        start();
    }

    public void start(){
        String host = "";
        int port = 0;
        System.out.println("Hostmane");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            host = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Port");
        try {
            port = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectToServer(host, port);
        msgConsoleToServer(in);
    }

    public void connectToServer(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            output = new PrintWriter(clientSocket.getOutputStream(), true); //ESCREVER PARA O SERVIDOR
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // LER DO SERVIDOR
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new ReadMessage());
        thread.start();

    }

    private void msgConsoleToServer(BufferedReader in){
        System.out.println("TEST 1");

        while (true){
            try {
                String message = in.readLine();
                output.println(message);
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReadMessage implements Runnable {

        @Override
        public void run() {
            System.out.println("TEST");
            while (true) {

                try {
                    String message = input.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
