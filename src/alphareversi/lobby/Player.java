package alphareversi.lobby;

import alphareversi.chat.Message;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Player class with chatMessages and chat information.
 * Created by wouter on 29-3-2016.
 */
public class Player {

    private SimpleStringProperty username;
    private ObservableList<Message> chatHistory = FXCollections.observableArrayList();
    private SimpleIntegerProperty messagesCount;

    public ObservableList<Message> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ObservableList<Message> chatHistory) {
        this.chatHistory = chatHistory;
    }

    /**
     * Add an chat message to the chat history.
     * @param messageText message text
     * @param from message sender
     */
    public void addChatMessage(String messageText, String from) {
        Message message = new Message(messageText, from);
        chatHistory.add(message);
        int count = messagesCount.getValue();
        count++;
        messagesCount.setValue(count);
    }

    Player(String username) {
        this.username = new SimpleStringProperty(username);
        messagesCount = new SimpleIntegerProperty(0);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }


    public int getMessagesCount() {
        return messagesCount.get();
    }

    public SimpleIntegerProperty messagesCountProperty() {
        return messagesCount;
    }

    public void setMessagesCount(int messagesCount) {
        this.messagesCount.set(messagesCount);
    }
}
