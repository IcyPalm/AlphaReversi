package alphareversi.game.tictactoemodule;

import alphareversi.game.AbstractGameModule;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeModule extends AbstractGameModule {
    private Player player;
    private TicTacToeModel model;

    /**
     * Constructor Module.
     */
    public TicTacToeModule(String player, boolean firstMove) {
        model = new TicTacToeModel();
        if (!decidePlayer(player)) {
            System.out.println("Wrong Command");
        }
    }

    public void start() {
        game();
    }

    /**
     * Runs the game by deciding if we play or the opponent plays.
     */
    private void game() {
        while (!model.gameOver()) {
            if (!model.opponentPlays()) {
                // move versturen naar de server (not implemented)
                // move verwerken in het model
                model.playMove(player.chooseMove());
            } else {
                // A boolean switches if recieveMove() method gets called
                // Simulates Receiving a move from the server
                waitForMove();
                // Play the move in the model
                model.playMove(opponentMove);
                // switch the boolean
                super.opponentPlayed = false;
            }
        }
    }

    /**
     * Wait till the method receiveMove gets called.
     */
    private void waitForMove() {
        while (!super.opponentPlayed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException error) {
                System.err.println(error.getMessage());
            }
        }
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
