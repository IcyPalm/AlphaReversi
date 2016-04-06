package alphareversi.game.reversimodule;

import alphareversi.game.GameBasicSquareBasedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



/**
 * Created by Robert on 27-3-2016. This class houses all the logic for the square based game
 * Reversi.
 */
public class ReversiModel extends GameBasicSquareBasedModel {
    private char white = 'W';
    private char black = 'B';
    private final HashSet<Integer> potentialMoves;
    private final ArrayList<Integer> locations;

    private int playerOneScore;
    private int playerTwoScore;

    private int playerOnTurn;

    private final HashMap<Integer, ArrayList<ArrayList<Integer>>> toFlip = new HashMap<>();

    private int[][] gameBoard;

    /**
     * Standard constructor for reversi. This also assings the my side variable
     *
     * @param mySide this indicates what is my side of the gameBoard.
     */
    public ReversiModel(int mySide) {
        super(mySide);
        gameBoard = new int[8][8];
        gameBoard[3][3] = 1;
        gameBoard[3][4] = 2;
        gameBoard[4][3] = 2;
        gameBoard[4][4] = 1;
        potentialMoves = new HashSet();
        locations = new ArrayList<>();
        playerOneScore = 2;
        playerTwoScore = 2;
        playerOnTurn = playerTwo;
        getValidMoves(playerOnTurn, this.gameBoard);
    }

    /**
     * This method places the actual pieces onto the gameBoard. The coordinates it can place pieces
     * are determined in an earlier stage of the program.
     */
    public void placePiece(int move, int side) {
        gameBoard[move / 8][move % 8] = side;
        flipper(move, gameBoard, side);
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
        int[][] newBoard = board.clone();
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
     * This method adjusts the score gameBoard of the game. If the sideToDecrement == 0, then there
     * is only a increment
     *
     * @param sideToIncrement The side to increment.
     * @param sideToDecrement The side to decrement.
     */
    private void adjustScore(int sideToIncrement, int sideToDecrement) {
        if (sideToIncrement == playerOne) {
            playerOneScore++;
        } else {
            playerTwoScore++;
        }
        if (sideToDecrement == playerOne) {
            playerOneScore--;
        } else if (sideToDecrement == playerTwo) {
            playerTwoScore--;
        }
    }


    //endregion


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
        int score = 0;
        for (int[] i : board) {
            for (int j : i) {
                if (j == side) {
                    score++;
                }
            }
        }
        return score;
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
    public HashSet<Integer> getValidMoves(int side, int[][] board) {
        ArrayList<Integer> locations = new ArrayList<>();
        locations = getLocations(side, board);
        HashSet<Integer> potentialMoves = new HashSet<>();
        for (int i = 0; i < locations.size(); i++) {
            doIHaveMoves(locations.get(i), side, board, potentialMoves);
        }
        return potentialMoves;
    }

    /**
     * This method fills the location of all the pieces that are on the gameBoard for a particular.
     * side
     */
    private ArrayList<Integer> getLocations(int side, int[][] board) {
        ArrayList<Integer> locations = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int p = 0; p < board[i].length; p++) {
                if (board[i][p] == side) {
                    locations.add((getPosition(i, p)));
                }
            }
        }
        return locations;
    }

    //region Do I have moves

    /**
     * This determines all the moves possible with a specific piece. This method is used for every
     * piece for a particular side
     */
    private void doIHaveMoves(int index, int side, int[][] board, HashSet<Integer> potentialMoves) {
        int row = index / 8;
        int column = index % 8;
        horizontalMoves(side, row, column, board, potentialMoves);
        verticalMoves(side, row, column, board, potentialMoves);
        diagonalMoves(side, row, column, board, potentialMoves);
    }


    /**
     * This method checks all the potential horizontal moves.
     */
    private void horizontalMoves(int side, int row, int column, int[][] board,
                                 HashSet<Integer> potentialMoves) {
        int potentialMove;
        if (inBounds(row, column + 1, board)) {
            if (board[row][column + 1] == getOpponent(side)) {
                potentialMove = movesOnTheRight(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if (inBounds(row, column - 1, board)) {
            if (board[row][column - 1] == getOpponent(side)) {
                potentialMove = movesOnTheLeft(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
    }

    //Horizontal Moves

    /**
     * Loops through all the values right of the original coordinates and returns a valid move on
     * the right of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheRight(int side, int row, int originalColumn, int[][] board) {
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            column++;
        }
        return -1;
    }

    /**
     * Loops through all the values left of the original coordinates and returns a valid move on the
     * left of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheLeft(int side, int row, int originalColumn, int[][] board) {
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            column--;
        }
        return -1;
    }

    /**
     * This method checks all the potential vertical moves.
     */
    private void verticalMoves(int side, int row, int column,
                               int[][] board, HashSet<Integer> potentialMoves) {
        int potentialMove;
        if (inBounds(row - 1, column, board)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row - 1, column));
                potentialMove = movesAbove(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    //addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column, board)) {
            if (board[row + 1][column] == getOpponent(side)) {
                potentialMove = movesDownUnder(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    //addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }

    }

    //Vertical Moves

    /**
     * Checks for all the moves above a piece.
     */
    private int movesAbove(int side, int originalRow, int column, int[][] board) {
        int row = originalRow - 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row--;
        }
        return -1;
    }

    /**
     * Checks for all the moves under a piece.
     */
    private int movesDownUnder(int side, int originalRow, int column, int[][] board) {
        int row = originalRow + 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row++;
        }
        return -1;
    }

    /**
     * This finds all the diagonal moves.
     */
    private void diagonalMoves(int side, int row, int column,
                               int[][] board, HashSet<Integer> potentialMoves) {
        int potentialMove;
        if (inBounds(row - 1, column - 1, board)) {
            if (board[row - 1][column - 1] == getOpponent(side)) {
                potentialMove = diagonalLeftUp(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if (inBounds(row - 1, column + 1, board)) {
            if (board[row - 1][column + 1] == getOpponent(side)) {
                potentialMove = diagonalRightUp(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column + 1, board)) {
            if (board[row + 1][column + 1] == getOpponent(side)) {
                potentialMove = diagonalRightDown(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column - 1, board)) {
            if (board[row + 1][column - 1] == getOpponent(side)) {
                potentialMove = diagonalLeftDown(side, row, column, board);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                }
            }
        }
    }

    //diagonal Moves

    /**
     * Find a move that is located diagonal left up.
     */
    private int diagonalLeftUp(int side, int originalRow, int originalColumn, int[][] board) {
        int row = originalRow - 2;
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row--;
            column--;

        }
        return -1;
    }

    /**
     * Find a move that is located diagonal right up.
     */
    private int diagonalRightUp(int side, int originalRow, int originalColumn, int[][] board) {
        int row = originalRow - 2;
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row--;
            column++;
        }
        return -1;
    }

    /**
     * Find a move that is located diagonal left down.
     */
    private int diagonalLeftDown(int side, int originalRow, int originalColumn, int[][] board) {
        int row = originalRow + 2;
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row++;
            column--;
        }
        return -1;
    }

    /**
     * Find a move that is located diagonal right down.
     */
    private int diagonalRightDown(int side, int originalRow, int originalColumn, int[][] board) {
        int row = originalRow + 2;
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column, board)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            }
            row++;
            column++;
        }
        return -1;
    }

    //endregion region


    /**
     * Return the current state of the game.
     */
    public int[][] getBoard() {
        return gameBoard;
    }

    /**
     * A simple method that prints out the corresponding color of each piece on the board.
     */
    public void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    System.out.print(white);
                } else if (board[i][j] == playerTwo) {
                    System.out.print(black);
                } else {
                    System.out.println("0");
                }

            }
            System.out.println();
        }
    }

    /**
     * A simple method that indicates if the game is over or not.
     * @return return if the game is over or not.
     */
    public boolean gameOver(int[][] board) {
        if (getValidMoves(playerOne, board).size() == 0
                && getValidMoves(playerTwo, board).size() == 0) {
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
        int playerOneScore = getScore(playerOne, board);
        int playerTwoScore = getScore(playerTwo, board);
        if (playerOneScore > playerTwoScore) {
            return playerOne;
        } else if ( playerTwoScore > playerOneScore) {
            return playerTwoScore;
        } else {
            return 3; //draw
        }
    }
}
