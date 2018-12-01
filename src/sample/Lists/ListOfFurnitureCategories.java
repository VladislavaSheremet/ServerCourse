package sample.Lists;

import sample.SerializeClasses.ShowInformationForCategory;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfFurnitureCategories implements Serializable {

    public ArrayList<ShowInformationForCategory> listOfFurniture = new ArrayList<>();

    public ListOfFurnitureCategories(ArrayList<ShowInformationForCategory> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ArrayList<ShowInformationForCategory> getListOfFurniture() {
        return listOfFurniture;
    }

    public void setListOfFurniture(ArrayList<ShowInformationForCategory> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ListOfFurnitureCategories() {}
}