package gameModuleTicTacToe;

import java.util.Random;

/**
 * Created by daant on 25-Mar-16.
 */
public class MainTest {
    /*
    This is a test main method. Only made to develop.
     */
    public static void main(String [] args) {
        TicTacToeModule module = new TicTacToeModule("AI", true);
        Thread moduleThread = new Thread(module);
        moduleThread.start();
        new testOpponent(module);
    }
}
