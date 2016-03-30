package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.ArrayList;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Lijst met spelers ontvangen.
 *
 * <p>S: SVR PLAYERLIST ["--speler--", ...]
 */
public class RecvPlayerlistCommand extends RecvCommand {
    private ArrayList playerList;

    /**
     * Parse string to create a filled RecvGamelistCommand.
     *
     * @param command string containing server message
     */
    public RecvPlayerlistCommand(String command) {
        String[] parts = command.split(" ", 3);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setPlayerList(CommandParser.parseArraylist(parts[2]));
    }

    public ArrayList getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList playerList) {
        this.playerList = playerList;
    }
}
