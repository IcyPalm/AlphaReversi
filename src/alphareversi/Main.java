package alphareversi;

import java.io.IOException;

import alphareversi.chat.ChatController;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.commands.receive.RecvStatusErrCommand;
import alphareversi.game.tictactoemodule.TicTacToeModel;
import alphareversi.game.tictactoemodule.TicTacToeViewController;
import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application implements CommandListener {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private LobbyController lobbyController;

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

        lobbyController = loader.<LobbyController>getController();
        lobbyController.setMainApp(this);
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

    @Override
    public void commandReceived(RecvCommand command) {
        Platform.runLater(() -> {
            if (command instanceof RecvGameMatchCommand) {
                try {
                    startGame((RecvGameMatchCommand) command);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (command instanceof RecvStatusErrCommand) {
                createErrorDialog((RecvStatusErrCommand) command);
            }
        });
    }

    private void createErrorDialog(RecvStatusErrCommand command) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error! Helemaal Ketteaa");
        alert.setHeaderText("Error");
        alert.setContentText(command.getReason());

        alert.showAndWait();
    }

}