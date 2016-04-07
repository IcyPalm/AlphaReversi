package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Lijst opvragen met verbonden spelers.
 *
 * <p>C: get playerlist
 * S: OK
 */
public class SendGetPlayerlistCommand extends SendCommand {

    /**
     * Constructor SendGetPlayerlistCommand.
     */
    public SendGetPlayerlistCommand() {
        this.setMethod("get");
        this.setAction("playerlist");
    }

    @Override
    public final String toString() {
        return this.getMethod() + " " + this.getAction();
    }
}
