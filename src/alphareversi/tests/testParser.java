package alphareversi.tests;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * S: SVR GAMELIST ["<speltype>", ...]
 * S: SVR PLAYERLIST ["<speler>", ...]
 * S: SVR GAME MATCH {GAMTYPE: "<speltype>", PLAYERTOMOVE: "<naam speler1>", OPPONENT: "<naam tegenstander>"}
 * S: SVR GAME YOURTURN {TURNMESSAGE: "<bericht voor deze beurt>"}
 * S: SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "<commentaar op resultaat>"}
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Player forfeited match"}
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Client disconnected"}
 * S: SVR GAME CHALLENGE {CHALLENGER: "Sjors", GAMETYPE: "Guess Game", CHALLENGENUMBER: "1"}
 * S: SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: "<uitdaging nummer>"}
 * S: SVR HELP <help informatie>
 */
public class testParser {

    @Test
    public void testSvrGameList()
    {
        RecvCommand RecvGameListCommand = CommandParser.parseString("SVR GAMELIST [\"TICTACTOE\", \"REVERSI\"]");
        assertEquals(RecvGameListCommand.getClass().getSimpleName(), "RecvGamelistCommand");
    }

    @Test
    public void testSvrPlayerList()
    {
        RecvCommand RecvPlayerlistCommand = CommandParser.parseString("SVR PLAYERLIST [\"Speler 1\", \"Speler 2\"]");
        assertEquals(RecvPlayerlistCommand.getClass().getSimpleName(), "RecvPlayerlistCommand");
    }

    @Test
    public void testSvrGameMatch()
    {
        RecvCommand RecvGameMatchCommand = CommandParser.parseString("SVR GAME MATCH {GAMTYPE: \"<speltype>\", PLAYERTOMOVE: \"<naam speler1>\", OPPONENT: \"<naam tegenstander>\"}");
        assertEquals(RecvGameMatchCommand.getClass().getSimpleName(), "RecvGameMatchCommand");
    }

    @Test
    public void testSvrGameYourturnCommand()
    {
        RecvCommand RecvGameYourturnCommand = CommandParser.parseString("SVR GAME YOURTURN {TURNMESSAGE: \"<bericht voor deze beurt>\"}");
        assertEquals(RecvGameYourturnCommand.getClass().getSimpleName(), "RecvGameYourturnCommand");
    }

    @Test
    public void testSvrGameMoveCommand()
    {
        RecvCommand RecvGameMoveCommand = CommandParser.parseString("SVR GAME MOVE {PLAYER: \"<speler>\", DETAILS: \"<reactie spel op zet>\", MOVE: \"<zet>\"}");
        assertEquals(RecvGameMoveCommand.getClass().getSimpleName(), "RecvGameMoveCommand");
    }

    @Test
    public void testSvrGameResultCommand()
    {
        RecvCommand RecvGameResultCommand = CommandParser.parseString("SVR GAME WIN {PLAYERONESCORE: \"<score speler1>\", PLAYERTWOSCORE: \"<score speler2>\", COMMENT: \"<commentaar op resultaat>\"}");
        assertEquals(RecvGameResultCommand.getClass().getSimpleName(), "RecvGameResultCommand");
    }

    @Test
    public void testSvrGameChallengeCommand()
    {
        RecvCommand RecvGameChallengeCommand = CommandParser.parseString("SVR GAME CHALLENGE {CHALLENGER: \"Sjors\", GAMETYPE: \"Guess Game\", CHALLENGENUMBER: \"1\"}");
        assertEquals(RecvGameChallengeCommand.getClass().getSimpleName(), "RecvGameChallengeCommand");
    }

    @Test
    public void testSvrChallengeCanceledCommand()
    {
        RecvCommand RecvGameChallengeCanceledCommand = CommandParser.parseString("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: \"1\"}");
        assertEquals(RecvGameChallengeCanceledCommand.getClass().getSimpleName(), "RecvGameChallengeCanceledCommand");
    }

    @Test
    public void testSvrHelpCommand()
    {
        RecvCommand RecvHelpCommand = CommandParser.parseString("SVR HELP <help informatie>");
        assertEquals(RecvHelpCommand.getClass().getSimpleName(), "RecvHelpCommand");
    }

    @Test
    public void testSvrOkCommand()
    {
        RecvCommand RecvStatusOkCommand = CommandParser.parseString("OK");
        assertEquals(RecvStatusOkCommand.getClass().getSimpleName(), "RecvStatusOkCommand");
    }

    @Test
    public void testSvrErrCommand()
    {
        RecvCommand RecvStatusErrCommand = CommandParser.parseString("ERR test bericht");
        assertEquals(RecvStatusErrCommand.getClass().getSimpleName(), "RecvStatusErrCommand");
    }

    @Test
    public void testWrongCommand()
    {
        RecvCommand WrongCommand = CommandParser.parseString("Bla bla bla");
        assertEquals(WrongCommand, null);
    }
}
