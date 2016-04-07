package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Help opvragen van een commando.
 *
 * <p>C: help
 * S: OK
 */
public class SendMessageCommand extends SendCommand {
    /**
     * Constructor SendHelpCommand.
     */
    public SendMessageCommand() {
        this.setMethod("help");
    }

    @Override
    public String toString() {
        return this.getMethod();
    }
}
