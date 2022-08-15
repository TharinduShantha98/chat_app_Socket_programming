package controller.server;

import controller.client.ClientHandler;
import controller.client.ImageHandler;
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
    int charactersLength;


    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        System.out.println("server is created");
        /*this.socket = serverSocket.accept();
            System.out.println("client is connected");
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));*/

    }




    public void  startServer(){

        try{

            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort());
                System.out.println("A new Client has a connected!");

              /*  InputStream inputStream = socket.getInputStream();
                int read = inputStream.read();
*/
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

                 /*ImageHandler imageHandler = new ImageHandler(socket);
                 Thread thread1 = new Thread(imageHandler);
                 thread1.start();*/

            }


        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void startServer2(){


        new Thread(()->{
            ImageHandler imageHandler = new ImageHandler(socket);
            try {
                imageHandler.receivedImageFormClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }


    public void closeServerSocket(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


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
