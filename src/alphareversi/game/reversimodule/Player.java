package alphareversi.game.reversimodule;

import java.awt.event.ActionListener;

/**
 * Created by daant on 25-Mar-16.
 */
public interface Player {
    /**
     * Notify the player that their turn has started.
     */
    public void startTurn();

    /**
     * Add a listener for moves by the player.
     */
    public void addActionListener(ActionListener listener);

    /**
     * Remove a listener for moves.
     */
    public void removeActionListener(ActionListener listener);
}
