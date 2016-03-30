package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Resultaat van een match die opgegeven is door een speler, bericht naar beide spelers:
 *
 * <p>S: SVR GAME --speler resultaat-- {PLAYERONESCORE: "--score speler1--",
 * PLAYERTWOSCORE: "--score speler2--", COMMENT: "Player forfeited match"}
 * De match is afgelopen, --speler-- heeft de match opgegeven.
 *
 * <p>#####################################################################
 * Resultaat van een match, speler heeft de verbinding verbroken:
 *
 * <p>S: SVR GAME --speler resultaat-- {PLAYERONESCORE: "--score speler1--",
 * PLAYERTWOSCORE: "--score speler2--", COMMENT: "Client disconnected"}
 * De match is afgelopen, --speler-- heeft de verbinding verbroken.
 *
 * <p>#####################################################################
 * Resultaat van een match ontvangen, bericht naar beide spelers.
 *
 * <p>S: SVR GAME --speler resultaat-- {PLAYERONESCORE: "--score speler1--",
 * PLAYERTWOSCORE: "--score speler2--", COMMENT: "--commentaar op resultaat--"}
 * De match is afgelopen, --speler resultaat-- kan de waarde 'WIN', 'LOSS' of 'DRAW' bevatten.
 */
public class RecvGameResultCommand extends RecvCommand {
    public String playerResult;

    public String playerOneScore;
    public String playerTwoScore;
    public String comment;

    /**
     * Parse string to create a filled RecvGamelistCommand.
     * @param command string containing server message
     */
    public RecvGameResultCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setPlayerResult(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setPlayerOneScore((String) objects.get("PLAYERONESCORE"));
        this.setPlayerTwoScore((String) objects.get("PLAYERTWOSCORE"));
        this.setComment((String) objects.get("COMMENT"));
    }

    public String getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(String playerResult) {
        this.playerResult = playerResult;
    }

    public String getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(String playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public String getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(String playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
