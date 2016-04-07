package alphareversi.chat;

import alphareversi.lobby.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by wouter on 7-4-2016.
 */
public class ChatWindowController {
    Player player;
    @FXML
    private Label chatWith;
    @FXML
    private TableView<SimpleStringProperty> chatHistory;
    @FXML
    Button closeButton;
    private ObservableList<SimpleStringProperty> chatHistoryData;
    
    public void start() {
        chatWith.setText(player.getUsername());
        chatHistoryData = player.getChatHistory();
        chatHistory.setItems(chatHistoryData);
    }
    

    public void setPlayer(Player player) {
        this.player = player;
        start();
    }

    public void sendMessage(ActionEvent actionEvent) {
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
