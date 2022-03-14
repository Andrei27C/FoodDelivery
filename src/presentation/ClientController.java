package presentation;

import business.BaseProduct;
import business.CompositeProduct;
import business.DeliveryService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientController {
    public ListView listView;
    public ListView<String> cartView;
    public TextField nameTF;
    public TextField rankingTF;
    public TextField macrosOrPriceTF;
    public Label costLabel;

    String selectedLine;
    String username;

    DeliveryService deliveryService = new DeliveryService();

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    int price = 0;

    public void clickOnTableRow(MouseEvent mouseEvent) {
        selectedLine = listView.getSelectionModel().getSelectedItem().toString();
    }

    public void refreshTable(ActionEvent actionEvent) {
        deliveryService.refreshTableClient(listView);
    }

    public void searchForName(ActionEvent actionEvent) {
        deliveryService.searchForName(listView, nameTF);
    }

    public void searchForRating(ActionEvent actionEvent) {
        deliveryService.searchForRating(listView,rankingTF);
    }

    public void searchMacrosOrPrice(ActionEvent actionEvent) {
        deliveryService.searchMacrosOrPrice(listView,macrosOrPriceTF);
    }

    public void addToCart(ActionEvent actionEvent) {
        List<BaseProduct> productsList = deliveryService.getProducts();
        assert productsList != null;
        List<BaseProduct> list = productsList
                .stream()
                .filter(c ->c.getName().contains(selectedLine.substring(0,selectedLine.indexOf(" -Rating:")-1)))
                .collect(Collectors.toList());

        price += list.get(0).getPrice();
        String priceString = Integer.toString(price);
        costLabel.setText(Integer.toString(price));
        cartView.getItems().add(selectedLine);

    }

    public void placeOrder(ActionEvent actionEvent) throws IOException {
        deliveryService.placeOrder(cartView,costLabel,username);
    }

    public void getUsername(String username)
    {
        this.username = username;
    }
}
