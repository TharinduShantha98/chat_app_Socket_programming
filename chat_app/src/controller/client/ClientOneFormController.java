package controller.client;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ClientOneFormController {
    public JFXTextField txtClient;
    private Socket socket = null;
    private static  final String TERMINATE = "EXIT";
    public List<String> lsFile;
    public String imageLocation;





    public  void initialize(){
        setListArray();



        new Thread(()->{
            try {
                socket = new Socket("localhost",5000);
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                while (true){
                    String response =  bufferedReader.readLine();


                    if(response.equalsIgnoreCase(TERMINATE)){
                        System.out.println("appata siri client close una");
                        socket.close();
                    }


                    System.out.println(response);








                }






            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();



    }





    public void clientSendOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter printWriter= new PrintWriter(socket.getOutputStream());
        printWriter.println(txtClient.getText());
        printWriter.flush();


    }

    private void setListArray(){
        lsFile = new ArrayList<>();
        lsFile.add("*.jpg");
        lsFile.add("*.jpeg");
        lsFile.add("*.png");
    }



    public void fileUploadMouseClick(MouseEvent mouseEvent) throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("files",lsFile));
        File file =  fileChooser.showOpenDialog(null);


        if(file != null){
            System.out.println(file.getAbsolutePath());
            imageLocation = file.getAbsolutePath();

        }




        OutputStream outputStream = socket.getOutputStream();
        BufferedImage image = ImageIO.read(new File(imageLocation));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);



        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());
        Thread.sleep(5000);





    }
}
