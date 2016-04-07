package alphareversi.game.tictactoemodule;

import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.InterfaceGameModule;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeModule implements InterfaceGameModule {
    private Player player;
    private TicTacToeModel model;
    private String opponent;
    private SendMoveCommand lastCommand;

    /**
     * Constructor Module.
     */
    public TicTacToeModule(String player, boolean firstMove, String opponent) {
        model = new TicTacToeModel();
        this.opponent = opponent;
        if (!decidePlayer(player)) {
            System.out.println("Wrong Command");
        }
    }

    /**
     * Method for receiving a command.
     * @param command the command to receive
     */
    public void receive(RecvCommand command) {
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
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        SendMoveCommand command = new SendMoveCommand();
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
     * Decides if the string in the class constructor matches.
     * with on off the classes with the interface Player.
     */
    private boolean decidePlayer(String player) {
        if (player.equals("HUMAN")) {
            this.player = new Human();
            return true;
        }
        if (player.equals("AI")) {
            this.player = new ArtificialIntelligence(model);
            return true;
        }
        return false;
    }

    /**
     * checks if the game is over.
     * @return the boolean to check if a game is over
     */
    public boolean gameOver() {
        return model.gameOver();
    }
}
