package presentation;

import business.Order;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportsController {

    public TextField startHourTF;
    public TextField endHourTF;
    public TextField clientTF;
    public TextField dateTF;
    public TextField xTimesTF;
    public TextField orderedAmountOfTimesTF;
    public TextField valueofOrderTF;

    private static List<Order> orderList;

    private static List<Order> getOrders()
    {
        if(orderList != null)
            orderList.clear();
        Pattern pattern = Pattern.compile("; ");
        try (Stream<String> lines = Files.lines(Path.of("Orders.txt"))) {
            return lines.map(line -> {
                String[] arr = pattern.split(line);
                //System.out.println(Arrays.toString(arr));
                return new Order(
                        arr[0],
                        arr[1],
                        arr[2],
                        arr[3],
                        Integer.parseInt(arr[4].substring(0,2)));
            }).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void generateInterval(ActionEvent actionEvent) throws IOException {
        int startTime = Integer.parseInt(startHourTF.getText().substring(0,2));
        int endTime = Integer.parseInt(endHourTF.getText().substring(0,2));

        orderList = getOrders();

        assert orderList != null;
        List<Order> list = orderList
                .stream()
                .filter(c -> c.getHour() >= startTime &&
                        c.getHour() <= endTime)
                .collect(Collectors.toList());


        list.forEach(System.out::println);

        File file = new File("ReportByInterval.txt");
        FileWriter fw=new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(list.toString());
        bw.close();
    }

    public void generateMoreThan(ActionEvent actionEvent) throws IOException {

        orderList = getOrders();
        ArrayList<String> allProducts = eachProductNoOfTimes();

        assert orderList != null;
        for (String product:
                allProducts) {
            String[] x = product.split(">");
            if(Integer.parseInt(x[1]) > Integer.parseInt(xTimesTF.getText()))
            {
                File file = new File("ReportByXTimes.txt");
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(orderList.get(0).getMeal());
                bw.close();
            }
        }
        /*
        if (orderList.size() + 1 >= Integer.parseInt(xTimesTF.getText())) {
            File file = new File("ReportByXTimes.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(orderList.get(0).getMeal());
            bw.close();
        }*/
    }
    
    private ArrayList<String> eachProductNoOfTimes()
    {
        class Product{
            String meal; int counter;
            public Product(String meal, int counter)
            {
                this.meal = meal;
                this.counter = counter;
            }
            public void incrementCounter()
            {
                counter++;
            }
        }
        ArrayList<Product> products = new ArrayList<>();
        for (Order order :
                orderList) {
            String[] temporaryList = order.getMeal().split(", ");
            for (String meal :
                    temporaryList) {
                boolean exists = false;
                for (Product product :
                        products) {
                    if (product.meal.equals(meal)){
                        product.incrementCounter();
                        exists = true;
                    }
                }
                if(!exists)
                    products.add(new Product(meal,1));
            }
        }
        ArrayList<String> allProducts = new ArrayList<>();
        for (Product product :
                products) {
            allProducts.add(product.meal + ">" + product.counter);

        }
        System.out.println(allProducts);
        return allProducts;
    }

    public void generatePerClient(ActionEvent actionEvent) throws IOException {
        orderList = getOrders();

        assert orderList != null;
        List<Order> auxProd = orderList
                .stream()
                .filter(p->Integer.parseInt(p.getPrice()) >= Integer.parseInt(orderedAmountOfTimesTF.getText()))
                .collect(Collectors.toList());


        if( orderList.size() + 1 >= Integer.parseInt(valueofOrderTF.getText()) ){
            File file = new File("ReportByClient.txt");
            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(auxProd.get(0).getUsername());
            bw.close();
        }
    }

    public void generateByDay(ActionEvent actionEvent) throws IOException {
        orderList = getOrders();

        assert orderList != null;
        List<Order> auxProd = orderList
                .stream()
                .filter(c ->c.getDate().contains(dateTF.getText()))
                .collect(Collectors.toList());

        auxProd.forEach(System.out::println);

        File file = new File("ReportByDay.txt");
        FileWriter fw=new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw=new BufferedWriter(fw);
        //bw.write();
        bw.close();
    }
}
