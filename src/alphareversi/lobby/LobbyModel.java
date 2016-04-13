package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.send.SendChallengeAcceptCommand;
import alphareversi.commands.send.SendChallengeCommand;
import alphareversi.commands.send.SendGetGamelistCommand;
import alphareversi.commands.send.SendGetPlayerlistCommand;
import alphareversi.commands.send.SendLoginCommand;
import alphareversi.commands.send.SendSubscribeCommand;
import alphareversi.commands.send.SendUnsubscribeCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyModel {


    private final ChoiceBox playAs;
    private final SimpleStringProperty serverAddress;
    private final SimpleStringProperty username;
    private TableView playerList;
    private ChoiceBox gameList;
    private Connection connection;
    private int serverPort;
    private String challengePlayWithResult;

    private boolean inGame;

    /**
     * Set the TableView playerList, ChoiceBox gameList, Connection. Create a new thread for
     * refreshing playerList
     */
    LobbyModel(TableView playerList, ChoiceBox gameList, ChoiceBox playAs) {
        challengePlayWithResult = null;
        serverAddress = new SimpleStringProperty();
        username = new SimpleStringProperty();
        this.playerList = playerList;
        this.gameList = gameList;
        this.playAs = playAs;
        this.connection = Connection.getInstance();
        new Thread(new RequestPlayerList()).start();
    }

    /**
     * Send initial server commands to server login, getPlayerList and getGameList.
     */
    public void sendStartupCommands() {
        SendLoginCommand loginCommand = new SendLoginCommand(username.getValue());
        connection.sendMessage(loginCommand);
        SendGetPlayerlistCommand getPlayerList = new SendGetPlayerlistCommand();
        connection.sendMessage(getPlayerList);
        SendGetGamelistCommand getGameList = new SendGetGamelistCommand();
        connection.sendMessage(getGameList);
    }

    /**
     * Add a player to the playerList TableView.
     */
    private void addPlayer(String username) {
        ObservableList<Player> data = playerList.getItems();
        data.add(new Player(username));
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setServerAddress(String username) {
        this.serverAddress.set(username);
    }

    /**
     * set the gameList and remove the old data.
     */
    public void setGameList(ArrayList gameList) {
        ObservableList<SimpleStringProperty> data = FXCollections.observableArrayList(gameList);
        this.gameList.getItems().clear();
        this.gameList.getItems().addAll(data);
        this.gameList.getSelectionModel().select(0);
    }

    /**
     * Check the old playerList and compare with to new one.
     * This only makes new players when necessary;
     *
     * @param playerList array with userNames
     */
    public void setPlayerList(ArrayList<String> playerList) {
        ObservableList newList = FXCollections.observableArrayList();
        ObservableList list = this.playerList.getItems();
        Iterator<String> iterator = playerList.iterator();
        while (iterator.hasNext() && list.size() != 0) {
            String name = iterator.next();
            for (Object object : list) {
                Player player = (Player) object;
                if (player.getUsername().equals(name)) {
                    newList.add(player);
                    iterator.remove();
                    break;
                }
            }

        }
        for (int i = 0; i < playerList.size(); i++) {
            Player player = new Player(playerList.get(i));
            newList.add(player);
        }
        list.setAll(newList);
    }

    /**
     * Send subscribe command with the selected game mode.
     */
    public void subscribe(Object selected) {
        SendSubscribeCommand subscribe = new SendSubscribeCommand(selected.toString());
        connection.sendMessage(subscribe);
    }

    /**
     * send the challenge player command for a gametype.
     */
    public void challengePlayer(String username, String gameType, int turnTime) {
        SendChallengeCommand challenge = new SendChallengeCommand(username, gameType, turnTime);
        connection.sendMessage(challenge);
    }


    /**
     * Send the accept challenge command.
     */
    public void acceptMatch(RecvGameChallengeCommand challenge) {
        SendChallengeAcceptCommand acceptChallenge
                = new SendChallengeAcceptCommand(challenge.getChallengeNumber());
        connection.sendMessage(acceptChallenge);
        System.out.println(acceptChallenge.toString());
    }

    public TableView getPlayerList() {
        return this.playerList;
    }


    public void setServerPort(String serverPort) {
        this.serverPort = Integer.parseInt(serverPort);
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void unsubscribe() {
        SendUnsubscribeCommand command = new SendUnsubscribeCommand();
        connection.sendMessage(command);
    }

    /**
     * Set the game players.
     *
     * @param gamePlayers Players from games
     */
    public void setGamePlayers(String[] gamePlayers) {
        ObservableList<String> data = FXCollections.observableArrayList();
        playAs.getItems().clear();
        for (int i = 0; i < gamePlayers.length; i++) {
            data.add(gamePlayers[i]);
        }
        playAs.getItems().setAll(data);
        playAs.getSelectionModel().select(1);
    }

    public String getChallengePlayWithResult() {
        return challengePlayWithResult;
    }

    public void setChallengePlayWithResult(String challengePlayWithResult) {
        this.challengePlayWithResult = challengePlayWithResult;
    }

    /**
     * Runnable inner Class that requests a new playerList every 5 seconds, if we are connected with
     * the server.
     */
    private class RequestPlayerList implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (connection.getConnected()) {
                    SendGetPlayerlistCommand getPlayerList = new SendGetPlayerlistCommand();
                    connection.sendMessage(getPlayerList);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public String getServerAddress() {
        return serverAddress.get();
    }

    public SimpleStringProperty serverAddressProperty() {
        return serverAddress;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
