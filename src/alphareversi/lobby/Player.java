package alphareversi.lobby;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;

/**
 * Created by wouter on 29-3-2016.
 */
public class Player {

    private SimpleStringProperty username;
    private ObservableList<SimpleStringProperty> chatHistory;
    private SimpleIntegerProperty messages;

    public ObservableList<SimpleStringProperty> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ObservableList<SimpleStringProperty> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addChatMessage (String message) {
        chatHistory.add(new SimpleStringProperty(message));
    }

    Player(String username) {
        this.username = new SimpleStringProperty(username);
         messages = new SimpleIntegerProperty(0);
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


    public int getMessages() {
        return messages.get();
    }

    public SimpleIntegerProperty messagesProperty() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages.set(messages);
    }
}
