package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Inloggen
 *
 * <p>C: login --speler--
 * S: OK
 */
public class SendLoginCommand extends SendCommand {
    private String player;

    public SendLoginCommand() {
        this.setMethod("login");
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getPlayer();
    }
}
