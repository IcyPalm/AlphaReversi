package alphareversi.commands;

import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

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

    public void removeListner(CommandListener listener) {

        for (Iterator<CommandListener> iter = commandListeners.listIterator(); iter.hasNext(); ) {
            CommandListener a = iter.next();
            if (a == listener) {
                iter.remove();
            }
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
