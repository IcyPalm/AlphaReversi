package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Inloggen
 *
 * C: login <speler>
 * S: OK
 */
public class SendLoginCommand extends SendCommand {
    private String Player;

    public SendLoginCommand()
    {
        this.setMethod("login");
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getPlayer();
    }
}
