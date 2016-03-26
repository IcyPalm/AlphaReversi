package alphareversi.commands.send;

import alphareversi.commands.SendCommand;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Een uitdaging accepteren.
 *
 * C: challenge accept <uitdaging nummer>
 * S: OK
 */
public class SendChallengeAcceptCommand extends SendCommand {
    private int ChallengeNumber;

    public SendChallengeAcceptCommand()
    {
        this.setMethod("challenge");
        this.setAction("accept");
    }

    public int getChallengeNumber() {
        return ChallengeNumber;
    }

    public void setChallengeNumber(int challengeNumber) {
        ChallengeNumber = challengeNumber;
    }

    @Override
    public String toString() {
        return this.getMethod() + " " + this.getAction() + " " + Integer.toString(ChallengeNumber);
    }
}
