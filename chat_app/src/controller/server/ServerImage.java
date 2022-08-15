package controller.server;

import controller.client.ImageHandler;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerImage {

    private  ServerSocket serverSocket1;
    private Socket socket1;


    public ServerImage(ServerSocket serverSocket1) {
        this.serverSocket1 = serverSocket1;
        System.out.println("server image is connected");

    }

    public void startImageServer(){
        while (!serverSocket1.isClosed()){
            try {
                Socket socket = serverSocket1.accept();
                System.out.println("A new Client has a connected for image server");
                ImageHandler imageHandler = new ImageHandler(socket);

                Thread thread = new Thread(imageHandler);
                thread.start();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }




















}
