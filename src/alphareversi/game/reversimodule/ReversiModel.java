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
     * This method is purely for AI to work with placePiece is for actually doing a move.
     *
     * @param move  The move that has been made.
     * @param side  The side doing the move.
     * @param board The board where the move has been made on.
     * @return The board after the all the flipping has been done.
     */
    public int[][] afterMove(int move, int side, int[][] board) {
        int[][] newBoard = new int[8][8];
        for (int row = 0; row < 8; row++) {
          newBoard[row] = board[row].clone();
        }

        newBoard[move / 8][move % 8] = side;
        flipper(move, newBoard, side);
        return newBoard;
    }

    //region flip pieces

    /**
     * This method flips all the pieces after a piece has been placed. The HashMap in question is
     * filled at the same time when establishing all the valid moves.
     */
    private void flipper(int move, int[][] board, int side) {
        int startRow = move / 8;
        int startColumn = move % 8;
        flipRight(side, startRow, startColumn, board);
        flipLeft(side, startRow, startColumn, board);
        flipUp(side, startRow, startColumn, board);
        flipDown(side, startRow, startColumn, board);
        flipLeftDown(side, startRow, startColumn, board);
        flipLeftUp(side, startRow, startColumn, board);
        flipRightDown(side, startRow, startColumn, board);
        flipRightUp(side, startRow, startColumn, board);
    }

    /**
     * Method that flips all the pieces in the ArrayList in the board that is provide.
     *
     * @param toFlip all the pieces that need to be flipped.
     * @param board  the board where the flipping in happens.
     */
    private void flipPieces(ArrayList<Integer> toFlip, int[][] board) {
        for (int i : toFlip) {
            flipPiece(i, board);
        }
    }

    /**
     * A simple method that flips the pieces on a specific coordinate.
     *
     * @param move  The move that has been made.
     * @param board The board where the move has been made on.
     */
    private void flipPiece(int move, int[][] board) {
        int row = move / 8;
        int column = move % 8;
        board[row][column] = getOpponent(board[row][column]);
    }

    /**
     * Flip all the pieces right of the starting position if possible
     *
     * @param side        The side that is flipping.
     * @param startRow    The starting row.
     * @param startColumn The starting column.
     * @param board       The board where the flips are happening.
     */
    private void flipRight(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow;
        int column = startColumn + 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side) && board[row][column] != empty) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    column++;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces left of the starting position if possible.
     *
     * @param side        The side that is flipping.
     * @param startRow    The starting row.
     * @param startColumn The starting column.
     * @param board       The board where the flips are happening.
     */
    private void flipLeft(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow;
        int column = startColumn - 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side) && board[row][column] != empty) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    column--;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces above the starting position if possible.
     *
     * @param side        the side of the player doing the flip.
     * @param startRow    the starting point of the row.
     * @param startColumn the starting point of the column.
     * @param board       the board where the flipping happens.
     */
    private void flipUp(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow - 1;
        int column = startColumn;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side) && board[row][column] != empty) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row--;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces under the starting position if possible.
     *
     * @param side        the side doing the flipping.
     * @param startRow    the starting row.
     * @param startColumn the starting column.
     * @param board       the board where the flipping happens.
     */
    private void flipDown(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow + 1;
        int column = startColumn;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side) && board[row][column] != empty) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row++;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces right up of the starting position if possible.
     *
     * @param side        the side doing the flipping.
     * @param startRow    the starting row.
     * @param startColumn the starting column.
     * @param board       the board where the flipping happens.
     */
    private void flipRightUp(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow - 1;
        int column = startColumn + 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side)) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row--;
                    column++;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces right down of the starting position if possible.
     *
     * @param side        the side doing the flipping.
     * @param startRow    the starting row.
     * @param startColumn the starting column.
     * @param board       the board where the flipping happens.
     */
    private void flipRightDown(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow + 1;
        int column = startColumn + 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side)) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row++;
                    column++;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces left up of the starting position if possible.
     *
     * @param side        the side doing the flipping.
     * @param startRow    the starting row.
     * @param startColumn the starting column.
     * @param board       the board where the flipping happens.
     */
    private void flipLeftUp(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow - 1;
        int column = startColumn - 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side)) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row--;
                    column--;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
    }

    /**
     * Flip all the pieces left down of the starting position if possible.
     *
     * @param side        the side doing the flipping.
     * @param startRow    the starting row.
     * @param startColumn the starting column.
     * @param board       the board where the flipping happens.
     */
    private void flipLeftDown(int side, int startRow, int startColumn, int[][] board) {
        ArrayList<Integer> toFlip = new ArrayList<>();
        int row = startRow + 1;
        int column = startColumn - 1;
        boolean doIHaveMove = false;
        if (inBounds(row, column, board)) {
            if (board[row][column] == getOpponent(side)) {
                while (true) {
                    if (!inBounds(row, column, board)) {
                        break;
                    } else if (board[row][column] == side) {
                        doIHaveMove = true;
                        break;
                    } else if (board[row][column] == empty) {
                        break;
                    } else {
                        toFlip.add(getPosition(row, column));
                    }
                    row++;
                    column--;
                }
            }
        }
        if (doIHaveMove) {
            flipPieces(toFlip, board);
        }
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
