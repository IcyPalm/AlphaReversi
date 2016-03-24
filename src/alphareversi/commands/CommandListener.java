package alphareversi.commands;

import alphareversi.commands.Command;

/**
 * Created by timmein on 24/03/16.
 */
public interface CommandListener {
    void commandReceived(Command command);
}


