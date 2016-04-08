package alphareversi;

import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.game.tictactoemodule.TicTacToeModel;
import alphareversi.game.tictactoemodule.TicTacToeViewController;
import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application implements CommandListener {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public Main() {
    }

    public static void main(String[] args) {
        // AlphaReversi
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Tic Tac Toe");
            initRootLayout();
            showLobby();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("game/tictactoemodule/rootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLobby() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby/lobby.fxml"));
        BorderPane view = (BorderPane) loader.load();

        rootLayout.setCenter(view);

        LobbyController controller = loader.<LobbyController>getController();
        controller.setMainApp(this);
    }

    public void startGame(RecvGameMatchCommand command) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("game/tictactoemodule/ticTacToeView.fxml"));
        BorderPane ticTacToeView = (BorderPane) loader.load();

        rootLayout.setCenter(ticTacToeView);

        TicTacToeModel ticTacToeModel = new TicTacToeModel();

        TicTacToeViewController controller = loader.getController();
        ticTacToeModel.setViewController(controller);
        controller.setTicTacToeModel(ticTacToeModel);
    }

    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvGameMatchCommand) {
            try {
                startGame((RecvGameMatchCommand) command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}