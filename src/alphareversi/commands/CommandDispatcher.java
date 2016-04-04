package alphareversi.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmein on 24/03/16.
 *
 * <p>Dispatcher that handles commands comming from the server.
 */
public class CommandDispatcher {

    private List<CommandListener> commandListeners;

    public CommandDispatcher() {
        this.commandListeners = new ArrayList<>();
    }

    /**
     * Add a listener to the broadcast list.
     * @param listener to addt o broadcast list
     */
    public void addListener(CommandListener listener) {

        if (!this.commandListeners.contains(listener)) {
            this.commandListeners.add(listener);
        }

    }

    /**
     * Send a command to all subscribed listeners.
     * @param command send to all subscribers
     */
    public void sendCommand(RecvCommand command) {

        for (CommandListener listener : this.commandListeners) {
            listener.commandReceived(command);
        }

    }

}
