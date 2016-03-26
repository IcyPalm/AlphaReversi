package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Help opvragen van een commando.
 *
 * C: help
 * S: OK
 */
public class SendHelpCommand extends SendCommand {
    public SendHelpCommand()
    {
        this.setMethod("help");
    }
    @Override
    public String toString() {
        return this.getMethod();
    }
}
