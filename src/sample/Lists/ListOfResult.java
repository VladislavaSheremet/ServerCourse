package sample.Lists;

import sample.SerializeClasses.ShowResultProfit;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfResult implements Serializable {

    public ArrayList<ShowResultProfit> listOfFurniture = new ArrayList<>();

    public ListOfResult(ArrayList<ShowResultProfit> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ArrayList<ShowResultProfit> getListOfResult() {
        return listOfFurniture;
    }

    public void setListOfResult(ArrayList<ShowResultProfit> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public ListOfResult() {}
}
