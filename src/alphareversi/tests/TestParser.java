package alphareversi.tests;

import static junit.framework.TestCase.assertEquals;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import org.junit.Test;



/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>S: SVR GAMELIST ["--speltype--", ...] S: SVR PLAYERLIST ["--speler--", ...] S: SVR GAME MATCH
 * {GAMTYPE: "--speltype--", PLAYERTOMOVE: "--naam speler1--", OPPONENT: "--naam tegenstander--"} S:
 * SVR GAME YOURTURN {TURNMESSAGE: "--bericht voor deze beurt--"} S: SVR GAME MOVE {PLAYER:
 * "--speler--", DETAILS: "--reactie spel op zet--", MOVE: "--zet--"} S: SVR GAME --speler
 * resultaat-- {PLAYERONESCORE: "--score speler1--", PLAYERTWOSCORE: "--score speler2--", COMMENT:
 * "--commentaar op resultaat--"} S: SVR GAME --speler resultaat-- {PLAYERONESCORE: "--score
 * speler1--", PLAYERTWOSCORE: "--score speler2--", COMMENT: "Player forfeited match"} S: SVR GAME
 * --speler resultaat-- {PLAYERONESCORE: "--score speler1--", PLAYERTWOSCORE: "--score speler2--",
 * COMMENT: "Client disconnected"} S: SVR GAME CHALLENGE {CHALLENGER: "Sjors", GAMETYPE: "Guess
 * Game", CHALLENGENUMBER: "1"} S: SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: "--uitdaging
 * nummer--"} S: SVR HELP --help informatie--
 */
public class TestParser {

    @Test
    public void testSvrGameList() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAMELIST [\"TICTACTOE\", \"REVERSI\"]");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGamelistCommand");
    }

    @Test
    public void testSvrPlayerList() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR PLAYERLIST [\"Speler 1\", \"Speler 2\"]");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvPlayerlistCommand");
    }

    @Test
    public void testSvrGameMatch() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME MATCH {GAMTYPE: \"<speltype>\", PLAYERTOMOVE: \"<naam speler1>\", "
                        + "OPPONENT: \"<naam tegenstander>\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameMatchCommand");
    }

    @Test
    public void testSvrGameYourturnCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME YOURTURN {TURNMESSAGE: \"<bericht voor deze beurt>\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameYourturnCommand");
    }

    @Test
    public void testSvrGameMoveCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME MOVE {PLAYER: \"<speler>\", DETAILS: \"<reactie spel op zet>\", "
                        + "MOVE: \"<zet>\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameMoveCommand");
    }

    @Test
    public void testSvrGameResultCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME WIN {PLAYERONESCORE: \"<score speler1>\", PLAYERTWOSCORE: "
                        + "\"<score speler2>\", COMMENT: \"<commentaar op resultaat>\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameResultCommand");
    }

    @Test
    public void testSvrGameChallengeCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME CHALLENGE {CHALLENGER: \"Sjors\", GAMETYPE: \"Guess Game\", "
                        + "CHALLENGENUMBER: \"1\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameChallengeCommand");
    }

    @Test
    public void testSvrChallengeCanceledCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: \"1\"}");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvGameChallengeCanceledCommand");
    }

    @Test
    public void testSvrHelpCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "SVR HELP <help informatie>");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvHelpCommand");
    }

    @Test
    public void testSvrOkCommand() {
        RecvCommand recvCommand = CommandParser.parseString("OK");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvStatusOkCommand");
    }

    @Test
    public void testSvrErrCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "ERR test bericht");
        assertEquals(recvCommand.getClass().getSimpleName(), "RecvStatusErrCommand");
    }

    @Test
    public void testWrongCommand() {
        RecvCommand recvCommand = CommandParser.parseString(
                "Bla bla bla");
        assertEquals(recvCommand, null);
    }
}
