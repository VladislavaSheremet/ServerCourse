package sample.Lists;

import sample.SerializeClasses.DataForGistogram;

import java.io.Serializable;
import java.util.ArrayList;

public class ListForGistogram implements Serializable {

    public ArrayList<DataForGistogram> listForGistogram = new ArrayList<>();

    public ListForGistogram(ArrayList<DataForGistogram> listForGistogram) {
        this.listForGistogram = listForGistogram;
    }

    public ArrayList<DataForGistogram> getlistForGistogram() {
        return listForGistogram;
    }

    public void setlistForGistogram(ArrayList<DataForGistogram> listForGistogram) {
        this.listForGistogram = listForGistogram;
    }

    public ListForGistogram() {}
}