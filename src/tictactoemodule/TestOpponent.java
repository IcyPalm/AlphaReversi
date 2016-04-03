package tictactoemodule;

import java.util.Scanner;

 /**
 * Created by daant on 25-Mar-16.
 * This class Simulates an opponent.
 */
public class TestOpponent {
    private boolean running = true;
    private TicTacToeModule module;
    private Scanner reader = new Scanner(System.in);

    /**
     * Simulates opponent
     */
    public TestOpponent(TicTacToeModule module) {
        this.module = module;
        game();
    }

    /**
     * Plays a game
     */
    public void game() {
        // code for playing against AI
        while (running) {
            int humanMove = reader.nextInt();
            module.receiveMove(humanMove);
        }
        // code for playing against human player (Easier than fixing a certain bug)
        /*
        module.receiveMove(2);
        waiting();
        module.receiveMove(4);
        waiting();
        module.receiveMove(6);
        */
    }

    /**
     * method used by the code against the human player
     */
    private synchronized void waiting() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException error) {
            System.err.println(error.getMessage());
        }
    }
}
