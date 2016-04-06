import alphareversi.game.AbstractGameModule;
import alphareversi.game.tictactoemodule.TicTacToeModule;

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestModule {

    @Test
    public void testAI() {
        AbstractGameModule ticTacToe = new TicTacToeModule("AI", true);
        Thread t = new Thread() {
            public void run() {
                ticTacToe.start();
            }
        };
        t.start();
        waiting();
        ticTacToe.receiveMove(4);
        waiting();
        ticTacToe.receiveMove(3);
        waiting();
        assertEquals(true, ticTacToe.gameOver());
    }

    synchronized private void waiting() {
        try {
            wait(500);
        } catch (InterruptedException error) {
            System.err.println(error.getMessage());
        }
    }
}