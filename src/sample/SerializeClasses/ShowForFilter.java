package sample.SerializeClasses;

import java.io.Serializable;

public class ShowForFilter implements Serializable {

    private int type;
    String otStr;
    String doStr;
    int otInt;
    int doInt;
    double otDouble;
    double doDouble;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getOtStr() {
        return otStr;
    }

    public void setOtStr(String otStr) {
        this.otStr = otStr;
    }

    public String getDoStr() {
        return doStr;
    }

    public void setDoStr(String doStr) {
        this.doStr = doStr;
    }

    public int getOtInt() {
        return otInt;
    }

    public void setOtInt(int otInt) {
        this.otInt = otInt;
    }

    public int getDoInt() {
        return doInt;
    }

    public void setDoInt(int doInt) {
        this.doInt = doInt;
    }

    public double getOtDouble() {
        return otDouble;
    }

    public void setOtDouble(double otDouble) {
        this.otDouble = otDouble;
    }

    public double getDoDouble() {
        return doDouble;
    }

    public void setDoDouble(double doDouble) {
        this.doDouble = doDouble;
    }

}

