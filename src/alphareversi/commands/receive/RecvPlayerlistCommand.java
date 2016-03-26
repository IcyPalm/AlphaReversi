package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.ArrayList;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Lijst met spelers ontvangen.
 *
 * S: SVR PLAYERLIST ["<speler>", ...]
 */
public class RecvPlayerlistCommand extends RecvCommand {
    private ArrayList PlayerList;

    public RecvPlayerlistCommand(String command)
    {
        String[] parts = command.split(" ", 3);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setPlayerList(CommandParser.parseArraylist(parts[2]));
    }

    public ArrayList getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(ArrayList playerList) {
        PlayerList = playerList;
    }
}
