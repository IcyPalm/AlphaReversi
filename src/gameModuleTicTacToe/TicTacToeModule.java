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

    public TicTacToeModule(String player) {
        if(!decidePlayer(player)) {
            System.out.println("Wrong Command");
        }
    }

    @Override
    public void run() {
        model = new TicTacToeModel();
        model.setSelfPlays();

        game();


    }

    private void game() {
        model.playMove(player.chooseMove(), 'X');

        waitForMove();
        model.playMove(1, 'O');
    }
    private void waitForMove() {

    }
    public synchronized void receiveMove(int move) {
        opponentMove = move;
    }
    private boolean decidePlayer(String player) {
        if (player.equals("HUMAN")) {
            this.player = new Human();
            return true;
        }
        if (player.equals("AI")) {
            this.player = new AI();
            return true;
        }
        return false;
    }
}
