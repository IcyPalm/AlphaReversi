package alphareversi;

import alphareversi.chat.ChatController;
import alphareversi.commands.CommandListener;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMatchCommand;
import alphareversi.commands.receive.RecvGameResultCommand;
import alphareversi.commands.receive.RecvStatusErrCommand;
import alphareversi.game.GameModule;
import alphareversi.game.reversimodule.ReversiModule;
import alphareversi.game.tictactoemodule.TicTacToeModule;
import alphareversi.lobby.LobbyController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application implements CommandListener {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private BorderPane lobbyView;
    private LobbyController lobbyController;
    private GameModule gameModule;
    private Stage chatStage;
    private ArrayList availableGames;
    private HashMap<String, Class> gameNameWithClass;
    private HashMap<String, String[]> gamesWithPlayers;

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
            this.primaryStage.setTitle("Lobby");
            initRootLayout();
            initLobby();
            showLobby();
        } catch (Exception error) {
            error.printStackTrace();
        }
        gamesWithPlayers = new HashMap<>();
        gamesWithPlayers.put(TicTacToeModule.getGameName(), TicTacToeModule.getPlayerTypes());
        gamesWithPlayers.put(ReversiModule.getGameName(), ReversiModule.getPlayerTypes());

        gameNameWithClass = new HashMap<>();
        gameNameWithClass.put(TicTacToeModule.getGameName(), TicTacToeModule.class);
        gameNameWithClass.put(ReversiModule.getGameName(), ReversiModule.class);
    }

    private void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("game/tictactoemodule/rootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HashMap<String, String[]> getGamesWithPlayers() {
        return gamesWithPlayers;
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

    /**
     * Create the chat window and set the playerlist from the lobby controller.
     */
    public void createChatWindow() {

        if (chatStage == null) {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("chat/chat.fxml"));
                root = loader.load();
                chatStage = new Stage();
                chatStage.setTitle("Chat");
                chatStage.setScene(new Scene(root, 600, 500));
                chatStage.show();

                ChatController chatController =
                        loader.<ChatController>getController();

                chatController.setPlayerList(lobbyController.getPlayerList());

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            chatStage.show();
            chatStage.requestFocus();
        }
    }

    /**
     * Method for starting a game.
     * @param command The command for starting a game.
     * @throws Exception A regular exception.
     */
    public void startGame(RecvGameMatchCommand command) throws Exception {
        Connection connection = Connection.getInstance();
        Class game = gameNameWithClass.get(command.getGametype());
        Constructor<?> cons = game.getConstructor(String.class,String.class,String.class);
        gameModule = (GameModule) cons.newInstance(lobbyController.getSelectedPlayerToPlay(),command.getOpponent(),command.getPlayerToMove());
        /*
        gameModule = new TicTacToeModule(lobbyController.getSelectedPlayerToPlay(),command.getOpponent(),command.getPlayerToMove());
*/
        rootLayout.setCenter(gameModule.getView());

        connection.commandDispatcher.addListener(gameModule);
    }

    private void stopGame(RecvGameResultCommand command) throws Exception {
        Connection connection = Connection.getInstance();
        connection.commandDispatcher.removeListener(gameModule);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Game Over");
        alert.setHeaderText("The game has ended. You have " + command.getType());
        alert.setContentText(command.getComment());
        alert.showAndWait();

        gameModule = null;
        showLobby();
    }

    private void createErrorDialog(RecvStatusErrCommand errorCommand) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error! Helemaal knethaa");
        alert.setHeaderText("ERROR");
        alert.setContentText(errorCommand.getReason());
        alert.showAndWait();
    }

    @Override
    public void commandReceived(RecvCommand command) {
        Platform.runLater(() -> {
            if (command instanceof RecvGameMatchCommand) {
                try {
                    startGame((RecvGameMatchCommand) command);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }  else if (command instanceof RecvGameResultCommand) {
                try {
                    stopGame((RecvGameResultCommand) command);
                } catch (Exception error) {
                    error.printStackTrace();
                }
            } else if (command instanceof RecvStatusErrCommand) {
                createErrorDialog((RecvStatusErrCommand) command);
            }
        });
    }
//    private void showTicTacToeView() throws Exception{
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("game/tictactoemodule/ticTacToeView.fxml"));
//        BorderPane ticTacToeView = (BorderPane) loader.load();
//
//        rootLayout.setCenter(ticTacToeView);
//
//        TicTacToeViewController controller = loader.getController();
//        ticTacToeModel.setViewController(controller);
//        controller.setReversiModel(ticTacToeModel);
//    }

}