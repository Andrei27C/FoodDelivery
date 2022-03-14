package presentation;

import data.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;

public class RegisterController {

    public TextField usernameTF;
    public TextField passwordTF;


    public void register(ActionEvent actionEvent) throws FileNotFoundException {
        DatabaseConnection data = new DatabaseConnection();
        if(!data.addUser(usernameTF.getText(), passwordTF.getText(), "user"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Username already exists");
            alert.showAndWait();
        }


    }
}
