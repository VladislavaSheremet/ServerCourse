package sample.Lists;

import sample.SerializeClasses.ShowForSearch;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfSearch implements Serializable {

    public ArrayList<ShowForSearch> listOfSearch = new ArrayList<>();

    public ListOfSearch(ArrayList<ShowForSearch> listOfFurniture) {
        this.listOfSearch = listOfFurniture;
    }

    public ArrayList<ShowForSearch> getListOfSearch() {
        return listOfSearch;
    }

    public void setListOfSearch(ArrayList<ShowForSearch> listOfFurniture) {
        this.listOfSearch = listOfFurniture;
    }

    public ListOfSearch() {}
}