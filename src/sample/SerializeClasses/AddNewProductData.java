package sample.SerializeClasses;

import java.io.Serializable;

public class AddNewProductData implements Serializable {
    String categories;
    String ProductName;
    String ProductDescription;
    String Material;
    String Color;
    String Country;
    String Information;
    String Price;
    String grassProfit;
    String sellingExpenses;
    String managementExpenses;
    String klSklad;

    public String getKlSklad() {
        return klSklad;
    }

    public void setKlSklad(String klSklad) {
        this.klSklad = klSklad;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getGrassProfit() {
        return grassProfit;
    }

    public void setGrassProfit(String grassProfit) {
        this.grassProfit = grassProfit;
    }

    public String getSellingExpenses() {
        return sellingExpenses;
    }

    public void setSellingExpenses(String sellingExpenses) {
        this.sellingExpenses = sellingExpenses;
    }

    public String getManagementExpenses() {
        return managementExpenses;
    }

    public void setManagementExpenses(String managementExpenses) {
        this.managementExpenses = managementExpenses;
    }
}