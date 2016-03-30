package alphareversi.lobby;

import alphareversi.Main;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.*;

import java.util.Optional;


/**
 * Created by wouter on 24-3-2016.
 */
public class LobbyController {


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
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    private void createUsernameDialog() {
        LobbyTextInputDialog dialog = new LobbyTextInputDialog("AlphaReversi", "localhost:8080");
        dialog.setTitle("Login credentials");
        dialog.setHeaderText("Enter your details");
        dialog.setContentText("Please enter your name:");
        dialog.setServerText("Please enter server address:");
        dialog.initModality(Modality.WINDOW_MODAL);
        // Traditional way to get the response value.
        Optional<String[]> result = dialog.showAndWait();
        if (result.isPresent()) {
            model.setUsername(result.get()[0], usernameLabel);
            model.setServerAddress(result.get()[1], serverAddressLabel);
        }

/*// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> model.setUsername(name[], username));
    }
}*/
    }

}