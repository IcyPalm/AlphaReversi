package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.Main;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameChallengeCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.receive.RecvPlayerlistCommand;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;






/**
 * Created by wouter on 24-3-2016. Controller for handling the Lobby input from the user
 */
public class LobbyController implements CommandListener {


    private Stage primaryStage;
    private Main main;
    private LobbyModel model;
    private Alert subscribeAlert;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label serverAddressLabel;
    @FXML
    private TableView playerList;
    @FXML
    private ChoiceBox gameList;

    public void setMainApp(Main main) {
        this.main = main;
    }

    /**
     * Bind the labels to the textValues. Create the lobbyModel. Subscribe to the commandDispatcher
     */
    public void initialize() {
        model = new LobbyModel(playerList, gameList);
        createConfigurationDialog();
        usernameLabel.textProperty().bind(model.usernameProperty());
        serverAddressLabel.textProperty().bind(model.serverAddressProperty());
        Connection.getInstance().commandDispatcher.addListener(this);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * send to subscribe model only if a gameType is selected.
     */
    @FXML
    private void subscribe() {
        Object selected = getSelectedGameObject();
        if (selected != null) {
            model.subscribe(selected);
            createSubscribeDialog(selected.toString());
        }
    }

    private void createSubscribeDialog(String gameType) {
        subscribeAlert = new Alert(Alert.AlertType.INFORMATION);
        subscribeAlert.setTitle("Subscription");
        subscribeAlert.setHeaderText("Your are subscribed to " + gameType);
        subscribeAlert.setContentText("Searching games.. Keep this dialog open.");
        subscribeAlert.showAndWait();

        subscribeAlert.setOnCloseRequest(event -> {
            model.unsubscirbe();
        });
    }

    /**
     * Challenge player based on the selected player and gametype.
     */
    @FXML
    private void challengePlayer() {
        Object selectedGame = getSelectedGameObject();
        Player selectedPlayer = getSelectedPlayer();
        if (selectedGame != null && selectedPlayer != null
                && !selectedPlayer.getUsername().equals(model.getUsername())) {
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

    public ObservableList<Player> getPlayerList () {
        return model.getPlayerList().getItems();
    }

    /**
     * Create dialog for incoming challenge.
     */
    private void createIncomingChallengeDialog(RecvGameChallengeCommand challenge) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Incomming Match");
        alert.setHeaderText("We have an incomming match from " + challenge.getChallenger());
        alert.setContentText("Match from " + challenge.getChallenger()
                + " for the gametype: " + challenge.getGameType());

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
     * Create dialog for configuration and login information.
     */
    private void createConfigurationDialog() {
        LobbyTextInputDialog dialog = new LobbyTextInputDialog("AlphaReversi", "hanzegameserver.nl", 7789);
        dialog.setTitle("Login credentials");
        dialog.setHeaderText("Enter your details");
        dialog.setContentText("Please enter your name:");
        dialog.setServerText("Please enter server address:");
        dialog.setPortText("Please enter server port");

        dialog.initModality(Modality.WINDOW_MODAL);

        Connection connection = Connection.getInstance();

        while (!connection.getConnected()) {
            Optional<String[]> result = dialog.showAndWait();
            result.ifPresent(data -> {
                model.setUsername(data[0]);
                model.setServerAddress(data[1]);
                model.setServerPort(data[2]);
                try {
                    Connection.getInstance().startConnection(model.getServerAddress(),
                            model.getServerPort());
                    model.sendStartupCommands();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    dialog.setErrorMessage(exception.getMessage());
                }
            });
        }
    }


    /**
     * Process all incomming commands from the server.
     */
    @Override
    public void commandReceived(RecvCommand command) {
        Platform.runLater(() -> {
        if (command instanceof RecvGamelistCommand) {
            model.setGameList(((RecvGamelistCommand) command).getGameList());
        } else if (command instanceof RecvPlayerlistCommand) {
            model.setPlayerList(((RecvPlayerlistCommand) command).getPlayerList());
        } else if (command instanceof RecvGameChallengeCommand) {
            //Create the dialog in the javaFX thread
                createIncomingChallengeDialog((RecvGameChallengeCommand) command);
        } else if (command instanceof RecvGameMatchCommand) {
            if (subscribeAlert != null){
                subscribeAlert.close();
            }
        }
        });
    }

    public void openChat(ActionEvent actionEvent) {
        this.main.createChatWindow();
    }

}
