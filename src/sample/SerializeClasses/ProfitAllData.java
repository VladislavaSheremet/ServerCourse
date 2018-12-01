package sample.SerializeClasses;

import java.io.Serializable;

public class ProfitAllData implements Serializable {

    private boolean profitAll;

    public boolean isProfitAll() {
        return profitAll;
    }

    public void setProfitAll(boolean profitAll) {
        this.profitAll = profitAll;
    }
}
