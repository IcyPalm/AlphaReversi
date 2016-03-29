package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.ArrayList;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * <p>
 * S: SVR GAMELIST ["<speltype>", ...]
 * <p>
 * Lijst met spellen ontvangen.
 */
public class RecvGamelistCommand extends RecvCommand {
    private ArrayList GameList;

    public RecvGamelistCommand(String command) {
        String[] parts = command.split(" ", 3);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setGameList(CommandParser.parseArraylist(parts[2]));
    }

    public ArrayList getGameList() {
        return GameList;
    }

    public void setGameList(ArrayList gameList) {
        GameList = gameList;
    }
}
