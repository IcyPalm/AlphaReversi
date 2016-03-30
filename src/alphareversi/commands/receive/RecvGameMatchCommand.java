package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;


/**
 * Created by Joost van Berkel on 3/26/2016. <p> Match aangeboden krijgen, bericht naar beide
 * spelers. <p> S: SVR GAME MATCH {GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT:
 * "<naam tegenstander>"} Nu bezig met een match, de inschrijving voor een speltype is vervallen.
 */
public class RecvGameMatchCommand extends RecvCommand {
    private String gametype;
    private String playerToMove;
    private String opponent;

    /**
     * Parse string to create a filled RecvGameMatchCommand.
     * @param command string containing server message
     */
    public RecvGameMatchCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setGametype((String) objects.get("GAMTYPE"));
        this.setPlayerToMove((String) objects.get("PLAYERTOMOVE"));
        this.setOpponent((String) objects.get("OPPONENT"));
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getPlayerToMove() {
        return playerToMove;
    }

    public void setPlayerToMove(String playerToMove) {
        this.playerToMove = playerToMove;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }
}
