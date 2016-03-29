package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016. <p> Resultaat van een zet ontvangen, bericht naar beide
 * spelers. <p> S: SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE:
 * "<zet>"} Er is een zet gedaan, dit bericht geeft aan wie deze gezet heeft, wat de reactie van het
 * spel erop is
 */
public class RecvGameMoveCommand extends RecvCommand {
    private String player;
    private String details;
    private String move;

    public RecvGameMoveCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setPlayer((String) objects.get("PLAYER"));
        this.setDetails((String) objects.get("DETAILS"));
        this.setMove((String) objects.get("MOVE"));
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
