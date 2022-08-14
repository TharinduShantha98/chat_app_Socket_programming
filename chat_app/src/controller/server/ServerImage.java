package controller.server;

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

    private Socket socket1;


    public ServerImage(ServerSocket serverSocket1) {

        try {
            socket1 = serverSocket1.accept();
            System.out.println("client  image is connected");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void receiveImagesFromClient(VBox vBox)  {
        System.out.println("badu awda");


        new Thread(()->{
            while (socket1.isConnected()){

                try {

                    String location = receivedImageFormClient();
                    System.out.println(location);
                    //ServerFormController.GetImageForDisplay(location, vBox);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }).start();




    }





    public String  receivedImageFormClient() throws IOException {

        InputStream inputStream = socket1.getInputStream();
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
}
