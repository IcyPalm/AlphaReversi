package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Een speler uitdagen voor een spel.
 *
 * <p>C: challenge "--speler--" "--speltype--"
 * S: OK
 */
public class SendChallengeCommand extends SendCommand {
    private String player;
    private String gameType;

    /**
     * Constructor SendChallengeCommand.
     * @param player set player name
     * @param gameType set game type
     */
    public SendChallengeCommand(String player, String gameType) {
        this.setMethod("challenge");
        this.setPlayer(player);
        this.setGameType(gameType);
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return this.getMethod() + " \"" + this.getPlayer() + "\" \"" + this.getGameType() + "\"";
    }
}
