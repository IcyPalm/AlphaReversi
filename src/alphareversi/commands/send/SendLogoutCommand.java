package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Uitloggen/Verbinding verbreken.
 *
 * <p>C: logout | exit | quit | disconnect | bye
 * S: -
 */
public class SendLogoutCommand extends SendCommand {

    /**
     * Constructor SendLogoutCommand.
     */
    public SendLogoutCommand() {
        this.setMethod("logout");
    }

    @Override
    public String toString() {
        return this.getMethod();
    }
}
