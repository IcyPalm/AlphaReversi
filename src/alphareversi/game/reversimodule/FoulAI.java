package alphareversi.game.reversimodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import alphareversi.Connection;
import alphareversi.commands.send.SendChallengeCommand;
import alphareversi.commands.send.SendLoginCommand;
import alphareversi.commands.send.SendMessageCommand;

/**
 * Created by daant on 25-Mar-16.
 */
public class FoulAI implements Player {
    private final Integer[] zone1 = {0, 7, 56, 63};
    private final Integer[] zone2 = {2, 3, 4, 5, 16, 24, 32, 40, 58, 59, 60, 62, 23, 31, 39, 47};
    private final Integer[] zone3 = {18, 19, 20, 21, 26, 27, 28, 29, 34, 35, 36, 37, 42, 43, 44, 45};
    private final Integer[] zone4 = {10, 11, 12, 13, 17, 25, 33, 41, 50, 51, 52, 53, 22, 30, 38, 46};
    private final Integer[] zone5 = {8, 9, 1, 6, 14, 15, 48, 49, 57, 54, 55, 62};
    private ReversiModel model;
    private List<ActionListener> actionListeners = new LinkedList<>();

    public FoulAI(ReversiModel model) {
        this.model = model;


        Connection connectionMain = Connection.getInstance();
//        Connection connection = new Connection();
//        try {
//            connection.startConnection(connectionMain.comms.getInetAddress().getHostAddress(), connectionMain.comms.getPort());
//            SendLoginCommand loginCommand = new SendLoginCommand("Henk de boze robot " + randInt(0, 9000));
//            connection.sendMessage(loginCommand);
//            new Thread() {
//                public void run() {
//                    while (true) {
//                        if (connection.getConnected()) {
//                            SendChallengeCommand challenge = new SendChallengeCommand(model.getOpponentUsername(), "Reversi");
//                            connection.sendMessage(challenge);
//                        }
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException exception) {
//                            exception.printStackTrace();
//                        }
//                    }
//                }
//            }.start();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
        for (int i = 0; i < 5; i++) {
            Connection connection = new Connection();
            try {
                connection.startConnection(connectionMain.comms.getInetAddress().getHostAddress(), connectionMain.comms.getPort());
                SendLoginCommand loginCommand = new SendLoginCommand("Henk de boze ಠ‿ಠ robot (╯°□°）╯︵ ┻━┻ " + randInt(0, 9000) + i);
                connection.sendMessage(loginCommand);
                new Thread() {
                    public void run() {
                        int i = 0;
                        while (i < 20) {
                            if (connection.getConnected()) {
                                SendMessageCommand message = new SendMessageCommand(model.getOpponentUsername(), "Ik ben henk de boze ಠ‿ಠ robot (╯°□°）╯︵ ┻━┻");
                                connection.sendMessage(message);
                                SendChallengeCommand challenge = new SendChallengeCommand(model.getOpponentUsername(), "Reversi");
                                connection.sendMessage(challenge);
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException exception) {
                                exception.printStackTrace();
                            }
                            i++;
                        }
                        connection.stopConnection();
                    }
                }.start();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Add an action listener.
     */
    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }

    /**
     * Remove an action listener.
     */
    public void removeActionListener(ActionListener listener) {
        this.actionListeners.remove(listener);
    }

    private void notifyActionListeners(int move) {
        for (ActionListener listener : this.actionListeners) {
            listener.actionPerformed(
                    new ActionEvent(this, move, "")
            );
        }
    }

    public void startTurn() {
        Collection<Integer> movesSet = model.getValidMoves(model.getMySide(), model.getBoard());
        Integer[] moves = movesSet.toArray(new Integer[movesSet.size()]);

        Integer[][] zones = {zone1, zone2, zone3, zone4, zone5};

        for (int i = 0; i < zones.length; i++) {
            ArrayList<Integer> topMoves = new ArrayList<>();
            for (int j = 0; j < moves.length; j++) {
                if (Arrays.asList(zones[i]).contains(moves[j])) {
                    topMoves.add(moves[j]);
                }
            }
            if (topMoves.size() > 0) {
                int random = randInt(0, topMoves.size() - 1);
                notifyActionListeners(topMoves.get(random));
                return;
            }
        }
        int random = randInt(0, moves.length - 1);
        notifyActionListeners(moves[random]);
    }

    private int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
