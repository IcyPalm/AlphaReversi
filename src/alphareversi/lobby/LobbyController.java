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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    @FXML
    private ChoiceBox playAs;

    public void setMainApp(Main main) {
        this.main = main;
    }

    /**
     * Bind the labels to the textValues. Create the lobbyModel. Subscribe to the commandDispatcher
     */
    public void initialize() {
        model = new LobbyModel(playerList, gameList, playAs);
        createConfigurationDialog();
        usernameLabel.textProperty().bind(model.usernameProperty());
        serverAddressLabel.textProperty().bind(model.serverAddressProperty());
        Connection.getInstance().commandDispatcher.addListener(this);
        gameList.setOnAction(event -> {
            model.setGamePlayers(getAvailablePlayers(getSelectedGameObject().toString()));
        });
    }

    /**
     * If you had an incoming challenge you use that result and if you created one. You get that
     * result of the selectionBox.
     *
     * @return String result of the SelectionBox
     */
    public String getSelectedPlayerToPlay() {
        String result = model.getChallengePlayWithResult();
        if (result != null) {
            model.setChallengePlayWithResult(null);
        } else {
            result = playAs.getSelectionModel().getSelectedItem().toString();
        }
        return result;
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
            model.unsubscribe();
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
            createOutgoingChallengeDialog(selectedPlayer.getUsername(),
                    getSelectedGameObject().toString());
        }
    }

    private void createOutgoingChallengeDialog(String username, String gameType) {
        TextInputDialog dialog = new TextInputDialog("4");
        dialog.setTitle("Challenge " + username);
        dialog.setHeaderText("Challenge the player " + username + " for the game " + gameType);
        dialog.setContentText("Please enter the turn time:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(turnTime -> {
            int time = 2;
            try {
                time = Integer.parseInt(turnTime);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
            model.challengePlayer(username, gameType, time);
        });
    }

    private Object getSelectedGameObject() {
        return gameList.getSelectionModel().getSelectedItem();
    }

    private Player getSelectedPlayer() {
        Object selected = playerList.getSelectionModel().getSelectedItem();
        return (Player) selected;
    }

    public ObservableList<Player> getPlayerList() {
        return model.getPlayerList().getItems();
    }

    private String[] getAvailablePlayers(String serverGame) {
        return main.getGamesWithPlayers().get(serverGame);
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
                + " for the gametype: " + challenge.getGameType()
                + " with a turntime of " + challenge.getTurntime() + " seconds.");

        String[] choices = main.getGamesWithPlayers().get(challenge.getGameType());
        ChoiceBox playAs = new ChoiceBox();
        playAs.getItems().addAll(choices);
        playAs.getSelectionModel().select(1);
        Label playAsText = new Label("Select the player to play");
        playAsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        VBox vbox = new VBox();
        vbox.getChildren().add(playAsText);
        vbox.getChildren().add(playAs);
        vbox.setSpacing(5);

        ButtonType accept = new ButtonType("Accept");
        ButtonType decline = new ButtonType("Decline");
        alert.getButtonTypes().setAll(accept, decline);
        DialogPane dialog = alert.getDialogPane();
        dialog.setExpandableContent(vbox);
        dialog.expandedProperty().set(true);

        Optional result = alert.showAndWait();
        if (result.get() == accept) {
            model.setChallengePlayWithResult(
                    playAs.getSelectionModel().getSelectedItem().toString());
            model.acceptMatch(challenge);
        } else if (result.get() == decline) {
            //Do nothing
        }
    }


    /**
     * Create dialog for configuration and login information.
     */
    private void createConfigurationDialog() {
        LobbyTextInputDialog dialog =
                new LobbyTextInputDialog("AlphaReversi", "hanzegameserver.nl", 7789);
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
                    Connection.getInstance().startConnection(
                            model.getServerAddress(), model.getServerPort());
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
                if (subscribeAlert != null) {
                    subscribeAlert.close();
                }
            }
        });
    }

    public void openChat(ActionEvent actionEvent) {
        this.main.createChatWindow();
    }

}
