package sample.Lists;

import sample.SerializeClasses.ShowInrormationAboutProductData;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfFurniture implements Serializable {

    public ArrayList<ShowInrormationAboutProductData> listOfFurniture = new ArrayList<>();

    public ListOfFurniture(ArrayList<ShowInrormationAboutProductData> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ArrayList<ShowInrormationAboutProductData> getListOfFurniture() {
        return listOfFurniture;
    }

    public void setListOfFurniture(ArrayList<ShowInrormationAboutProductData> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ListOfFurniture() {}

}
