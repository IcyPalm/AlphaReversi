package alphareversi.game.tictactoemodule;

import alphareversi.Connection;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.SendCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.GameModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeModule extends GameModule {
    private static final String[] playerTypes = {"HUMAN", "AI"};
    private static final String gameName = "Tic-tac-toe";
    private Player player;
    private TicTacToeModel model;
    private String opponent;

    private SendMoveCommand lastCommand;
    private String playerType;
    private BorderPane ticTacToeView;

    /**
     * Constructor Module.
     */
    public TicTacToeModule(String playerType, String opponent,
                           String playerToMove, String ourUsername, int turnTime) throws Exception {
        model = new TicTacToeModel();

        if (!opponent.equals(playerToMove)) {
            model.setSelfPlays();
        } else {
            model.setOpponentPlays();
        }

        this.opponent = opponent;
        if (!decidePlayer(playerType)) {
            System.out.println("Wrong Command");
        }

        Connection connection = Connection.getInstance();
        connection.commandDispatcher.addListener(this);
    }

    public static String[] getPlayerTypes() {
        return playerTypes;
    }

    public static String getGameName() {
        return gameName;
    }

    /**
     * Method for receiving a command.
     *
     * @param command the command to receive
     */
    public void commandReceived(RecvCommand command) {
        System.out.println("GameModule received a command " + command.getClass());
        if (command instanceof RecvGameMoveCommand) {
            RecvGameMoveCommand com = (RecvGameMoveCommand) command;
            System.out.println(com.getPlayer() + " : made move : " + com.getMove().toString());
            if (this.opponent.equals(((RecvGameMoveCommand) command).getPlayer())) {
                model.playMove(processMove((RecvGameMoveCommand) command));
            }
        } else if (command instanceof RecvGameYourturnCommand) {
            RecvGameYourturnCommand com = (RecvGameYourturnCommand) command;
            System.out.println("it is now my turn");
            if (player instanceof Human) {
                this.player.chooseMove();
            } else if (player instanceof ArtificialIntelligence) {
                int move = this.player.chooseMove();
                model.playMove(move);
                updateMoveCommand(move);
            }
        }
    }

    /**
     * Updates the current SendMoveCommand with the latest move.
     *
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        System.out.println("send command");
        Connection connection = Connection.getInstance();
        SendMoveCommand command = new SendMoveCommand(move);
        connection.sendMessage(command);
    }

    private int processMove(RecvGameMoveCommand command) {
        return Integer.parseInt(command.getMove());
    }

    /**
     * Decides if the string in the class constructor matches. with on off the classes with the
     * interface Player.
     */
    private boolean decidePlayer(String playerType) {
        if (playerType.equals("HUMAN")) {
            this.player = new Human();
            this.playerType = playerType;
            return true;
        }
        if (playerType.equals("AI")) {
            this.player = new ArtificialIntelligence(model);
            this.playerType = playerType;
            return true;
        }
        return false;
    }

    /**
     * checks if the game is over.
     *
     * @return the boolean to check if a game is over
     */
    public boolean gameOver() {
        return model.gameOver();
    }

    private BorderPane setTicTacToeView() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TicTacToeModule.class.getResource("ticTacToeView.fxml"));
        BorderPane ticTacToeView = (BorderPane) loader.load();

        TicTacToeViewController controller = loader.getController();
        setControllers(controller);

        return ticTacToeView;
    }

    private void setControllers(TicTacToeViewController controller) {
        model.setViewController(controller);
        controller.setTicTacToeModel(model, this.playerType, this.opponent);
        controller.setPlayer(player);
    }

    public String getPlayerType() {
        return playerType;
    }

    /**
     * initiate and create the view.
     * @return view
     */
    public BorderPane getView() {
        try {
            ticTacToeView = setTicTacToeView();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return ticTacToeView;
    }


}
