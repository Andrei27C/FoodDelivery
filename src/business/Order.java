package business;

import java.io.Serializable;

public class Order implements Serializable {
    private String username;
    private String meal;
    private String price;
    private String date;
    private int hour;

    public Order(String username, String firstMeal, String price, String date, int hour){
        this.username = username;
        this.meal = firstMeal;
        this.price = price;
        this.date = date;
        this.hour = hour;
    }

    @Override
    public String toString(){
        return username + ", " + meal +  ", " + price + ", " + date + ", " + hour + "\n";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String firstMeal) {
        this.meal = firstMeal;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
