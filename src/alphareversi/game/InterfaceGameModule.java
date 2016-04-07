package alphareversi.game;

import alphareversi.commands.RecvCommand;
import alphareversi.commands.send.SendMoveCommand;

/**
 * Created by daant on 05-Apr-16.
 */
public interface InterfaceGameModule {

    public boolean gameOver();

    public void receive(RecvCommand incoming);

    public SendMoveCommand send(SendMoveCommand command);



}
