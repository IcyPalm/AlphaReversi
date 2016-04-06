package alphareversi.game;

/**
 * Created by daant on 05-Apr-16.
 */
public abstract class AbstractGameModule {
    protected volatile boolean opponentPlayed = false;
    protected int opponentMove;

    public AbstractGameModule() {
    }

    /**
     * Abstract method for starting the module. All child classes must have this method
     */
    public abstract void start();

    /**
     * Abstract method for starting the module. All child classes must have this method
     */
    public abstract boolean gameOver();

    /**
     * Receives a move and set opponentPlay to true.
     * It is not checked if the move is valid.
     * @param move The move that the opponent wants to play.
     */
    public synchronized void receiveMove(int move) {
        opponentMove = move;
        opponentPlayed = true;
    }


}
