package alphareversi.game.reversimodule;

import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.GameModule;
import alphareversi.game.tictactoemodule.Player;
import javafx.scene.layout.BorderPane;

/**
 * Created by Robert on 7-4-2016.
 */
public class ReversiModule extends GameModule {
    private static final String[] playerTypes = {"HUMAN", "AI"};
    private Player player;
    private ReversiModel model;
    private String opponent;
    private SendMoveCommand lastCommand;
    private int[][] board;
    private static final String gameName = "Reversi";
    /**
     * Reversi module for sever communication.
     * @param reversiModel The model that is based to the Module.
     * @param opponent The opponent's name.
     */
    public ReversiModule(ReversiModel reversiModel,  String opponent) {
        this.model = reversiModel;
        this.opponent = opponent;
        this.board = model.getBoard();
    }



    @Override
    public boolean gameOver() {
        return model.gameOver(board);
    }

    /**
     * TODO: NEEDS WRITING.
     * @param command somethingsomething
     */
    public void receive(RecvCommand command) {
        if (command instanceof RecvGameMoveCommand) {
            System.out.println(((RecvGameMoveCommand) command).getPlayer());
            if (this.opponent == ((RecvGameMoveCommand) command).getPlayer()) {
                model.placePiece(processMove((RecvGameMoveCommand) command),
                        model.getPlayerOnTurn() );
            }
        } else if (command instanceof RecvGameYourturnCommand) {
            int move = this.player.chooseMove();
            model.placePiece(move, model.getPlayerOnTurn());
            updateMoveCommand(move);
        }
    }

    /**
     * Updates the current SendMoveCommand with the latest move.
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        SendMoveCommand command = new SendMoveCommand(move);
        command.setMove(move);
        this.lastCommand = command;
    }

    private int processMove(RecvGameMoveCommand command) {
        return Integer.parseInt(command.getMove());
    }

    @Override
    public SendMoveCommand send(SendMoveCommand command) {
        //TODO: NEEDS IMPLEMENTING!
        return lastCommand;
    }

    @Override
    public BorderPane getView() {
        //TODO: NEEDS IMPLEMENTING!
        return null;
    }

    @Override
    public void commandReceived(RecvCommand command) {
        //TODO: NEEDS IMPLEMENTING!
    }

    public static String[] getPlayerTypes() {
        return playerTypes;
    }

    public static String getGameName() {
        return gameName;
    }
}
