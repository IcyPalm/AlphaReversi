package alphareversi.chat;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by wouter on 7-4-2016.
 */
public class Message {

    final SimpleStringProperty from;
    final SimpleStringProperty messageText;

    /**
     * Set the message from and text.
     * @param from from player
     * @param message player message
     */
    public Message(String from, String message) {
        this.from = new SimpleStringProperty(from);
        this.messageText = new SimpleStringProperty(message);

    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getMessageText() {
        return messageText.get();
    }

    public SimpleStringProperty messageTextProperty() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText.set(messageText);
    }


}
