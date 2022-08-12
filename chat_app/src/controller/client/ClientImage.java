package controller.client;

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




    public  void  sendImageToServer(String imageLocation){

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
            Thread.sleep(5000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }



}
