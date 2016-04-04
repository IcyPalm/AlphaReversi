package alphareversi.commands.receive;

import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 3/26/2016.
 *
 * <p>S: ERR --reden--
 */
public class RecvStatusErrCommand extends RecvCommand {
    private String reason;

    /**
     * Parse string to create a filled RecvGamelistCommand.
     *
     * @param command string containing server message
     */
    public RecvStatusErrCommand(String command) {
        String[] parts = command.split(" ", 2);
        this.setType(parts[0]); //ERR
        this.setReason(parts[1]);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
