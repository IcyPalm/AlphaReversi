package alphareversi.game.reversimodule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Robert on 8-4-2016.
 */
public class ReversiControllerFXTest extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        rootLayout = new BorderPane();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Reversi");
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReversiController.class.getResource("ReversiView.fxml"));
            BorderPane personOverview = (BorderPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
