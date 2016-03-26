package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Resultaat van een match die opgegeven is door een speler, bericht naar beide spelers:
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Player forfeited match"}
 * De match is afgelopen, <speler> heeft de match opgegeven.
 *
 * #####################################################################
 * Resultaat van een match, speler heeft de verbinding verbroken:
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Client disconnected"}
 * De match is afgelopen, <speler> heeft de verbinding verbroken.
 *
 *
 * #####################################################################
 * Resultaat van een match ontvangen, bericht naar beide spelers.
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "<commentaar op resultaat>"}
 * De match is afgelopen, <speler resultaat> kan de waarde 'WIN', 'LOSS' of 'DRAW' bevatten.
 */
public class RecvGameResultCommand  extends RecvCommand {
    public String PlayerResult;

    public String PlayerOneScore;
    public String PlayerTwoScore;
    public String Comment;

    public RecvGameResultCommand(String command)
    {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setPlayerResult(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setPlayerOneScore((String)objects.get("PLAYERONESCORE"));
        this.setPlayerTwoScore((String)objects.get("PLAYERTWOSCORE"));
        this.setComment((String)objects.get("COMMENT"));
    }

    public String getPlayerResult() {
        return PlayerResult;
    }

    public void setPlayerResult(String playerResult) {
        PlayerResult = playerResult;
    }

    public String getPlayerOneScore() {
        return PlayerOneScore;
    }

    public void setPlayerOneScore(String playerOneScore) {
        PlayerOneScore = playerOneScore;
    }

    public String getPlayerTwoScore() {
        return PlayerTwoScore;
    }

    public void setPlayerTwoScore(String playerTwoScore) {
        PlayerTwoScore = playerTwoScore;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
