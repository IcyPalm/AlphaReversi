package alphareversi.game.reversimodule;

import alphareversi.game.reversimodule.Player;

import java.util.Scanner;

/**
 * Created by daant on 25-Mar-16.
 */
public class Human implements Player {
    private boolean allowedToPlay;
    private int side;

    public void setSide(int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    public int chooseMove() {
        allowedToPlay = true;
        return 0;
    }

    public boolean allowedToPlay() {
        return allowedToPlay;
    }

    public void setAllowedToPlayFalse() {
        allowedToPlay = false;
    }
}
