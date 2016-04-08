package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 4/7/2016.
 *
 * <p>Een bericht van een speler is ontvangen
 *
 * <p>S: SVR MESSAGE {PLAYERNAME: "", MESSAGE: ""}
 */
public class RecvMessageCommand extends RecvCommand {
    private String player;
    private String message;

    /**
     * Parse string to create a filled RecvMessageCommand.
     * @param command string containing server message
     */
    public RecvMessageCommand(String command) {
        String[] parts = command.split(" ", 3);

        this.setType(parts[0]);
        this.setMethod(parts[1]);

        HashMap objects = CommandParser.parseObjectMap(parts[2]);
        this.setPlayer((String) objects.get("PLAYERNAME"));
        this.setMessage((String) objects.get("MESSAGE"));
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
