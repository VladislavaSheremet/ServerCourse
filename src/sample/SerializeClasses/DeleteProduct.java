package sample.SerializeClasses;

import java.io.Serializable;

public class DeleteProduct implements Serializable {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

