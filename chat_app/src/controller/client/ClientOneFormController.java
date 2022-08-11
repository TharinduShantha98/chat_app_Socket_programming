package controller.client;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    public ScrollPane sp_main;
    public VBox vBox_message;

    private Socket socket = null;
    private static  final String TERMINATE = "EXIT";
    public List<String> lsFile;
    public String imageLocation;





    public  void initialize(){
        setListArray();



       /* new Thread(()->{
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


        }).start();*/



        vBox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });

        //client.receiveMessageFormServer(vbox_message)

    }





    public void clientSendOnAction(ActionEvent actionEvent) throws IOException {
      /*  PrintWriter printWriter= new PrintWriter(socket.getOutputStream());
        printWriter.println(txtClient.getText());
        printWriter.flush();*/

        String messageToSend = txtClient.getText();


        if(!messageToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #5eb3d4");
            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.1,0.1,0.01,0.996));
            text.setStyle("-fx-font-weight: bold");

            hBox.getChildren().add(textFlow);
            vBox_message.getChildren().add(hBox);

            //client.sendMessageToClient(messageToSend);
        }


    }


    public static  void addLabel(String messageFromClient,VBox vBox){


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));


        Text text = new Text(messageFromClient);
        text.setStyle("-fx-font-weight: bold");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(139,136,76)");
        // textFlow.setStyle("-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });




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
