package alphareversi.lobby;

import alphareversi.Connection;
import alphareversi.Main;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyController implements CommandListener{


    Stage primaryStage;
    Main main;
    LobbyModel model;
    @FXML
    Label usernameLabel;
    @FXML
    Label serverAddressLabel;
    @FXML
    TableView playerList;

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void initialize() {
        model = new LobbyModel(playerList);
        createUsernameDialog();
        usernameLabel.textProperty().bind(model.username);
        serverAddressLabel.textProperty().bind(model.serverAddress);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    private void createUsernameDialog() {
        Connection connection = Connection.getInstance();

        LobbyTextInputDialog dialog = new LobbyTextInputDialog("AlphaReversi", "localhost:8080");
        dialog.setTitle("Login credentials");
        dialog.setHeaderText("Enter your details");
        dialog.setContentText("Please enter your name:");
        dialog.setServerText("Please enter server address:");

        dialog.initModality(Modality.WINDOW_MODAL);

        String exceptionMessage = "";

        while (!connection.getConnected()) {
            // Traditional way to get the response value.
            if (exceptionMessage.length() > 0) {
                dialog.setErrorMessage(exceptionMessage);
            }

            Optional<String[]> result = dialog.showAndWait();
            if (result.isPresent()) {
                model.setUsername(result.get()[0], usernameLabel);
                model.setServerAddress(result.get()[1], serverAddressLabel);
                try {
                    Connection.getInstance().startConnection(model.serverAddress.toString(),8080);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    exceptionMessage = exception.getMessage();
                }
            }
        }

/*// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> model.setUsername(name[], username));
    }
}*/
    }

    @Override
    public void commandReceived(RecvCommand command) {
        System.out.println(command.getAction());
    }
}