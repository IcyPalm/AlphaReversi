package gameModuleTicTacToe;

import java.util.Scanner;

/**
 * Created by daant on 25-Mar-16.
 */
public class testOpponent {
    private boolean running = true;
    private TicTacToeModule module;
    private Scanner reader = new Scanner(System.in);
    public testOpponent(TicTacToeModule module) {
        this.module = module;
        game();
    }
    public void game() {
        while(running) {
            int humanMove = reader.nextInt();
            module.receiveMove(humanMove);
        }
    }
}
