package alphareversi.game.reversimodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by daant on 25-Mar-16.
 */
public class RandomAi implements Player {
    private ReversiModel model;

    private final Integer[] zone1 = {
            0, 7, 56, 63};
    private final Integer[] zone2 = {
            2, 3, 4, 5, 16, 24, 32, 40, 58, 59, 60, 62, 23, 31, 39, 47};
    private final Integer[] zone3 = {
            18, 19, 20, 21, 26, 27, 28, 29, 34, 35, 36, 37, 42, 43, 44, 45};
    private final Integer[] zone4 = {
            10, 11, 12, 13, 17, 25, 33, 41, 50, 51, 52, 53, 22, 30, 38, 46};
    private final Integer[] zone5 = {
            8, 9, 1, 6, 14, 15, 48, 49, 57, 54, 55, 62};

    private List<ActionListener> actionListeners = new LinkedList<>();

    public RandomAi(ReversiModel model) {
        this.model = model;
    }

    /**
     * Add an action listener.
     */
    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }

    /**
     * Remove an action listener.
     */
    public void removeActionListener(ActionListener listener) {
        this.actionListeners.remove(listener);
    }

    private void notifyActionListeners(int move) {
        for (ActionListener listener : this.actionListeners) {
            listener.actionPerformed(
                    new ActionEvent(this, move, "")
            );
        }
    }

    /**
     * start turn.
     * /TODO
     */
    public void startTurn() {
        Collection<Integer> movesSet = model.getValidMoves(model.getMySide(), model.getBoard());
        Integer[] moves = movesSet.toArray(new Integer[movesSet.size()]);
        int random = randInt(0, movesSet.size() - 1);
        notifyActionListeners(moves[random]);
    }

    private int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
