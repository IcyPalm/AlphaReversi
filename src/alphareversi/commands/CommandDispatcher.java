package alphareversi.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmein on 24/03/16.
 */
public class CommandDispatcher {

    private List<CommandListener> commandListeners;

    public CommandDispatcher() {
        this.commandListeners = new ArrayList<>();
    }

    public void addListener(CommandListener listener) {

        if (!this.commandListeners.contains(listener)) {
            this.commandListeners.add(listener);
        }

    }

    public void sendCommand(RecvCommand command) {

        for (CommandListener listener : this.commandListeners) {
            listener.commandReceived(command);
        }

    }

}
