package alphareversi.chat;

import java.io.IOException;

import alphareversi.Connection;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGamelistCommand;
import alphareversi.commands.receive.RecvPlayerlistCommand;
import alphareversi.lobby.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by wouter on 7-4-2016.
 */
public class ChatController implements CommandListener{

    private ObservableList<Player> playerListdata;
    private Connection connection;

    @FXML
    private TableView<Player> playerList;

    public void initialize() {
        connection = Connection.getInstance();
        connection.commandDispatcher.addListener(this);
        playerList.setOnMouseClicked(event -> { if (event.getClickCount() == 2) {
            createChatWindow(playerList.getSelectionModel().getSelectedItem());
        }
    });
    }

    public void setPlayerList(ObservableList<Player> playerList) {
        this.playerListdata = playerList;
    }

    private void createChatWindow(Player player) {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Chat with " + player.getUsername());
                stage.setScene(new Scene(root, 600, 400));
                stage.show();

                ChatWindowController chatWindowController =
                        loader.<ChatWindowController>getController();

                chatWindowController.setPlayer(player);

/*            //hide this current window (if this is whant you want
            ((Node)(event.getSource())).getScene().getWindow().hide();*/

            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvPlayerlistCommand) {
            playerList.setItems(playerListdata);
        }
    }
}
