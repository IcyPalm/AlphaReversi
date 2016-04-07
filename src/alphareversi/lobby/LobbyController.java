package alphareversi.lobby;

import java.io.IOException;
import java.util.Optional;

import alphareversi.Connection;
import alphareversi.Main;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.receive.RecvPlayerlistCommand;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by wouter on 24-3-2016. Controller for handling the Lobby input from the user
 */
public class LobbyController implements CommandListener {


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

    /**
     * Bind the labels to the textValues. Create the lobbyModel. Subscribe to the commandDispatcher
     */
    public void initialize() {
        model = new LobbyModel(playerList, gameList);
        createConfigurationDialog();
        usernameLabel.textProperty().bind(model.username);
        serverAddressLabel.textProperty().bind(model.serverAddress);
        Connection.getInstance().commandDispatcher.addListener(this);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * send to subscribe model only if a gameType is selected
     */
    @FXML
    private void subscribe() {
        Object selected = getSelectedGameObject();
        if (selected != null) {
            model.subscribe(selected);
        }
    }

    /**
     * Challenge player based on the selected player and gametype.
     */
    @FXML
    private void challengePlayer() {
        Object selectedGame = getSelectedGameObject();
        Player selectedPlayer = getSelectedPlayer();
        if (selectedGame != null && selectedPlayer != null && !selectedPlayer.username.getValue().equals(model.username.getValue())) {
            model.challengePlayer(selectedPlayer.getUsername(), getSelectedGameObject().toString());
        }
    }

    private Object getSelectedGameObject() {
        return gameList.getSelectionModel().getSelectedItem();
    }

    private Player getSelectedPlayer() {
        Object selected = playerList.getSelectionModel().getSelectedItem();
        return (Player) selected;
    }

    /**
     * Create dialog for incoming challenge
     */
    private void createIncomingChallengeDialog(RecvGameChallengeCommand challenge) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Incomming Match");
        alert.setHeaderText("We have an incomming match from " + challenge.getChallenger());
        alert.setContentText("Match from " + challenge.getChallenger() + " for the gametype: " + challenge.getGameType());

        ButtonType accept = new ButtonType("Accept");
        ButtonType decline = new ButtonType("Decline");

        alert.getButtonTypes().setAll(accept, decline);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == accept) {
            model.acceptMatch(challenge);
        } else if (result.get() == decline) {
            //Do nothing
        }
    }


    /**
     * Create dialog for configuration and login information
     */
    private void createConfigurationDialog() {
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
                    model.sendStartupCommands();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    exceptionMessage = exception.getMessage();
                }
            }
        }
    }


    /**
     * Process all incomming commands from the server
     */
    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvGamelistCommand) {
            model.setGameList(((RecvGamelistCommand) command).getGameList());
        } else if (command instanceof RecvPlayerlistCommand) {
            model.setPlayerList(((RecvPlayerlistCommand) command).getPlayerList());
        } else if (command instanceof RecvGameChallengeCommand) {
            //Create the dialog in the javaFX thread
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    createIncomingChallengeDialog((RecvGameChallengeCommand) command);
                }

            });
        }
    }
}