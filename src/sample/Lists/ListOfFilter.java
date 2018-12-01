package sample.Lists;

import sample.SerializeClasses.ShowForFilter;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfFilter implements Serializable {

    public ArrayList<ShowForFilter> listOfSort = new ArrayList<>();

    public ListOfFilter(ArrayList<ShowForFilter> listOfFurniture) {
        this.listOfSort = listOfFurniture;
    }

    public ArrayList<ShowForFilter> getListOfFilter() {
        return listOfSort;
    }

    public void setListOfFilter(ArrayList<ShowForFilter> listOfFurniture) {
        this.listOfSort = listOfFurniture;
    }

    public ListOfFilter() {}
}