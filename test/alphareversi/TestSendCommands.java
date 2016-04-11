package alphareversi;

import static junit.framework.TestCase.assertEquals;

import alphareversi.commands.send.SendChallengeAcceptCommand;
import alphareversi.commands.send.SendChallengeCommand;
import alphareversi.commands.send.SendForfeitCommand;
import alphareversi.commands.send.SendGetGamelistCommand;
import alphareversi.commands.send.SendGetPlayerlistCommand;
import alphareversi.commands.send.SendHelpCommand;
import alphareversi.commands.send.SendLoginCommand;
import alphareversi.commands.send.SendLogoutCommand;
import alphareversi.commands.send.SendMessageCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.commands.send.SendSubscribeCommand;
import alphareversi.commands.send.SendUnsubscribeCommand;

import org.junit.Test;

/**
 * Created by Joost van Berkel on 3/26/2016.
 * C: login --speler--
 * C: logout
 * C: get gamelist
 * C: get playerlist
 * C: subscribe --speltype--
 * C: unsubscribe
 * C: move --zet--
 * C: forfeit
 * C: challenge "--speler--" "--speltype--" --turntime--
 * C: challenge accept --uitdaging nummer--
 * C: help
 */
public class TestSendCommands {

    @Test
    public void testSendLoginCommand() {
        SendLoginCommand loginCommand = new SendLoginCommand("Zolero");
        assertEquals(true, loginCommand.toString().contains("login Zolero"));
    }

    @Test
    public void testSendLogoutCommand() {
        SendLogoutCommand logoutCommand = new SendLogoutCommand();
        assertEquals(true, logoutCommand.toString().contains("logout"));
    }

    @Test
    public void testSendGetGamelistCommand() {
        SendGetGamelistCommand getGameListCommand = new SendGetGamelistCommand();
        assertEquals(true, getGameListCommand.toString().contains("get gamelist"));
    }

    @Test
    public void testSendGetPlayerlistCommand() {
        SendGetPlayerlistCommand getPlayerlistCommand = new SendGetPlayerlistCommand();

        assertEquals(true, getPlayerlistCommand.toString().contains("get playerlist"));
    }

    @Test
    public void testSendSubscribeCommand() {
        SendSubscribeCommand subscribeCommand = new SendSubscribeCommand("TicTacToe");
        assertEquals(true, subscribeCommand.toString().contains("subscribe TicTacToe"));
    }

    @Test
    public void testSendUnsubscribeCommand() {
        SendUnsubscribeCommand unsubscribeCommand = new SendUnsubscribeCommand();
        assertEquals(true, unsubscribeCommand.toString().contains("subscribe"));
    }

    @Test
    public void testSendMoveCommand() {
        SendMoveCommand moveCommand = new SendMoveCommand(5);
        assertEquals(true, moveCommand.toString().contains("move 5"));
    }

    @Test
    public void testSendForfeitCommand() {
        SendForfeitCommand forfeitCommand = new SendForfeitCommand();

        assertEquals(true, forfeitCommand.toString().contains("forfeit"));
    }

    @Test
    public void testSendMessageCommand() {
        SendMessageCommand messageCommand = new SendMessageCommand("Zolero", "rip");
        assertEquals(true, messageCommand.toString().contains("msg \"Zolero\" rip"));
    }

    @Test
    public void testSendChallengeCommand() {
        SendChallengeCommand challengeCommand = new SendChallengeCommand("Zolero", "Reversi", 2);
        assertEquals(true, challengeCommand.toString().contains(
                "challenge \"Zolero\" \"Reversi\" 2"));
    }

    @Test
    public void testSendChallengeAcceptCommand() {
        SendChallengeAcceptCommand challengeAcceptCommand = new SendChallengeAcceptCommand(1);
        assertEquals(true, challengeAcceptCommand.toString().contains("challenge accept 1"));
    }

    @Test
    public void testSendHelpCommand() {
        SendHelpCommand sendHelpCommand = new SendHelpCommand();
        assertEquals(true, sendHelpCommand.toString().contains("help"));
    }
}
