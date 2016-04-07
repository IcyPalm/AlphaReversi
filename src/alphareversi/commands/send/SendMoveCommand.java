package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Een zet doen na het toegewezen krijgen van een beurt.
 *
 * <p>C: move --zet--
 * S: OK
 */
public class SendMoveCommand extends SendCommand {
    private int move;

    /**
     * Constructor SendMoveCommand.
     * @param move set move position
     */
    public SendMoveCommand(int move) {
        this.setMethod("move");
        this.setMove(move);
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + Integer.toString(this.getMove());
    }
}
