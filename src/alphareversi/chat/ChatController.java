package alphareversi.chat;

import alphareversi.Connection;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvMessageCommand;
import alphareversi.lobby.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by wouter on 7-4-2016.
 */
public class ChatController implements CommandListener {

    private ObservableList<Player> playerListdata = FXCollections.observableArrayList();
    private Connection connection;
    private HashMap<Player, Stage> chatWindows;

    @FXML
    private TableView<Player> playerList;

    /**
     * Initialize the chatController. Set the connection and register with commandDispatcher and Set
     * actionEvent on the Table
     */
    public void initialize() {
        connection = Connection.getInstance();
        connection.commandDispatcher.addListener(this);
        playerList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                createChatWindow(playerList.getSelectionModel().getSelectedItem());
            }
        });
        chatWindows = new HashMap<>();
    }

    public void setPlayerList(ObservableList<Player> playerList) {
        this.playerListdata = playerList;
        this.playerList.setItems(playerListdata);
    }

    private void createChatWindow(Player player) {
        Parent root;
        if (chatWindows.get(player) == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Chat with " + player.getUsername());
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
                stage.requestFocus();
                chatWindows.put(player, stage);

                ChatWindowController chatWindowController =
                        loader.<ChatWindowController>getController();
                chatWindowController.setPlayer(player);

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            chatWindows.get(player).show();
        }
    }


    private Player findPlayer(RecvMessageCommand command) {
        for (int i = 0; i < playerList.getItems().size(); i++) {
            Player player = playerList.getItems().get(i);
            if (player.getUsername().equals(command.getPlayer())) {
                return player;
            }
        }
        return null;
    }


    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvMessageCommand) {
            RecvMessageCommand messageCommand = (RecvMessageCommand) command;
            Player player = findPlayer((RecvMessageCommand) command);
            if (player != null) {
                player.addChatMessage(messageCommand.getPlayer(), messageCommand.getMessage());
            }

        }
    }
}
