package alphareversi.game.tictactoemodule;

import static junit.framework.TestCase.assertEquals;

import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.game.InterfaceGameModule;
import alphareversi.game.tictactoemodule.TicTacToeModule;

import org.junit.Test;

public class TestModule {

    @Test
    public void testAi() {
        InterfaceGameModule ticTacToe = new TicTacToeModule("AI", true);

        ticTacToe.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        ticTacToe.receive(new RecvGameMoveCommand("S: SVR GAME MOVE "
                + "{PLAYER: \"--OPPONENT--\", DETAILS: \"--reactie spel op zet--\", MOVE:\"4\"}"));
        ticTacToe.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        ticTacToe.receive(new RecvGameMoveCommand("S: SVR GAME MOVE "
                + "{PLAYER: \"--OPPONENT--\", DETAILS: \"--reactie spel op zet--\", MOVE:\"3\"}"));
        ticTacToe.receive(new RecvGameYourturnCommand("<p>S: SVR GAME YOURTURN "
                + "{TURNMESSAGE: \"--bericht voor deze beurt--\""));
        assertEquals(true, ticTacToe.gameOver());
    }
}