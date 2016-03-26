package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Lijst opvragen met verbonden spelers.
 *
 * C: get playerlist
 * S: OK
 */
public class SendGetPlayerlistCommand extends SendCommand {
    public SendGetPlayerlistCommand()
    {
        this.setMethod("get");
        this.setAction("playerlist");
    }
    @Override
    public String toString() {
        return this.getMethod() + " " + this.getAction();
    }
}
