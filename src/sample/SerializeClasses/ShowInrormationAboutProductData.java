package sample.SerializeClasses;

import java.io.Serializable;

public class ShowInrormationAboutProductData implements Serializable {

    private boolean show;

    private int id;
    private String name;
    private String description;
    private String Category;
    private int idcategory;
    private String material;
    private String color;
    private String country;
    private double price;
    private int klSklad;
    private int klBuy;
    private String additionalInformation;
    private double gross;
    private double selling;
    private double management;

    public int getKlSklad() {
        return klSklad;
    }

    public void setKlSklad(int klSklad) {
        this.klSklad = klSklad;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getKlBuy() {
        return klBuy;
    }

    public void setKlBuy(int klBuy) {
        this.klBuy = klBuy;
    }

    public ShowInrormationAboutProductData(int id, String Category, int idcategory, String name,
                                           String description, String material, String color, String country,
                                           double price, String additionalInformation, double gross, double selling, double management) {
        this.id = id;
        this.idcategory = idcategory;
        this.Category = Category;
        this.name = name;
        this.description = description;
        this.material = material;
        this.color = color;
        this.country = country;
        this.price = price;
        this.additionalInformation = additionalInformation;
        this.gross = gross;
        this.selling = selling;
        this.management = management;
    }

    public ShowInrormationAboutProductData() {}

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    public double getSelling() {
        return selling;
    }

    public void setSelling(double selling) {
        this.selling = selling;
    }

    public double getManagement() {
        return management;
    }

    public void setManagement(double management) {
        this.management = management;
    }

    public int getIdcategory() { return idcategory; }

    public void setIdcategory(int idcategory) { this.idcategory = idcategory; }

    public String getAdditionalInformation() { return additionalInformation; }

    public void setAdditionalInformation(String additionalInformation) { this.additionalInformation = additionalInformation; }
}
