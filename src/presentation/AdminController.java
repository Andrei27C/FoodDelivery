package presentation;

import business.BaseProduct;
import business.CompositeProduct;
import business.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminController {
    public Button refreshTableButton;
    public Button addButton;
    public Button deleteButton;
    public Button compositeProductsButton;

    public TextField nameTF;
    public TextField ratingTF;
    public TextField caloriesTF;
    public TextField proteinsTF;
    public TextField fatTF;
    public TextField sodiumTF;
    public TextField priceTF;

    public ListView<BaseProduct> listView;
    public ListView<CompositeProduct> compView;
    public Button updateButton;
    public TextField compNameTF;
    public TextArea compProductsTF;

    String selectedLine;
    String modifiedLine;

    DeliveryService deliveryService = new DeliveryService();

    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void refreshTable(ActionEvent actionEvent) {
        deliveryService.refreshTable(listView);

    }

    public static <T> Predicate<T> filterFunction(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void add(ActionEvent actionEvent) throws IOException {
        deliveryService.add(nameTF,ratingTF,caloriesTF,proteinsTF,fatTF,sodiumTF,priceTF,listView);


    }

    public void delete(ActionEvent actionEvent) throws IOException {
        deliveryService.delete(listView);

    }

    public void refreshComposite(ActionEvent actionEvent) {
        deliveryService.refreshComposite(compView);

    }

    public void clickOnTableRow()
    {
        String s = listView.getSelectionModel().getSelectedItem().toString();
        nameTF.setText(s.substring(0,s.indexOf(" -Rating:")-1));
        ratingTF.setText(s.substring(s.indexOf("Rating: ")+8,s.indexOf(" -Kcal:")));
        caloriesTF.setText(s.substring(s.indexOf("Kcal: ")+6,s.indexOf(" -Proteins:")));
        proteinsTF.setText(s.substring(s.indexOf("Proteins: ")+10,s.indexOf(" -Fats:")));
        fatTF.setText(s.substring(s.indexOf("Fats: ")+6,s.indexOf(" -Sodium:")));
        sodiumTF.setText(s.substring(s.indexOf("Sodium: ")+8,s.indexOf(" -Price:")));
        priceTF.setText(s.substring(s.indexOf("Price: ")+7));

        double rating = Double.parseDouble(ratingTF.getText());
        int ratingInt = (int) rating;
        if(rating == ratingInt)
            selectedLine = nameTF.getText() + " ," + ratingInt + "," + caloriesTF.getText() + "," +
                    proteinsTF.getText() + "," + fatTF.getText() + "," + sodiumTF.getText() + "," + priceTF.getText();
        else
            selectedLine = nameTF.getText() + " ," + ratingTF.getText() + "," + caloriesTF.getText() + "," +
                    proteinsTF.getText() + "," + fatTF.getText() + "," + sodiumTF.getText() + "," + priceTF.getText();
        deliveryService.setSelectedLine(selectedLine);

    }

    public void modify() throws IOException {
        modifiedLine = nameTF.getText() + " ," + ratingTF.getText() + "," + caloriesTF.getText() + "," +
                proteinsTF.getText() + "," + fatTF.getText() + "," + sodiumTF.getText() + "," + priceTF.getText();
        //System.out.println(modifiedLine);
        //System.out.println(selectedLine);
        deliveryService.modify(modifiedLine,listView);
    }

    public void createComposite(ActionEvent actionEvent) throws IOException {
        deliveryService.createComposite(compNameTF,compProductsTF);
    }

    public void generateReports(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../presentation/ReportsGUI.fxml"));
            Stage reportsStage = new Stage();
            reportsStage.setScene(new Scene(root, 408, 335));
            reportsStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }
}
