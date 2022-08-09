package controller.client;

import com.jfoenix.controls.JFXButton;
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

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

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
