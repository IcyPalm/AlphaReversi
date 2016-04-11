package alphareversi.chat;

import alphareversi.Connection;
import alphareversi.commands.send.SendMessageCommand;
import alphareversi.lobby.Player;
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
    Connection connection;
    private ObservableList<Message> chatHistoryData;

    /**
     * Set connection and all gui elements.
     */
    public void start() {
        connection = Connection.getInstance();
        chatWith.setText(player.getUsername());
        chatHistoryData = player.getChatHistory();
        chatHistory.setItems(chatHistoryData);
        inputMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    sendMessage();
                }
            }
        });
    }
    

    public void setPlayer(Player player) {
        this.player = player;
        start();
    }

    /**
     * Send the message in the TextInput.
     */
    public void sendMessage() {
        String messageText = inputMessage.getText();
        player.addChatMessage("You", messageText);
        inputMessage.setText("");
        SendMessageCommand messageCommand =
                new SendMessageCommand(player.getUsername(), messageText);
        connection.sendMessage(messageCommand);
    }

    /**
     * Close the chat scene.
     * @param actionEvent actionEvent from the close button.
     */
    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
