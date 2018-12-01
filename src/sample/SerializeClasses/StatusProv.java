package sample.SerializeClasses;

import java.io.Serializable;

public class StatusProv implements Serializable {

    String login;
    boolean k;

    public boolean isK(boolean k) {
        return this.k;
    }

    public void setK(boolean k) {
        this.k = k;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) {
        this.login = login;
    }
}
