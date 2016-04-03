package tictactoemodule;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeModule implements Runnable {
    private Player player;
    private TicTacToeModel model;
    private int opponentMove;

    private volatile boolean opponentPlayed = false;

   /*
    * Constructor Module
    */
    public TicTacToeModule(String player, boolean firstMove) {
        model = new TicTacToeModel();
        if (firstMove) {
            model.setSelfPlays();
        } else {
            model.setOpponentPlays();
        }
        if (!decidePlayer(player)) {
            System.out.println("Wrong Command");
        }
    }

    @Override
    public void run() {
        game();
    }

   /*
    * Runs the game by deciding if we play or the opponent plays.
    */
    private void game() {
        while (!model.gameOver()) {
            if (!model.opponentPlays()) {
                // move versturen naar de server (not implemented)
                // move verwerken in het model
                model.playMove(player.chooseMove());
            } else {
                // A boolean switches if recieveMove() method gets called
                // Simulates Receiving a move from the server
                waitForMove();

                // Play the move in the model
                model.playMove(opponentMove);
                // switch the boolean
                opponentPlayed = false;
            }
        }
    }

   /*
    * Wait till the method receiveMove gets called.
    */
    private void waitForMove() {
        while (!opponentPlayed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException E) {
                System.err.println(E.getMessage());
            }
        }
    }

   /*
    * Receives a move and set opponentPlay to true.
    * @param move The move that the opponent wants to play.
    * It is not checked if the move is valid.
    */
    public synchronized void receiveMove(int move) {
        opponentMove = move;
        opponentPlayed = true;
    }

   /*
    * Decides if the string in the class constructor matches
    * with on off the classes with the interface Player
    */
    private boolean decidePlayer(String player) {
        if (player.equals("HUMAN")) {
            this.player = new Human();
            return true;
        }
        if (player.equals("AI")) {
            this.player = new Ai(model);
            return true;
        }
        return false;
    }
}
