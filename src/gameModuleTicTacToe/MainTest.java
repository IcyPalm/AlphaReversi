package gameModuleTicTacToe;

/**
 * Created by daant on 25-Mar-16.
 */
public class MainTest {
    public static void main(String [] args) {
        TicTacToeModule module = new TicTacToeModule("AI");
        Thread moduleThread = new Thread(module);
        moduleThread.start();
        //module.receiveMove(1);
    }
}
