package tictactoemodule;

/**
 * Created by daant on 25-Mar-16.
 */
public class MainTest {
   /*
    * This is a test main method. Only made to develop.
    */
    public static void main(String [] args) {
        TicTacToeModule module = new TicTacToeModule("AI", false);
        Thread moduleThread = new Thread(module);
        moduleThread.start();
        // creating a test opponent
        new TestOpponent(module);
    }
}
