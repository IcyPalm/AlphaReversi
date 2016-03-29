package alphareversi.tests;

import alphareversi.commands.send.*;

import static junit.framework.TestCase.assertEquals;
import org.junit.Test;

/**
 * Created by Joost van Berkel on 3/26/2016.
 *
 * C: login <speler>
 * C: logout
 * C: get gamelist
 * C: get playerlist
 * C: subscribe <speltype>
 * C: move <zet>
 * C: forfeit
 * C: challenge "<speler>" "<speltype>"
 * C: challenge accept <uitdaging nummer>
 * C: help
 */
public class testSendCommands {

    @Test
    public void testSendLoginCommand()
    {
        SendLoginCommand loginCommand = new SendLoginCommand();
        loginCommand.setPlayer("Zolero");

        assertEquals(true, loginCommand.toString().contains("login Zolero"));
    }

    @Test
    public void testSendLogoutCommand()
    {
        SendLogoutCommand logoutCommand = new SendLogoutCommand();

        assertEquals(true, logoutCommand.toString().contains("logout"));
    }

    @Test
    public void testSendGetGamelistCommand()
    {
        SendGetGamelistCommand getGameListCommand = new SendGetGamelistCommand();

        assertEquals(true, getGameListCommand.toString().contains("get gamelist"));
    }

    @Test
    public void testSendGetPlayerlistCommand()
    {
        SendGetPlayerlistCommand getPlayerlistCommand = new SendGetPlayerlistCommand();

        assertEquals(true, getPlayerlistCommand.toString().contains("get playerlist"));
    }

    @Test
    public void testSendSubscribeCommand()
    {
        SendSubscribeCommand subscribeCommand = new SendSubscribeCommand();
        subscribeCommand.setGameType("TicTacToe");

        assertEquals(true, subscribeCommand.toString().contains("subscribe TicTacToe"));
    }

    @Test
    public void testSendMoveCommand()
    {
        SendMoveCommand moveCommand = new SendMoveCommand();
        moveCommand.setMove(5);

        assertEquals(true, moveCommand.toString().contains("move 5"));
    }

    @Test
    public void testSendForfeitCommand()
    {
        SendForfeitCommand forfeitCommand = new SendForfeitCommand();

        assertEquals(true, forfeitCommand.toString().contains("forfeit"));
    }

    @Test
    public void testSendChallengeCommand()
    {
        SendChallengeCommand challengeCommand = new SendChallengeCommand();
        challengeCommand.setPlayer("Zolero");
        challengeCommand.setGameType("Reversi");

        assertEquals(true, challengeCommand.toString().contains("challenge \"Zolero\" \"Reversi\""));
    }

    @Test
    public void testSendChallengeAcceptCommand()
    {
        SendChallengeAcceptCommand challengeAcceptCommand = new SendChallengeAcceptCommand();
        challengeAcceptCommand.setChallengeNumber(1);

        assertEquals(true, challengeAcceptCommand.toString().contains("challenge accept 1"));
    }


    @Test
    public void testSendHelpCommand()
    {
        SendHelpCommand sendHelpCommand = new SendHelpCommand();

        assertEquals(true, sendHelpCommand.toString().contains("help"));
    }
}
