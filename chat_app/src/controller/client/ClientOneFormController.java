package controller.client;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientOneFormController {
    public JFXTextField txtClient;
    private Socket socket = null;
    private static  final String TERMINATE = "EXIT";




    public  void initialize(){




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
}
