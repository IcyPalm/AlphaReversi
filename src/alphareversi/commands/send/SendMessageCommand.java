package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * Help opvragen van een commando.
 *
 * <p>C: msg "playername" message
 * S: OK
 */
public class SendMessageCommand extends SendCommand {
    private String player;
    private String message;

    /**
     * Constructor SendMessageCommand.
     * @param player set player name
     * @param message set message
     */
    public SendMessageCommand(String player, String message) {
        this.setMethod("MSG");
        this.setPlayer(player);
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return this.getMethod() + " \"" + this.getPlayer() + "\" " +  this.getMessage();
    }
}
