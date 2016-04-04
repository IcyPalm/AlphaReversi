package alphareversi.lobby;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyModel {

    Player self;
    Label usernameLabel;
    TableView playerList;
    SimpleStringProperty serverAddress;


    LobbyModel(TableView playerList) {
        serverAddress = new SimpleStringProperty();
        this.playerList = playerList;
        addPlayer("henk");
        addPlayer("tieten");
    }

    private void addPlayer (String username) {
        ObservableList<Player> data = playerList.getItems();
        data.add(new Player(username));
    }

    public void setUsername(String username) {
        self = new Player(username);
    }

    public void setServerAddress(String username) {
        this.serverAddress.set(username);
    }

}
