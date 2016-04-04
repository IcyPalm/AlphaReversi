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
public class SendSubscribeCommand extends SendCommand {
    private String gameType;

    public SendSubscribeCommand() {
        this.setMethod("subscribe");
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getGameType();
    }
}
