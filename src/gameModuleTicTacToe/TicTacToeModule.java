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

    public TicTacToeModule(String player) {
        model = new TicTacToeModel();
        model.setSelfPlays();
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
            model.playMove(player.chooseMove());

            waitForMove();
            model.playMove(opponentMove);
            opponentPlayed = false;
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
