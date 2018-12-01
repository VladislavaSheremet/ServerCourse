package sample.Lists;


import sample.SerializeClasses.DataForAnalis;

import java.io.Serializable;
import java.util.ArrayList;

public class ListForAnalis  implements Serializable {

    public ArrayList<DataForAnalis> listForAnalis = new ArrayList<>();

    public ListForAnalis(ArrayList<DataForAnalis> listForAnalis) {
        this.listForAnalis = listForAnalis;
    }

    public ArrayList<DataForAnalis> getlistForAnalis() {
        return listForAnalis;
    }

    public void setlistForAnalis(ArrayList<DataForAnalis> listForAnalis) {
        this.listForAnalis = listForAnalis;
    }

    public ListForAnalis() {}
}
