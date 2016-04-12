package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Een speler uitdagen voor een spel.
 *
 * <p>C: challenge "--speler--" "--speltype--" --turntime--
 * S: OK
 */
public class SendChallengeCommand extends SendCommand {
    private String player;
    private String gameType;
    private int turntime = 2000;

    /**
     * Constructor SendChallengeCommand.
     * @param player set player name
     * @param gameType set game type
     * @param turntime set the turntime
     */
    public SendChallengeCommand(String player, String gameType, int turntime) {
        this.setMethod("challenge");
        this.setPlayer(player);
        this.setGameType(gameType);
        this.setTurntime(turntime);
    }

    /**
     * Constructor SendChallengeCommand without turntime.
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

    public int getTurntime() {
        return turntime;
    }

    public void setTurntime(int turntime) {
        this.turntime = turntime;
    }

    @Override
    public String toString() {
        return this.getMethod() + " \"" + this.getPlayer() + "\" \"" + this.getGameType()
                + "\" " + Integer.toString(this.getTurntime());
    }
}
