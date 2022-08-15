package controller.client;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ImageHandler  implements  Runnable{
    public static ArrayList<ImageHandler> imageHandlers = new ArrayList<>();
    private final Socket socket;
    private final int port;
   // private InputStream inputStream;
    private OutputStream outputStream;
    private Image image = null;



    public ImageHandler(Socket socket) {
        this.socket = socket;
        this.port = socket.getPort();

//        try {
//            inputStream = socket.getInputStream();
//            outputStream = socket.getOutputStream();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        imageHandlers.add(this);





    }

    @Override
    public void run() {


       /* while (socket.isConnected()){
            try {
                String s = receivedImageFormClient();
                System.out.println(s);
                broadCastImage(s);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/

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
            broadCastImage("src/assets/client/test2.jpg");
        }


       /* int dstWidth = 100;
        int dstHeight = 100;
        InputStream imageStream = socket.getInputStream();
        byte[] sizeAr = new byte[4];
        imageStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        byte[] imageAr = new byte[size];
        imageStream.read(imageAr);


        BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(imageAr));
        if (srcImage == null) {
            System.err.println("NO SOURCE IMAGE!");

        }
        BufferedImage dstImage = new BufferedImage(dstWidth, dstHeight,
                BufferedImage.TYPE_INT_RGB);
        dstImage.getGraphics().drawImage(
                srcImage, 0, 0, dstWidth, dstHeight, null);

        boolean jpg = ImageIO.write(dstImage, "jpg", new File("src/assets/client/test2.jpg"));
        if(jpg){
            return "src/assets/client/test2.jpg";
        }*/

        return null;


    }






    private void broadCastImage(String imageLocation){
        System.out.println("");
        for(ImageHandler imageHandler : imageHandlers){


            if(imageHandler.port != port){

                try {
                    imageHandler.outputStream = imageHandler.socket.getOutputStream();
                    BufferedImage image = ImageIO.read(new File(imageLocation));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image,"jpg",byteArrayOutputStream);
                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    imageHandler.outputStream.write(size);
                    imageHandler.outputStream.write(byteArrayOutputStream.toByteArray());
                    imageHandler.outputStream.flush();
                    System.out.println("Flushed: " + System.currentTimeMillis());
                    Thread.sleep(5000);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }





    }
}
