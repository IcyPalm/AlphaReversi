package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.send.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.ArrayList;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyModel {


    TableView playerList;
    SimpleStringProperty serverAddress;
    SimpleStringProperty username;
    ChoiceBox gameList;
    Connection connection;
    private int serverPort;
    private ArrayList oldPlayerList;


    LobbyModel(TableView playerList, ChoiceBox gameList) {
        serverAddress = new SimpleStringProperty();
        username = new SimpleStringProperty();
        this.playerList = playerList;
        this.gameList = gameList;
        this.connection = Connection.getInstance();
        oldPlayerList = new ArrayList();
        new Thread(new RequestCommands()).start();
    }

    public void requestServerLists() {
            SendLoginCommand loginCommand = new SendLoginCommand(username.getValue());
            connection.sendMessage(loginCommand);
            SendGetPlayerlistCommand getPlayerList = new SendGetPlayerlistCommand();
            connection.sendMessage(getPlayerList);
            SendGetGamelistCommand getGameList = new SendGetGamelistCommand();
            connection.sendMessage(getGameList);
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

    public void setGameList (ArrayList gameList) {
        ObservableList<SimpleStringProperty> data = FXCollections.observableArrayList(gameList);
        this.gameList.getItems().removeAll();
        this.gameList.getItems().addAll(data);
        this.gameList.getSelectionModel().select(0);
    }

    /**
     * Set the new playerList, but only if there is a difference with the old playerList.
     * @param playerList array with usernames
     */
    public void setPlayerList (ArrayList<String> playerList) {
        if (!playerList.equals(oldPlayerList)) {
            this.playerList.getItems().clear();
            playerList.forEach(this::addPlayer);
            oldPlayerList = playerList;
        }
    }

    /**
     * Send subscribe command with the selected game mode.
     * @param selected
     */
    public void subscribe(Object selected) {
        SendSubscribeCommand subscribe = new SendSubscribeCommand(selected.toString());
        connection.sendMessage(subscribe);
    }

    /**
     * send the challenge player command for a gametype
     * @param username
     * @param gameType
     */
    public void challengePlayer(String username, String gameType) {
        SendChallengeCommand challenge = new SendChallengeCommand(username,gameType);
        connection.sendMessage(challenge);
    }


    public void acceptMatch(RecvGameChallengeCommand challenge) {
        SendChallengeAcceptCommand acceptChallenge = new SendChallengeAcceptCommand(challenge.getChallengeNumber());
        connection.sendMessage(acceptChallenge);
        System.out.println(acceptChallenge.toString());
    }

    public void declineMatch(RecvGameChallengeCommand challenge) {
       // SendChallengeDeclineCommand declineChallenge = new SendChallengeDeclineCommand (challenge.getChallengeNumber());
    }

    public void setServerPort(String serverPort) {
        this.serverPort = Integer.parseInt(serverPort);
    }

    public int getServerPort () {
        return this.serverPort;
    }

    /**
     * Runnable inner Class that requests a new playerList every 5 seconds, if we are connected with the server.
     */
    private class RequestCommands implements Runnable{

        @Override
        public void run() {
            while (true) {
                if (connection.getConnected()) {
                    SendGetPlayerlistCommand getPlayerList = new SendGetPlayerlistCommand();
                    connection.sendMessage(getPlayerList);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
