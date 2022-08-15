package controller.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public  static ArrayList<ClientHandler> clientHandlers =  new ArrayList<>();
    private  Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private  String clientUserName;
    private int port;


    public ClientHandler(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //client user name implement
            this.clientUserName = bufferedReader.readLine();
          //  this.clientUserName = Clients.username;

            System.out.println(socket.getPort());
            this.port = socket.getPort();

            clientHandlers.add(this);


            //broadcastMessage("SERVER" + clientUserName +" has entered the chat" );
            System.out.println("methnanta awa");
            System.out.println(this.clientUserName);


        } catch (IOException e) {

            closeEverThing(socket,bufferedReader,bufferedWriter);
            e.printStackTrace();

        }

    }

    @Override
    public void run() {

        String messageFromClient;

        while (socket.isConnected()){

            try {

                messageFromClient  =  bufferedReader.readLine();
                //System.out.println(messageFromClient);
                broadcastMessage(messageFromClient);

            } catch (IOException e) {
                closeEverThing(socket,bufferedReader,bufferedWriter);
                e.printStackTrace();
               // break;
            }


        }

    }

    private void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers){

            try {

                //clientUserName
                if(clientHandler.port != port){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();

               }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


    public void removeClientHandler(){
            clientHandlers.remove(this);
            broadcastMessage("SERVER " + clientUserName+ "has left chat");



    }








    public void closeEverThing(Socket socket , BufferedReader bufferedReader, BufferedWriter bufferedWriter){

        removeClientHandler();

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
