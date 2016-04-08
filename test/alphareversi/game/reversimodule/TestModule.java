package alphareversi.game.reversimodule;

import org.junit.Test;

import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.game.InterfaceGameModule;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Robert on 8-4-2016.
 */
public class TestModule {
    @Test
    public void testAi() {
        ReversiModel reversiModel = new ReversiModel(1);
        InterfaceGameModule reversiModule = new ReversiModule(reversiModel, "OPPONENT");

        RecvGameMoveCommand command1 = new RecvGameMoveCommand("S: SVR GAME MOVE "
                + "{PLAYER:\"empty\", DETAILS:\"reactie spel op zet\", MOVE:\"4\"}");
        command1.setPlayer("OPPONENT");
        RecvGameMoveCommand command2 = new RecvGameMoveCommand("S: SVR GAME MOVE "
                + "{PLAYER:\"empty\", DETAILS:\"reactie spel op zet\", MOVE:\"3\"}");
        command2.setPlayer("OPPONENT");

        reversiModule.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        reversiModule.receive(command1);
        reversiModule.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        reversiModule.receive(command2);
        reversiModule.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        assertEquals(true, reversiModule.gameOver());
    }
}
