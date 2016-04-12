package alphareversi.game.reversimodule;

import alphareversi.game.GameBasicSquareBasedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Robert on 27-3-2016. This class houses all the logic for the square based game
 * Reversi.
 */
public class ReversiModel extends GameBasicSquareBasedModel {
    final private static char WHITE = 'W';
    final private static char BLACK = 'B';
    final private LinkedList<Integer> moveHistory = new LinkedList<>();

    private int playerOnTurn;
    private boolean playing = true;

    private ReversiController viewController;

    private final HashMap<Integer, ArrayList<ArrayList<Integer>>> toFlip = new HashMap<>();

    private Board board;

    /**
     * Standard constructor for reversi. This also assings the my side variable
     *
     * @param mySide this indicates what is my side of the gameBoard.
     */
    public ReversiModel(int firstTurn) {
        super(Board.SELF);
        this.board = new Board(firstTurn);
        this.playerOnTurn = firstTurn;
    }

    /*
     * Added by Maarten
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    public int getMostRecentMove() {
        return this.moveHistory.getFirst();
    }

    /**
     * This method places the actual pieces onto the board. The coordinates it can place pieces
     * are determined in an earlier stage of the program.
     *
     * @param position Where to place the piece.
     * @param player   The owner of this piece.
     */
    public void placePiece(int position, int player) throws InvalidMoveException {
        this.board.place(player, position);
        this.moveHistory.addFirst(position);
        this.flipTurn();

        viewController.updateBoard(this.board.get());
    }

    /**
     * Switch the current playing player. Skips a turn if one player has no
     * available moves.
     */
    private void flipTurn() {
        System.out.println("[Reversi/Model] Flipping turns");
        this.playerOnTurn = this.getOpponent(this.playerOnTurn);
        if (this.board.getAvailableMoves(this.playerOnTurn).size() == 0) {
            System.out.println("[Reversi/Model] Flipping turns: Twice");
            this.playerOnTurn = this.getOpponent(this.playerOnTurn);
        }
    }

    /**
     * This method is purely used for placePiece since it looks at the current board.
     * @param move The move you want to do.
     * @param side The side wanting to do the move.
     * @return Indicates if the move is oké.
     */
    public boolean moveOk(int move, int side) {
        if (this.board.isValidMove(side, move)) {
            return true;
        }
        return false;
    }

    /**
     * return the player on turn.
     */
    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    /**
     * return score of side.
     *
     * @param board The board we want the know the score of.
     * @param side  The side which score we want to know.
     */
    public int getScore(int side, int[][] board) {
        return new Board(board).getScore(side);
    }


    /**
     * Boolean determines if it's our turn or not.
     */
    public boolean amIOnTurn() {
        if (mySide == playerOnTurn) {
            return true;
        }
        return false;
    }

    /**
     * This method gets all the valid moves for one side.
     */
    public Collection<Integer> getValidMoves(int side, int[][] board) {
        return new Board(board).getAvailableMoves(side);
    }

    /**
     * This method fills the location of all the pieces that are on the board for a particular.
     * side
     */
    private ArrayList<Integer> getLocations(int side, int[][] board) {
        ArrayList<Integer> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int p = 0; p < board[i].length; p++) {
                if (board[i][p] == side) {
                    locations.add(getPosition(i, p));
                }
            }
        }
        return locations;
    }

    /**
     * Return the current state of the game.
     */
    public int[][] getBoard() {
        return this.board.get();
    }

    public Board getBoardInstance() {
        return this.board;
    }

    /**
     * A simple method that prints out the corresponding color of each piece on the board.
     */
    public void printBoard(int[][] board) {
        System.out.println(new Board(board).toString());
    }

    /**
     * Stop the game for external reasons (a server error, etc).
     */
    public void setGameOver() {
        this.playing = false;
    }

    /**
     * A simple method that indicates if the game is over or not.
     * @return return if the game is over or not.
     */
    public boolean gameOver() {
        if (this.playing
                && this.board.getAvailableMoves(Board.SELF).size() == 0
                && this.board.getAvailableMoves(Board.OPPONENT).size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves the player with the highest score, return 3 if it's a draw.
     * @param board The board on which we have to determine if we have a winner.
     * @return return the winner or return that we have a draw.
     */
    public int getWinner(int[][] board) {
        int playerOneScore = this.board.getScore(Board.SELF);
        int playerTwoScore = this.board.getScore(Board.OPPONENT);
        if (playerOneScore > playerTwoScore) {
            return Board.SELF;
        } else if (playerTwoScore > playerOneScore) {
            return Board.OPPONENT;
        } else {
            return 3; //draw
        }
    }

    /**
     * Get the character associated with my side.
     * @return The character that is returned.
     */
    public char getMyCharacter() {
        if (mySide == Board.SELF) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    /**
     * Get the character associated with my opponent's side.
     * @return The character that is returned.
     */
    public char getOpponentCharacter() {
        if (mySide != Board.SELF) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public void setViewController(ReversiController viewController) {
        this.viewController = viewController;
    }
}
