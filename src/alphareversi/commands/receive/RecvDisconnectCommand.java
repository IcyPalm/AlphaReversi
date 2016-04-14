package alphareversi.commands.receive;

import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 4/13/2016.
 * S: null
 */
public class RecvDisconnectCommand extends RecvCommand {
    public RecvDisconnectCommand(String command) {
        this.setType(command);
    }
}
