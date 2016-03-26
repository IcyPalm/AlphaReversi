package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/26/2016.
 *
 * Match aangeboden krijgen, bericht naar beide spelers.
 *
 * S: SVR GAME MATCH {GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT: "<naam tegenstander>"}
 * Nu bezig met een match, de inschrijving voor een speltype is vervallen.
 */
public class RecvGameMatchCommand extends RecvCommand {
    private String Gametype;
    private String PlayerToMove;
    private String Opponent;

    public RecvGameMatchCommand(String command)
    {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setGametype((String)objects.get("GAMTYPE"));
        this.setPlayerToMove((String)objects.get("PLAYERTOMOVE"));
        this.setOpponent((String)objects.get("OPPONENT"));
    }

    public String getGametype() {
        return Gametype;
    }

    public void setGametype(String gametype) {
        Gametype = gametype;
    }

    public String getPlayerToMove() {
        return PlayerToMove;
    }

    public void setPlayerToMove(String playerToMove) {
        PlayerToMove = playerToMove;
    }

    public String getOpponent() {
        return Opponent;
    }

    public void setOpponent(String opponent) {
        Opponent = opponent;
    }
}
