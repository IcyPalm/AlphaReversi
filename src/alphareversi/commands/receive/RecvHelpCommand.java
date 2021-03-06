package alphareversi.commands.receive;

import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Help informatie is ontvangen, kan meerdere achtereenvolgende responses bevatten.
 *
 * <p>S: SVR HELP --help informatie--
 */
public class RecvHelpCommand extends RecvCommand {
    private String information;

    /**
     * Parse string to create a filled RecvGamelistCommand.
     * @param command string containing server message
     */
    public RecvHelpCommand(String command) {
        String[] parts = command.split(" ", 3);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setInformation(parts[2]);
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
