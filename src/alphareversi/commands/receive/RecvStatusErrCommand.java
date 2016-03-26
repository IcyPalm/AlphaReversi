package alphareversi.commands.receive;

import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 3/26/2016.
 *
 * S: ERR <reden>
 */
public class RecvStatusErrCommand extends RecvCommand {
    private String Reason;

    public RecvStatusErrCommand(String command) {
        String[] parts = command.split(" ", 2);
        this.setType(parts[0]); //ERR
        this.setReason(parts[1]);
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
