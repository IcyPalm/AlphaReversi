package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * <p>
 * De beurt toegewezen krijgen tijdens match.
 * <p>
 * S: SVR GAME YOURTURN {TURNMESSAGE: "<bericht voor deze beurt>"}
 */
public class RecvGameYourturnCommand extends RecvCommand {
    private String TurnMessage;

    public RecvGameYourturnCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setTurnMessage((String) objects.get("TURNMESSAGE"));
    }

    public String getTurnMessage() {
        return TurnMessage;
    }

    public void setTurnMessage(String turnMessage) {
        TurnMessage = turnMessage;
    }
}
