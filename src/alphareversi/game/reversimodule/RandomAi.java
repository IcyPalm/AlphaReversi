package alphareversi.game.reversimodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by daant on 25-Mar-16.
 */
public class RandomAi implements Player {
    private ReversiModel model;

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

    public void startTurn() {
        HashSet<Integer> movesSet = model.getValidMoves(model.getMySide(),model.getBoard());
        Integer[] moves = movesSet.toArray(new Integer[movesSet.size()]);
        int random = randInt(0,movesSet.size());
        notifyActionListeners(moves[random]);
    }

    private int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
