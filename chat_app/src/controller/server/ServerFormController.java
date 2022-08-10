package controller.server;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public JFXTextField txtServer;
    public Socket  socket;
    private static  final String TERMINATE = "EXIT";
    public TextArea txtAreaServer;



    public void initialize(){



        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("server started");

                socket = serverSocket.accept();
                System.out.println("client connected");


                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                while (true){
                    String response = bufferedReader.readLine();

                    if(response.equalsIgnoreCase(TERMINATE)){
                        System.out.println("appatat siri server eka  close una ");
                    }


                    System.out.println(response);
                    txtAreaServer.appendText("Tharindu : "+ response+ "\n");
                    txtAreaServer.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

                }




            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();





    }








    public void serverSendOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println(txtServer.getText());
        writer.flush();

        txtAreaServer.appendText("                          "+txtServer.getText()+ "\n");

       // txtAreaServer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);



    }
}
