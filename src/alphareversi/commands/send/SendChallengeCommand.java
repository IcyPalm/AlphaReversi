package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Een speler uitdagen voor een spel.
 *
 * C: challenge "<speler>" "<speltype>"
 * S: OK
 */
public class SendChallengeCommand extends SendCommand {
    private String Player;
    private String GameType;

    public SendChallengeCommand()
    {
        this.setMethod("challenge");
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public String getGameType() {
        return GameType;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }

    @Override
    public String toString() {
        return this.getMethod() + " \"" + this.getPlayer() + "\" \"" + this.getGameType()+"\"";
    }
}
