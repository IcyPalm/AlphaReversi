package alphareversi.commands.receive;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

import java.util.HashMap;

/**
 * Created by Joost van Berkel on 3/24/2016.
 * <p>
 * Een uitdaging ontvangen.
 * <p>
 * S: SVR GAME CHALLENGE {CHALLENGER: "Sjors", GAMETYPE: "Guess Game", CHALLENGENUMBER: "1"}
 * Nu mogelijkheid de uitdaging te accepteren.
 */
public class RecvGameChallengeCommand extends RecvCommand {
    private String Challenger;
    private String GameType;
    private int ChallengeNumber;


    public RecvGameChallengeCommand(String command) {
        String[] parts = command.split(" ", 4);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);

        HashMap objects = CommandParser.parseObjectMap(parts[3]);
        this.setChallenger((String) objects.get("CHALLENGER"));
        this.setGameType((String) objects.get("GAMETYPE"));
        this.setChallengeNumber(Integer.parseInt((String) objects.get("CHALLENGENUMBER")));
    }


    public String getChallenger() {
        return Challenger;
    }

    public void setChallenger(String challenger) {
        Challenger = challenger;
    }

    public String getGameType() {
        return GameType;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }

    public int getChallengeNumber() {
        return ChallengeNumber;
    }

    public void setChallengeNumber(int challengeNumber) {
        ChallengeNumber = challengeNumber;
    }
}
