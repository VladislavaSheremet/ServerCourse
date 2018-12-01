package sample.SerializeClasses;

import java.io.Serializable;

public class ShowInformationForCategory implements Serializable {
    private int category;

    private int id;
    private String name;
    private String description;
    private int idcategory;
    private String material;
    private String color;
    private String country;
    private double price;
    private int klScklad;
    private int klBuy;
    private String additionalInformation;
    private double gross;
    private double selling;
    private double management;

    public ShowInformationForCategory(int id, int category, int idcategory, String name,
                                      String description, String material, String color, String country,
                                      double price, int klScklad, String additionalInformation, double gross, double selling, double management) {
        this.id = id;
        this.idcategory = idcategory;
        this.name = name;
        this.description = description;
        this.material = material;
        this.color = color;
        this.country = country;
        this.klScklad = klScklad;
        this.price = price;
        this.additionalInformation = additionalInformation;
        this.gross = gross;
        this.selling = selling;
        this.management = management;
    }

    public int getKlBuy() {
        return klBuy;
    }

    public void setKlBuy(int klBuy) {
        this.klBuy = klBuy;
    }

    public int getKlScklad() {
        return klScklad;
    }

    public void setKlScklad(int klScklad) {
        this.klScklad = klScklad;
    }

    public ShowInformationForCategory() {}

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
