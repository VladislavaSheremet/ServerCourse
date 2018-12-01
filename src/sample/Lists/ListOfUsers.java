package sample.Lists;

import sample.SerializeClasses.ShowClientData;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfUsers implements Serializable {

    public ArrayList<ShowClientData> listOfUser = new ArrayList<>();

    public ListOfUsers(ArrayList<ShowClientData> listOfFurniture) {
        this.listOfUser = listOfFurniture;
    }

    public ArrayList<ShowClientData> getListOfUser() {
        return listOfUser;
    }

    public void setListOfUser(ArrayList<ShowClientData> listOfUser) {
        this.listOfUser = listOfUser;
    }

    public ListOfUsers() {}
}

