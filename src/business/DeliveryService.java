package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService extends Observable implements IDeliveryService {

    String selectedLine;
    String modifiedLine;


    public String getSelectedLine() {
        return selectedLine;
    }

    public void setSelectedLine(String selectedLine) {
        this.selectedLine = selectedLine;
    }

    public String getModifiedLine() {
        return modifiedLine;
    }

    public void setModifiedLine(String modifiedLine) {
        this.modifiedLine = modifiedLine;
    }

    @Override
    public void refreshTable(ListView<BaseProduct> listView) {
        System.out.println("Merge");

        listView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {

            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.parseFloat(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(filterFunction(BaseProduct::getName))
                    .collect(Collectors.toList());

            products.forEach(a -> listView.getItems().add(a));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> Predicate<T> filterFunction(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public void add(TextField nameTF, TextField ratingTF, TextField caloriesTF, TextField proteinsTF, TextField fatTF, TextField sodiumTF, TextField priceTF, ListView<BaseProduct> listView) throws IOException {
        System.out.println("Merge");
        String finalStr;
        double rating = Double.parseDouble(ratingTF.getText());
        int ratingInt = (int) rating;
        if(rating == ratingInt)
            finalStr = nameTF.getText() + " ," + ratingInt + "," + caloriesTF.getText() + "," +
                proteinsTF.getText() + "," + fatTF.getText() + "," + sodiumTF.getText() + "," + priceTF.getText() + "\n";
        else
            finalStr = nameTF.getText() + " ," + ratingTF.getText() + "," + caloriesTF.getText() + "," +
                    proteinsTF.getText() + "," + fatTF.getText() + "," + sodiumTF.getText() + "," + priceTF.getText() + "\n";
        Path path = Paths.get("products.csv");
        if (Files.exists(path)) {
            Files.write(Paths.get("products.csv"),
                    finalStr.getBytes(),
                    StandardOpenOption.APPEND);
        }
        refreshTable(listView);
    }

    @Override
    public void delete(ListView<BaseProduct> listView) throws IOException {
        File file = new File("products.csv");
        List<String> x = Files.lines(file.toPath())
                .filter(line -> !line.contains(selectedLine.substring(0,selectedLine.indexOf(','))))
                .collect(Collectors.toList());
        Files.write(file.toPath(), x, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        refreshTable(listView);
    }

    @Override
    public void refreshComposite(ListView<CompositeProduct> compView) {
        System.out.println("Merge");
        compView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("CompProducts.txt"))) {
            List<CompositeProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new CompositeProduct(
                        arr[0],
                        arr[1]);
            }).filter(filterFunction(CompositeProduct::getName))
                    .collect(Collectors.toList());

            products.forEach(a -> compView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void modify(String modLine, ListView<BaseProduct> listView) throws IOException {
        System.out.println("Merge");

        this.modifiedLine = modLine;
        System.out.println(modifiedLine);
        System.out.println(selectedLine);
        try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {
            List<String> x = lines
                    .map(line-> line.replaceAll(selectedLine, this.modifiedLine))
                    .collect(Collectors.toList());

            x.forEach(System.out::println);
            Files.write(Path.of("products.csv"), x);
        }
        selectedLine = modifiedLine;
        System.out.println("Modify successful");
        refreshTable(listView);
    }

    @Override
    public void createComposite(TextField compNameTF, TextArea compProductsTF) throws IOException {
        System.out.println("Merge");

        String composite = compNameTF.getText() + "," + compProductsTF.getText() + "\n";
        Path path = Paths.get("CompProducts.txt");
        if (Files.exists(path)) {
            Files.write(Paths.get("CompProducts.txt"),
                    composite.getBytes(),
                    StandardOpenOption.APPEND);
        }
    }

    @Override
    public void refreshTableClient(ListView listView) {
        listView.getItems().clear();
        Pattern pattern = Pattern.compile(",");

        try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {

            List<BaseProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.parseFloat(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(filterFunction(BaseProduct::getName))
                    .collect(Collectors.toList());
            products.forEach(a -> listView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }

        try (Stream<String> lines = Files.lines(Path.of("CompProducts.txt"))) {
            List<CompositeProduct> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new CompositeProduct(
                        arr[0],
                        arr[1]);
            }).filter(filterFunction(CompositeProduct::getName))
                    .collect(Collectors.toList());
            products.forEach(a -> listView.getItems().add(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List getProducts()
    {
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> lines = Files.lines(Path.of("products.csv"))) {
            return lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                return new BaseProduct(
                        arr[0],
                        Float.parseFloat(arr[1]),
                        Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]),
                        Integer.parseInt(arr[4]),
                        Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).filter(filterFunction(BaseProduct::getName))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void searchForName(ListView listView, TextField nameTF) {
        listView.getItems().clear();

        List<BaseProduct> productsList = getProducts();
        List<CompositeProduct> compositeProducts = getProducts();

        assert productsList != null;
        List<BaseProduct> list = productsList
                .stream()
                .filter(c ->c.getName().toLowerCase().contains(nameTF.getText().toLowerCase()))
                .collect(Collectors.toList());
        list.forEach(a->listView.getItems().add(a));
    }

    @Override
    public void searchForRating(ListView listView, TextField rankingTF) {
        listView.getItems().clear();

        List<BaseProduct> productsList = getProducts();

        assert productsList != null;
        List<BaseProduct> list = productsList
                .stream()
                .filter(c->c.getRating() == Float.parseFloat(rankingTF.getText()))
                .collect(Collectors.toList());

        list.forEach(a->listView.getItems().add(a));
    }

    @Override
    public void searchMacrosOrPrice(ListView listView, TextField macrosOrPriceTF) {
        listView.getItems().clear();

        List<BaseProduct> productsList = getProducts();

        assert productsList != null;
        List<BaseProduct> list = productsList
                .stream()
                .filter(c-> c.getCalories() == Integer.parseInt(macrosOrPriceTF.getText()) ||
                        c.getProteins() == Integer.parseInt(macrosOrPriceTF.getText()) ||
                        c.getFats() == Integer.parseInt(macrosOrPriceTF.getText()) ||
                        c.getSodium() == Integer.parseInt(macrosOrPriceTF.getText()) ||
                        c.getPrice() == Integer.parseInt(macrosOrPriceTF.getText()))
                .collect(Collectors.toList());

        list.forEach(a->listView.getItems().add(a));
    }

    @Override
    public void placeOrder(ListView<String> cartView, Label costLabel, String username) throws IOException {
        String finalStr = username + "; ";// = getUsername();
        List<String> foods = cartView.getItems();
        for( String x : foods){
            finalStr += x.substring(0,x.indexOf(" -Rating:")-1) + ", ";
        }
        finalStr = finalStr.substring(0,finalStr.length()-2);
        finalStr += "; " + costLabel.getText();
        finalStr += "; " + getDate() + "\n";
        Path path = Paths.get("Orders.txt");

        if (Files.exists(path)) {
            Files.write(Paths.get("Orders.txt"),
                    finalStr.getBytes(),
                    StandardOpenOption.APPEND);
        }

        setChanged();
        String alertString = username + " just placed an order!";
        notifyObservers(alertString);
    }

    @Override
    public String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy; HH:mm");
        return formatter.format(date);
    }

}
