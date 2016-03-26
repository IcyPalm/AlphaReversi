package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Resultaat van een zet ontvangen, bericht naar beide spelers.
 *
 * S: SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
 * Er is een zet gedaan, dit bericht geeft aan wie deze gezet heeft, wat de reactie van het spel erop is
 */
public class RecvGameMoveCommand extends RecvCommand {
    private String Player;
    private String Details;
    private String Move;

    public RecvGameMoveCommand(String command)
    {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setPlayer((String)objects.get("PLAYER"));
        this.setDetails((String)objects.get("DETAILS"));
        this.setMove((String)objects.get("MOVE"));
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getMove() {
        return Move;
    }

    public void setMove(String move) {
        Move = move;
    }
}
