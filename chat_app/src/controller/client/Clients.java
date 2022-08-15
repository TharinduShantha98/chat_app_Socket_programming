package controller.client;

import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

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



    //=========================================
    public  void  sendImageToServer(String imageLocation){
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


    }




}
