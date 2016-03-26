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
    private String GameType;

    public SendSubscribeCommand() {
        this.setMethod("subscribe");
    }

    public String getGameType() {
        return GameType;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getGameType();
    }
}
