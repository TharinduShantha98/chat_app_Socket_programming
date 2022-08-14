package controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {


    public JFXButton btnLogin;
    public JFXTextField txtUserName;

    public static String userName;


    public  void loginOnAction(ActionEvent actionEvent) throws IOException {

        userName = txtUserName.getText();


        Stage stage  = (Stage) btnLogin.getScene().getWindow();
        stage.close();


        URL resource = getClass().getResource("../../views/ClientOneForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setTitle("Client");
        stage1.show();


    }
}
