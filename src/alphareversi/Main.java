package alphareversi;

import java.io.IOException;

import alphareversi.chat.ChatController;
import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by wouter on 16-3-2016.
 */

public class Main extends Application {
    static Scene baseScene;
    static Stage primaryStage;
    LobbyController lobbyController;

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby/lobby.fxml"));
        Parent root = loader.load();

        this.primaryStage = primaryStage;
        baseScene = new Scene(root);
        primaryStage.setScene(baseScene);

        lobbyController =
                loader.<LobbyController>getController();
        lobbyController.setMainApp(this);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void createChatWindow() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat/chat.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Chat");
            stage.setScene(new Scene(root, 600, 500));
            stage.show();

            ChatController chatController =
                    loader.<ChatController>getController();

            chatController.setPlayerList(lobbyController.getPlayerList());

/*            //hide this current window (if this is whant you want
            ((Node)(event.getSource())).getScene().getWindow().hide();*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(Main.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(page, 700, 450);
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(page);
        }
        primaryStage.sizeToScene();
        return page;
    }

    public static void main(String[] args) {
        launch(args);
    }
}