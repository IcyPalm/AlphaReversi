package alphareversi.chat;

import alphareversi.lobby.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by wouter on 7-4-2016.
 */
public class ChatWindowController {
    Player player;
    @FXML
    private Label chatWith;
    @FXML
    private TableView<Message> chatHistory;
    @FXML
    Button closeButton;
    @FXML
    TextField inputMessage;
    private ObservableList<Message> chatHistoryData;
    
    public void start() {
        chatWith.setText(player.getUsername());
        chatHistoryData = player.getChatHistory();
        chatHistory.setItems(chatHistoryData);
        inputMessage.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    sendMessage();
                }
            }
        });
    }
    

    public void setPlayer(Player player) {
        this.player = player;
        start();
    }

    public void sendMessage() {
        player.addChatMessage("You", inputMessage.getText());
        inputMessage.setText("");
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
