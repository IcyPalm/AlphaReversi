package alphareversi.commands.receive;

import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 3/26/2016.
 * <p>
 * S: OK
 */
public class RecvStatusOkCommand extends RecvCommand {
    public RecvStatusOkCommand(String command) {
        this.setType(command);
    }
}
