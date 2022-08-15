package controller.client;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ClientOneFormController {
    public JFXTextField txtClient;
    public ScrollPane sp_main;
    public VBox vBox_message;
    public Label lblUserName;
    public List<String> lsFile;
    public String imageLocation;


    private Clients clients;
    private ClientImage clientImage;
    public  static  String userName;


    public  void initialize(){
        setListArray();
        if(!LoginFormController.userName.isEmpty()){
            userName = LoginFormController.userName;
            System.out.println(userName);
            lblUserName.setText(userName);
        }else {
            userName = "Tharindu";

        }


        new Thread(()->{

            try {

              //  clientImage = new ClientImage(new Socket("localhost",4000));
                clients = new Clients(new Socket("localhost",5000), userName);
                clients.sendMessage(userName +" connected");


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


        vBox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });


    }


    public void clientSendOnAction(ActionEvent actionEvent) throws IOException {


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


            new  Thread(()->{
                clients.listenForMessage(vBox_message);
                clients.sendMessage(messageToSend);
            }).start();



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
        //textFlow.setStyle("-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });




    }


    public void mouseOnClickMENEmoji(MouseEvent mouseEvent) {
        String  MenEmoji = new String(Character.toChars(0x1F604));
        txtClient.setText(MenEmoji);

    }

    public void mouseClickHEARTemoji(MouseEvent mouseEvent) {

        String  heartEmoji = new String(Character.toChars(0x2764));
        txtClient.setText(heartEmoji);
    }

    public void mouseClickHANDemoji(MouseEvent mouseEvent) {
        String  heartEmoji = new String(Character.toChars(0x270C));
        txtClient.setText(heartEmoji);

    }

    public void mouseClickFROWN_ALTemoji(MouseEvent mouseEvent) {
        String  disappointed_face = new String(Character.toChars(	0x1F61E));
        txtClient.setText(disappointed_face);

    }

    public void mouseClickSTARTemoji(MouseEvent mouseEvent) {
        String sparkles  = new String(Character.toChars(	0x2728));
        txtClient.setText(sparkles);



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
            sendImage(imageLocation);
           // client.sendImageToServer(imageLocation);


            new Thread(()->{

               /* try {
                    clientImage = new ClientImage(new Socket("localhost",4000));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                clients.receiveImagesFromClient(vBox_message);
                clients.sendImageToServer(imageLocation);
            }).start();


        }


    }



    public  void sendImage(String pathName){

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));


        File file = new File(pathName);
        try {
            Image image1 = new Image(new FileInputStream(file));
            ImageView imageView = new ImageView(image1);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            TextFlow textFlow = new TextFlow(imageView);
            hBox.getChildren().add(textFlow);
            vBox_message.getChildren().add(hBox);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }





    public static   void GetImageForDisplay(String pathName, VBox vBox){

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));


        File file = new File(pathName);
        try {
            Image image1 = new Image(new FileInputStream(file));
            ImageView imageView = new ImageView(image1);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            TextFlow textFlow = new TextFlow(imageView);
            hBox.getChildren().add(textFlow);


            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBox);
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }


}
