package gameModuleTicTacToe;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.function.ObjDoubleConsumer;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeModule implements Runnable {
    private Player player;
    private TicTacToeModel model;
    private int opponentMove;

    private volatile boolean opponentPlayed = false;

    public TicTacToeModule(String player, boolean firstMove) {
        model = new TicTacToeModel();
        if (firstMove) {
            model.setSelfPlays();
        }
        else {
            model.setOpponentPlays();
        }
        if(!decidePlayer(player)) {
            System.out.println("Wrong Command");
        }
    }

    @Override
    public void run() {
        game();
    }

    private void game() {
        while (!model.gameOver()) {
            if (!model.opponentPlays()) {
                // move versturen naar de server (not implemented)
                // move verwerken in het model
                model.playMove(player.chooseMove());
            }
            else {
                // A boolean switches if recieveMove() method gets called
                // Simulates Receiving a move from the server
                waitForMove();

                // Play the move in the model
                model.playMove(opponentMove);
                // switch the boolean
                opponentPlayed = false;
            }
        }
    }
    private void waitForMove() {
        while(!opponentPlayed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }
    public synchronized void receiveMove(int move) {
        opponentMove = move;
        opponentPlayed = true;

    }
    private boolean decidePlayer(String player) {
        if (player.equals("HUMAN")) {
            this.player = new Human();
            return true;
        }
        if (player.equals("AI")) {
            this.player = new AI(model);
            return true;
        }
        return false;
    }
}
