package controller.server;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

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

                        String location = receivedImageFormClient();
                        ServerFormController.GetImageForDisplay(location, vBox);
//                        if(location != null){
//
//
//                        }else{
//                            String messageFormClient = bufferedReader.readLine();
//                            ServerFormController.addLabel(messageFormClient, vBox);
//                        }







//                        String messageFormClient = bufferedReader.readLine();
//
//                        if(messageFormClient != null){
//                            ServerFormController.addLabel(messageFormClient, vBox);
//
//
//                        }else{
//                            String location = receivedImageFormClient();
//                            ServerFormController.GetImageForDisplay(location, vBox);
//
//
//
//                        }






                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message form  client ");
                        closeEverThing(socket,bufferedReader,bufferedWriter);



                    }
                }

            }
        }).start();


    }




    public String  receivedImageFormClient() throws IOException {

        InputStream inputStream = socket.getInputStream();
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        boolean write = ImageIO.write(image, "jpg", new File("src/assets/client/test2.jpg"));
        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());

        if(write){
           return "src/assets/client/test2.jpg";
        }

       return null;


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
