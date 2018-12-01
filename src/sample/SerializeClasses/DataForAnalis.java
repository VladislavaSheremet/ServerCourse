package sample.SerializeClasses;

import java.io.Serializable;

public class DataForAnalis implements Serializable {

    private int id;
    private String name;
    private int kl;

    public int getKl() {
        return kl;
    }

    public void setKl(int kl) {
        this.kl = kl;
    }

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

}
