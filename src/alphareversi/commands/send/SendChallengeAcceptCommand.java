package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * <p>Een uitdaging accepteren.
 *
 * <p>C: challenge accept --uitdaging nummer--
 * S: OK
 */
public class SendChallengeAcceptCommand extends SendCommand {
    private int challengeNumber;

    public SendChallengeAcceptCommand() {
        this.setMethod("challenge");
        this.setAction("accept");
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public void setChallengeNumber(int challengeNumber) {
        this.challengeNumber = challengeNumber;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getAction() + " " + Integer.toString(challengeNumber);
    }
}
