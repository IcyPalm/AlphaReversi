package alphareversi.commands.receive;

import java.util.HashMap;

import alphareversi.commands.CommandParser;
import alphareversi.commands.RecvCommand;

/**
 * Created by Joost van Berkel on 3/24/2016. <p> Resultaat van een uitdaging die is komen te
 * vervallen. <p> S: SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: "<uitdaging nummer>"} De
 * uitdaging is vervallen. Mogelijke oorzaken: speler heeft een andere uitdaging gestart, speler is
 * een match begonnen, speler heeft de verbinding verbroken.
 */
public class RecvGameChallengeCanceledCommand extends RecvCommand {
    private String challengeResult;
    private int challengeNumber;

    public RecvGameChallengeCanceledCommand(String command) {
        String[] parts = command.split(" ", 5);
        this.setType(parts[0]);
        this.setMethod(parts[1]);
        this.setAction(parts[2]);
        this.setChallengeResult(parts[3]);

        HashMap objects = CommandParser.parseObjectMap(parts[4]);
        this.setChallengeNumber(Integer.parseInt((String) objects.get("CHALLENGENUMBER")));
    }


    public String getChallengeResult() {
        return challengeResult;
    }

    public void setChallengeResult(String challengeResult) {
        this.challengeResult = challengeResult;
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public void setChallengeNumber(int challengeNumber) {
        this.challengeNumber = challengeNumber;
    }
}
