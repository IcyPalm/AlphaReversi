package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.ArrayList;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * S: SVR GAMELIST ["--speltype--", ...]
 * Lijst met spellen ontvangen.
 */
public class RecvGamelistCommand extends RecvCommand {
    private ArrayList gameList;

    /**
     * Parse string to create a filled RecvGamelistCommand.
     * @param command string containing server message
     */
    public RecvGamelistCommand(String command) {
        String[] parts = command.split(" ", 3);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setGameList(CommandParser.parseArraylist(parts[2]));
    }

    public ArrayList getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList gameList) {
        this.gameList = gameList;
    }
}
