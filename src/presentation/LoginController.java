package presentation;

import business.DeliveryService;
import data.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import sample.Admin;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    public TextField usernameTF;
    public TextField passwordTF;

    DatabaseConnection data = new DatabaseConnection();
    DeliveryService deliveryService = new DeliveryService();

    public LoginController() throws IOException {
    }

    public void login(ActionEvent actionEvent) throws IOException {
        if(userExists()==0)
        {
            goToAdmin();
        }
        else if(userExists()==1)
            goToUser();
        else if(userExists()==2)
            goToEmployee();
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Username or password incorrect");
            alert.showAndWait();
        }

    }

    private void goToUser() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/ClientGUI.fxml"));
            Parent root = loader.load();
            ClientController secController = loader.getController();
            secController.getUsername(usernameTF.getText());
            secController.setDeliveryService(deliveryService);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1011, 720));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    private void goToAdmin() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/AdminGUI.fxml"));
            Parent root = loader.load();
            AdminController secController = loader.getController();
            secController.setDeliveryService(deliveryService);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1280, 620));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    private void goToEmployee() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/EmployeeGUI.fxml"));
            Parent root = loader.load();
            EmployeeController secController = loader.getController();
            secController.setDeliveryService(deliveryService);
            deliveryService.addObserver(secController);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1280, 620));
            stage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    private int userExists() throws IOException {
        ArrayList<User> userArrayList = data.initDatabase();
        //System.out.println(userArrayList);
        int verifier = -1;
        for (User x :
                userArrayList) {
            if (x.getUsername().equals(usernameTF.getText()) && x.getPassword().equals(passwordTF.getText()))
            {
                if(x.getRole().equals("admin"))
                    verifier = 0;
                else if(x.getRole().equals("user"))
                    verifier = 1;
                else
                    verifier = 2;
            }
        }
        return verifier;
    }



    public void register(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../presentation/RegisterGUI.fxml"));
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root, 250, 350));
            registerStage.show();

        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }
}
