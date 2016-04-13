package alphareversi.game;

import alphareversi.commands.CommandListener;
import alphareversi.commands.SendCommand;
import javafx.scene.layout.BorderPane;

/**
 * Created by timmein on 08/04/16.
 */
public abstract class GameModule implements CommandListener {
    public abstract boolean gameOver();

    public abstract BorderPane getView();
}
