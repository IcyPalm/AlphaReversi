package alphareversi.game.reversimodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daant on 25-Mar-16.
 */
public class Human implements Player {
    private boolean allowedToPlay;
    private int side;
    private ReversiModel model;

    private List<ActionListener> actionListeners = new LinkedList<>();

    public Human(ReversiModel model) {
        this.model = model;
        this.side = model.getMySide();
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

    public void playMove(int move) {
        this.notifyActionListeners(move);
        this.setAllowedToPlayFalse();
    }

    public void startTurn() {
        allowedToPlay = true;
    }

    public boolean allowedToPlay() {
        return allowedToPlay;
    }

    public void setAllowedToPlayFalse() {
        allowedToPlay = false;
    }
}
