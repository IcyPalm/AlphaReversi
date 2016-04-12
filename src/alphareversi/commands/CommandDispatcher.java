package alphareversi.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by timmein on 24/03/16.
 *
 * <p>Dispatcher that handles commands comming from the server.
 */
public class CommandDispatcher {

    public List<CommandListener> commandListeners;

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
     * Method for removing a listener.
     * @param listener The listener to remove.
     */
    public void removeListener(CommandListener listener) {

        for (Iterator<CommandListener> iter = commandListeners.listIterator(); iter.hasNext(); ) {
            CommandListener listeners = iter.next();
            if (listeners == listener) {
                iter.remove();
            }
        }

    }

    /**
     * Send a command to all subscribed listeners.
     * @param command send to all subscribers
     */
    public synchronized void sendCommand(RecvCommand command) {

        for (CommandListener listener : this.commandListeners) {
            listener.commandReceived(command);
        }

    }

}
