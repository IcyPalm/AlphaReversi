package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Inschrijven voor een speltype.
 *
 * <p>C: subscribe --speltype--
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
