package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by timmein on 13/04/16.
 */
public class SendCustomCommand extends SendCommand {
    private String command;

    public SendCustomCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}
