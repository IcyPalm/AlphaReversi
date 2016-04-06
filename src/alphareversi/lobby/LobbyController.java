package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.Main;
import alphareversi.commands.CommandDispatcher;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.receive.RecvPlayerlistCommand;
import alphareversi.commands.send.SendGetGamelistCommand;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyController implements CommandListener{


    Stage primaryStage;
    Main main;
    LobbyModel model;
    @FXML
    Label usernameLabel;
    @FXML
    Label serverAddressLabel;
    @FXML
    TableView playerList;
    @FXML
    ChoiceBox gameList;

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void initialize() {
        model = new LobbyModel(playerList,gameList);
        createUsernameDialog();
        usernameLabel.textProperty().bind(model.username);
        serverAddressLabel.textProperty().bind(model.serverAddress);
        Connection.getInstance().commandDispatcher.addListener(this);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    private void subscribe () {
        Object selected = getSelectedGameObject();
        if (selected != null) {
            model.subscribe(selected);
        }
    }

    @FXML
    private void challengePlayer () {
        Object selectedGame = getSelectedGameObject();
        Player selectedPlayer = getSelectedPlayer();
        if (selectedGame != null && selectedPlayer != null) {
            model.challengePlayer(selectedPlayer.getUsername(), getSelectedGameObject().toString());
        }
    }

    private Object  getSelectedGameObject () {
            return gameList.getSelectionModel().getSelectedItem();
    }

    private Player getSelectedPlayer () {
        Object selected = playerList.getSelectionModel().getSelectedItem();
        return (Player) selected;
    }

    private void createIncomingChallengeDialog(RecvGameChallengeCommand challenge) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Incomming Match");
        alert.setHeaderText("We have an incomming match from " + challenge.getChallenger());
        alert.setContentText("We have an incomming match from " + challenge.getChallenger() + "for the gametype m" + challenge.getGameType());

        ButtonType accept = new ButtonType("Accept");
        ButtonType decline = new ButtonType("Decline");

        alert.getButtonTypes().setAll(accept, decline);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == accept){
            model.acceptMatch(challenge);
        } else if (result.get() == decline) {
            //model.declineMatch(challenge);
        }
    }

    private void createUsernameDialog() {
        Connection connection = Connection.getInstance();

        LobbyTextInputDialog dialog = new LobbyTextInputDialog("AlphaReversi", "localhost", 7789);
        dialog.setTitle("Login credentials");
        dialog.setHeaderText("Enter your details");
        dialog.setContentText("Please enter your name:");
        dialog.setServerText("Please enter server address:");
        dialog.setPortText("Please enter server port");

        dialog.initModality(Modality.WINDOW_MODAL);

        String exceptionMessage = "";

        while (!connection.getConnected()) {
            // Traditional way to get the response value.
            if (exceptionMessage.length() > 0) {
                dialog.setErrorMessage(exceptionMessage);
            }

            Optional<String[]> result = dialog.showAndWait();
            if (result.isPresent()) {
                model.setUsername(result.get()[0]);
                model.setServerAddress(result.get()[1]);
                model.setServerPort(result.get()[2]);
                try {
                    Connection.getInstance().startConnection(model.serverAddress.getValue(), model.getServerPort());
                    model.requestServerLists();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    exceptionMessage = exception.getMessage();
                }
            }
        }

/*// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> model.setUsername(name[], username));
    }
}*/
    }

    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvGamelistCommand) {
            model.setGameList(((RecvGamelistCommand) command).getGameList());
        } else if (command instanceof RecvPlayerlistCommand) {
            model.setPlayerList(((RecvPlayerlistCommand) command).getPlayerList());
        } else if (command instanceof RecvGameChallengeCommand) {
            System.out.println(command.toString());
            createIncomingChallengeDialog((RecvGameChallengeCommand) command);

        }
    }
}