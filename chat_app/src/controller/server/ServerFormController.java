package controller.server;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerFormController {
    public JFXTextField txtServer;
    public TextArea txtAreaServer;
    public ImageView imageView;
    public ScrollPane sp_main;
    public VBox vBox_message;



    public Socket  socket;
    private static  final String TERMINATE = "EXIT";

    private  Server server;




    public void initialize(){


        try {
            server = new Server(new ServerSocket(5000));



        } catch (IOException e) {
            e.printStackTrace();
        }


        vBox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((double) newValue);

            }
        });


        server.receiveMessageFromClient(vBox_message);




    }








    public void serverSendOnAction(ActionEvent actionEvent) throws IOException {
        /*PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(txtServer.getText());
        writer.flush();

        txtAreaServer.appendText("                          "+txtServer.getText()+ "\n");*/
       // txtAreaServer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        String  messageToSend  =  txtServer.getText();

        if(!messageToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));


            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color: #5eb3d4");
            //textFlow.setStyle("-fx-border-radius: 20px");

            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.1,0.1,0.01,0.996));
            text.setStyle("-fx-font-weight: bold");


            hBox.getChildren().add(textFlow);
            vBox_message.getChildren().add(hBox);

            // sendMessageToClient
            server.sendMessageToClient(messageToSend);


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




}
