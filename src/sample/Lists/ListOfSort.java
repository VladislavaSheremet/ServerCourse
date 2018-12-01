package sample.Lists;

import sample.SerializeClasses.ShowForSorting;
import sample.SerializeClasses.ShowInrormationAboutProductData;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfSort implements Serializable {

    public ArrayList<ShowForSorting> listOfSort = new ArrayList<>();

    public ListOfSort(ArrayList<ShowForSorting> listOfFurniture) {
        this.listOfSort = listOfFurniture;
    }

    public ArrayList<ShowForSorting> getListOfSort() {
        return listOfSort;
    }

    public void setListOfSort(ArrayList<ShowForSorting> listOfFurniture) {
        this.listOfSort = listOfFurniture;
    }

    public ListOfSort() {}

}