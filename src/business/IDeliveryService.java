package business;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public interface IDeliveryService {
    void refreshTable(ListView<BaseProduct> listView);

    void add(TextField nameTF, TextField ratingTF, TextField caloriesTF, TextField proteinsTF, TextField fatTF, TextField sodiumTF, TextField priceTF, ListView<BaseProduct> listView) throws IOException;

    void delete(ListView<BaseProduct> listView) throws IOException;

    void refreshComposite(ListView<CompositeProduct> compView);

    void modify(String modLine, ListView<BaseProduct> listView) throws IOException;

    void createComposite(TextField compNameTF, TextArea compProductsTF) throws IOException;

    void refreshTableClient(ListView listView);

    List getProducts();

    void searchForName(ListView listView, TextField nameTF);

    void searchForRating(ListView listView, TextField rankingTF);

    void searchMacrosOrPrice(ListView listView, TextField macrosOrPriceTF);

    void placeOrder(ListView<String> cartView, Label costLabel, String username) throws IOException;

    String getDate();
}
