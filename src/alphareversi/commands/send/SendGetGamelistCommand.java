package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * <p>
 * Lijst opvragen met ondersteunde spellen
 * <p>
 * C: get gamelist
 * S: OK
 */
public class SendGetGamelistCommand extends SendCommand {
    public SendGetGamelistCommand() {
        this.setMethod("get");
        this.setAction("gamelist");
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getAction();
    }
}
