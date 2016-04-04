package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Een match opgeven.
 *
 * <p>C: forfeit
 * S: OK
 */
public class SendForfeitCommand extends SendCommand {
    public SendForfeitCommand() {
        this.setMethod("forfeit");
    }

    @Override
    public String toString() {
        return this.getMethod();
    }
}
