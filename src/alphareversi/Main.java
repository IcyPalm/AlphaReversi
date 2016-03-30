package alphareversi;

import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by wouter on 16-3-2016.
 */

public class Main extends Application {
    static Scene baseScene;
    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby/lobby.fxml"));
        Parent root = loader.load();

        this.primaryStage = primaryStage;
        baseScene = new Scene(root);
        primaryStage.setScene(baseScene);

        LobbyController controller =
                loader.<LobbyController>getController();
        controller.setMainApp(this);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}