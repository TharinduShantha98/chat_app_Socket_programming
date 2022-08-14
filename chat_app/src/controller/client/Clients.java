package controller.client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Clients {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public  String username;



    public Clients(Socket socket, String username) {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            System.out.println(username);
            System.out.println(socket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
            closeEverThing(socket,bufferedReader,bufferedWriter);
        }

    }


    public void sendMessage(String messageToSend){

        try{
            System.out.println(this.username);
//            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();


            bufferedWriter.write(username+": " + messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();


                //System.out.println(messageToSend);
                //System.out.println("socket is connected");






        } catch (IOException e) {
            e.printStackTrace();
            closeEverThing(socket,bufferedReader,bufferedWriter);
        }


    }



    public  void listenForMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {



                while (socket.isConnected()){


                    try {
                         String messageFormGroupChat = bufferedReader.readLine();
                        /*System.out.println(messageFormGroupChat);
                        System.out.println("meka harii palayan yako");*/

                        if(messageFormGroupChat != null){
                            ClientOneFormController.addLabel(messageFormGroupChat,vBox);

                        }else {
                            ClientOneFormController.addLabel("start",vBox);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
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
