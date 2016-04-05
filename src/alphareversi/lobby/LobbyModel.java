package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.send.SendGetGamelistCommand;
import alphareversi.commands.send.SendGetPlayerlistCommand;
import alphareversi.commands.send.SendLoginCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyModel {


    TableView playerList;
    SimpleStringProperty serverAddress;
    SimpleStringProperty username;


    LobbyModel(TableView playerList) {
        serverAddress = new SimpleStringProperty();
        username = new SimpleStringProperty();
        this.playerList = playerList;
        addPlayer("henk");
        addPlayer("tieten");

    }

    public void requestServerLists() {
        SendLoginCommand loginCommand = new SendLoginCommand(username.getValue());
        System.out.println(loginCommand.toString());
        SendGetGamelistCommand getGameList = new SendGetGamelistCommand();
        Connection.getInstance().sendMessage(getGameList);
        SendGetPlayerlistCommand getPlayerList = new SendGetPlayerlistCommand();
        Connection.getInstance().sendMessage(getPlayerList);
    }

    private void addPlayer (String username) {
        ObservableList<Player> data = playerList.getItems();
        data.add(new Player(username));
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setServerAddress(String username) {
        this.serverAddress.set(username);
    }

}
