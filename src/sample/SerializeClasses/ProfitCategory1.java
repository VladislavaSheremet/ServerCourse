package sample.SerializeClasses;

import java.io.Serializable;

public class ProfitCategory1 implements Serializable {

    private boolean profitCategory1;
    private int category;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isProfitCategory1() {
        return profitCategory1;
    }

    public void setProfitCategory1(boolean profitCategory1) {
        this.profitCategory1 = profitCategory1;
    }
}
