package sample.SerializeClasses;

import java.io.Serializable;

public class ShowResultProfit  implements Serializable {

    private boolean show;

    private int id;
    private String name;
    private String Category;
    private int idcategory;
    private double price;
    private int klSklad;
    private int klBuy;
    private double gross;
    private double selling;
    private double management;
    private double result;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

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
}

