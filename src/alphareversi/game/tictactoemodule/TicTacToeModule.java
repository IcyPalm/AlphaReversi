package alphareversi.game.tictactoemodule;

import alphareversi.commands.RecvCommand;
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
    private Player player;
    private TicTacToeModel model;
    private String opponent;

    private SendMoveCommand lastCommand;
    private String playerType;
    private BorderPane ticTacToeView;

    /**
     * Constructor Module.
     */
    public TicTacToeModule(String playerType, String opponent, String beginningPlayer) throws Exception {
        model = new TicTacToeModel();
        this.opponent = opponent;
        if (!decidePlayer(playerType)) {
            System.out.println("Wrong Command");
        }
        decideWhoBegin(beginningPlayer);
        ticTacToeView = setTicTacToeView();
    }

    /**
     * Method for receiving a command.
     *
     * @param command the command to receive
     */
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvGameMoveCommand) {
            System.out.println(((RecvGameMoveCommand) command).getPlayer());
            if (this.opponent == ((RecvGameMoveCommand) command).getPlayer()) {
                model.playMove(processMove((RecvGameMoveCommand) command));
            }
        } else if (command instanceof RecvGameYourturnCommand) {
            int move = this.player.chooseMove();
            model.playMove(move);
            updateMoveCommand(move);
        }
    }

    /**
     * Updates the current SendMoveCommand with the latest move.
     *
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        SendMoveCommand command = new SendMoveCommand(move);
        command.setMove(move);
        this.lastCommand = command;
    }

    @Override
    public SendMoveCommand send(SendMoveCommand command) {
        return lastCommand;
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
     * Decide who begins
     */
    private void decideWhoBegin(String beginningPlayer) {

    }

    /**
     * checks if the game is over.
     *
     * @return the boolean to check if a game is over
     */
    public boolean gameOver() {
        return model.gameOver();
    }

    public String[] getPlayerTypes() {
        return playerTypes;
    }

    private BorderPane setTicTacToeView() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TicTacToeModule.class.getResource("ticTacToeView.fxml"));
        BorderPane ticTacToeView = (BorderPane) loader.load();

        TicTacToeViewController controller = loader.getController();
        model.setViewController(controller);
        controller.setTicTacToeModel(model);

        return ticTacToeView;
    }

    public String getPlayerType() {
        return playerType;
    }

    public BorderPane getView() {
        return ticTacToeView;
    }
}
