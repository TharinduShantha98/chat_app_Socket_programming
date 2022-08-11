package controller.server;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter  bufferedWriter;


    public Server(ServerSocket serverSocket) {

        try {


            this.serverSocket = serverSocket;
            System.out.println("server is created");

            this.socket = serverSocket.accept();
            System.out.println("client is connected");
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        } catch (IOException e) {
            System.out.println("Error creating server");
            e.printStackTrace();
        }

    }





    public void sendMessageToClient(String messageToClient){

        try {

            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();


        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending to the  client ");
        }


    }

    public void receiveMessageFromClient(VBox vBox){

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
