package alphareversi.game.tictactoemodule;

import java.util.Scanner;

/**
 * Created by daant on 25-Mar-16.
 */
public class Human implements Player {
    private Scanner reader = new Scanner(System.in);

    @Override
    public int chooseMove() {
        return reader.nextInt();
    }
}