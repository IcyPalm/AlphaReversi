package alphareversi.game.tictactoemodule;

import static junit.framework.TestCase.assertEquals;

import alphareversi.game.AbstractGameModule;
import alphareversi.game.tictactoemodule.TicTacToeModule;

import org.junit.Test;

public class TestModule {

    @Test
    public void testAi() {
        AbstractGameModule ticTacToe = new TicTacToeModule("AI", true);
        Thread thread = new Thread() {
            public void run() {
                ticTacToe.start();
            }
        };
        thread.start();
        waiting();
        ticTacToe.receiveMove(4);
        waiting();
        ticTacToe.receiveMove(3);
        waiting();
        assertEquals(true, ticTacToe.gameOver());
    }

    private synchronized void waiting() {
        try {
            wait(1000);
        } catch (InterruptedException error) {
            System.err.println(error.getMessage());
        }
    }
}