package alphareversi.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import alphareversi.Logger;

/**
 * Created by timmein on 24/03/16.
 *
 * <p>Dispatcher that handles commands comming from the server.
 */
public class CommandDispatcher {
    private Logger logger = new Logger("Dispatcher");

    public List<CommandListener> commandListeners;

    private final Object lock = new Object();

    public CommandDispatcher() {
        this.commandListeners = new ArrayList<>();
    }

    /**
     * Add a listener to the broadcast list.
     * @param listener to addt o broadcast list
     */
    public synchronized void addListener(CommandListener listener) {
        synchronized (lock) {
            this.logger.log("+ New listener: " + listener.getClass());
            if (!this.commandListeners.contains(listener)) {
                this.commandListeners.add(listener);
            }
        }
    }

    /**
     * Method for removing a listener.
     * @param listener The listener to remove.
     */
    public synchronized void removeListener(CommandListener listener) {
        synchronized (lock) {
            for (Iterator<CommandListener> iter = commandListeners.listIterator(); iter.hasNext(); ) {
                CommandListener listeners = iter.next();
                if (listeners == listener) {
                    this.logger.log("- Remove listener: " + listener.getClass());
                    iter.remove();
                }
            }
        }
    }

    /**
     * Send a command to all subscribed listeners.
     * @param command send to all subscribers
     */
    public synchronized void sendCommand(RecvCommand command) {
        synchronized (lock) {
            for (int i = 0; i < this.commandListeners.size(); i++) {
                this.commandListeners.get(i).commandReceived(command);
            }
        }
    }

}
