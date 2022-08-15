package controller.client;

import controller.server.ServerFormController;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientImage {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public ClientImage(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

  /*  public  void  sendImageToServer(String imageLocation){
        System.out.println("methanta awa yakooo ");
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            BufferedImage image = ImageIO.read(new File(imageLocation));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image,"jpg",byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());
            Thread.sleep(7000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void receiveImagesFromClient(VBox vBox)  {
        System.out.println("badu awda");


        new Thread(()->{
            while (socket.isConnected()){

                try {

                    String location = receivedImageFormClient();
                    System.out.println(location);
                    ClientOneFormController.GetImageForDisplay(location, vBox);




                } catch (IOException e) {
                    e.printStackTrace();
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


    }*/









}
