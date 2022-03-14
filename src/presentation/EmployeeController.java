package presentation;

import business.CompositeProduct;
import business.DeliveryService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeController implements Observer {

    public ListView<String> ordersView;
    DeliveryService deliveryService = new DeliveryService();

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }



    public void listOrders() {
        ordersView.getItems().clear();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "Orders.txt"));
            String line = reader.readLine();
            while (line != null) {
                ordersView.getItems().add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order");
        alert.setHeaderText((String)arg);
        alert.showAndWait();
        listOrders();
    }
}
