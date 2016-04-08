package alphareversi;

import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.commands.receive.RecvGameResultCommand;
import alphareversi.game.GameModule;
import alphareversi.game.tictactoemodule.TicTacToeModel;
import alphareversi.game.tictactoemodule.TicTacToeModule;
import alphareversi.game.tictactoemodule.TicTacToeViewController;
import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application implements CommandListener {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane lobbyView;
    private LobbyController lobbyController;
    private GameModule gameModule;

    public Main() {
    }

    public static void main(String[] args) {
        // AlphaReversi
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Connection connection = Connection.getInstance();
            connection.commandDispatcher.addListener(this);
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Tic Tac Toe");
            initRootLayout();
            initLobby();
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

    private void initLobby() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby/lobby.fxml"));

        lobbyView = (BorderPane) loader.load();

        lobbyController = loader.<LobbyController>getController();
        lobbyController.setMainApp(this);
    }

    public void showLobby() {
        rootLayout.setCenter(lobbyView);
    }

    public void startGame(RecvGameMatchCommand command) throws Exception {
        Connection connection = Connection.getInstance();
        gameModule = new TicTacToeModule("AI",command.getOpponent());

        rootLayout.setCenter(gameModule.getView());

        connection.commandDispatcher.addListener(gameModule);
    }

    private void stopGame(RecvGameResultCommand command) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Game Over");
        alert.setHeaderText("The game has ended.");
        alert.setContentText("");
        alert.showAndWait();

        gameModule = null;
        showLobby();
    }

    @Override
    public void commandReceived(RecvCommand command) {
        Platform.runLater(() -> {
            if (command instanceof RecvGameMatchCommand) {
                try {
                    startGame((RecvGameMatchCommand) command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }  else if (command instanceof RecvGameResultCommand) {
                try {
                    stopGame((RecvGameResultCommand) command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}