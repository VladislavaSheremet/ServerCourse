package sample.SerializeClasses;

import java.io.Serializable;

public class DataForGistogram implements Serializable {

    private int id;
    private String name;
    private double result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
