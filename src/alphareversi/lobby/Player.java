package alphareversi.lobby;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by wouter on 29-3-2016.
 */
public class Player {

    SimpleStringProperty username;

    Player(String username) {
        this.username = new SimpleStringProperty(username);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }
}
