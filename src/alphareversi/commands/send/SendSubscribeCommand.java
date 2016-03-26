package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Inschrijven voor een speltype.
 *
 * C: subscribe <speltype>
 * S: OK
 */
public class SendSubscribeCommand extends SendCommand {
    @Override
    public String makeString() {
        return null;
    }
}
