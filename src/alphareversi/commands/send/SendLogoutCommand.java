package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Uitloggen/Verbinding verbreken.
 *
 * C: logout | exit | quit | disconnect | bye
 * S: -
 */
public class SendLogoutCommand extends SendCommand {
    public SendLogoutCommand()
    {
        this.setMethod("logout");
    }
    @Override
    public String toString() {
        return this.getMethod();
    }
}
