package controller.client;

import controller.server.ServerFormController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        } catch (IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
        }


    }

    public void sendMessageToServer(String messageToServer){

        try {

            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();


        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending to the  client ");
        }

    }


    public void receiveMessageFromServer(VBox vBox){

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (socket.isConnected()){
                    try {
                        String messageFormClient = bufferedReader.readLine();
                        ServerFormController.addLabel(messageFormClient, vBox);



                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message form  client ");
                        closeEverThing(socket,bufferedReader,bufferedWriter);



                    }
                }

            }
        }).start();


    }


    public void closeEverThing(Socket socket , BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        if(bufferedReader != null){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null){
            try {
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }










}
