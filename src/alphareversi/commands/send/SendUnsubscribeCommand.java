package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Wouter Bijen on 04-04-2015.
 *
 * <p>uitschrijven voor een speltype.
 *
 * <p>C: unsubscribe --speltype--
 * S: OK
 */
public class SendUnsubscribeCommand extends SendCommand {

    /**
     * Constructor SendSubscribeCommand.
     */
    public SendUnsubscribeCommand() {
        this.setMethod("unsubscribe");
    }

    @Override
    public String toString() {
        return this.getMethod();
    }
}
