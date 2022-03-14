package business;

public class BaseProduct {

    private String name;
    private float rating;
    private int price;
    private int sodium;
    private int fats;
    private int proteins;
    private int calories;

    public BaseProduct(String name, float rating, int calories, int proteins, int fats, int sodium, int price)
    {
        this.name = name;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " -Rating: " + rating + " -Kcal: " + calories + " -Proteins: " + proteins + " -Fats: " + fats + " -Sodium: " + sodium + " -Price: " + price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
