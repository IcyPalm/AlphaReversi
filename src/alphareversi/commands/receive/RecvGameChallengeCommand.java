package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * Een uitdaging ontvangen.
 * S: SVR GAME CHALLENGE {CHALLENGER: "Henk", GAMETYPE: "TicTacToe",
 * CHALLENGENUMBER: "Challengenummer", TURNTIME: "2"}
 * Nu mogelijkheid de uitdaging te accepteren.
 */
public class RecvGameChallengeCommand extends RecvCommand {
    private String challenger;
    private String gameType;
    private int challengeNumber;
    private int turntime;

    /**
     * Parse string to create a filled RecvGameChallengeCommand.
     * @param command string containing server message
     */
    public RecvGameChallengeCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setChallenger((String) objects.get("CHALLENGER"));
        this.setGameType((String) objects.get("GAMETYPE"));
        this.setChallengeNumber(Integer.parseInt((String) objects.get("CHALLENGENUMBER")));
        this.setTurntime(Integer.parseInt((String) objects.get("TURNTIME")));
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public void setChallengeNumber(int challengeNumber) {
        this.challengeNumber = challengeNumber;
    }

    public int getTurntime() {
        return turntime;
    }

    public void setTurntime(int turntime) {
        this.turntime = turntime;
    }
}
