package GameModules.ReversiModule;

import GameModules.GameBasicSquareBasedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Robert on 27-3-2016.
 *This class houses all the logic for the square based game Reversi.
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

    /**
     * Standard constructor for reversi. This also assings the my side variable
     * @param mySide this indicates what is my side of the board.
     */
    public ReversiModel(int mySide) {
        super(mySide);
        board = new int[8][8];
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 2;
        potentialMoves = new HashSet();
        locations = new ArrayList<>();
        playerOneScore = 2;
        playerTwoScore = 2;
        playerOnTurn = playerTwo;
        getValidMoves(playerOnTurn);
    }

    /**
     * This method places the actual pieces onto the board. The coordinates it can place pieces are
     * determined in an earlier stage of the program.
     */
    public void placePiece(int move, int side) {
        if (potentialMoves.contains(move)) {
            board[move / 8][move % 8] = side;
            flipper(move);
            playerOnTurn = getOpponent(side);
            adjustScore(side, empty);
        }
        System.out.println("Not a valid move");
    }

    /**
     * This method flips all the pieces after a piece has been placed. The HashMap in question is
     * filled at the same time when establishing all the valid moves.
     */
    private void flipper(int move) {
        for (ArrayList<Integer> i : toFlip.get(move)) {
            for (int j = 0; j < i.size(); j++) {
                flipPiece(i.get(j));
            }
        }
    }

    /**
     * A simple method that flips the pieces on a specific coordinate.
     */
    private void flipPiece(int move) {
        int oldOccupant = board[move / 8][move % 8];
        board[move / 8][move % 8] = oldOccupant;
        int newOccupant = board[move / 8][move % 8];
        adjustScore(newOccupant, oldOccupant);
    }

    /**
     * This method adjusts the score board of the game. If the sideToDecrement == 0, then there is
     * only a increment
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

    //Region PossibleMoves:

    /**
     * This method gets all the valid moves for one side.
     */
    public void getValidMoves(int side) {
        locations.clear();
        potentialMoves.clear();
        getLocations(side);
        for (int i = 0; i < locations.size(); i++) {
            doIHaveMoves(locations.get(i), side);
        }

    }

    /**
     * Return the Hashset with all the potential moves.
     */
    public HashSet<Integer> getPotentialMoves() {
        return potentialMoves;
    }

    /**
     * return the player on turn.
     */
    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    /**
     * return my score.
     */
    public int getMyScore() {
        if (mySide == playerOne) {
            return playerOneScore;
        } else {
            return playerTwoScore;
        }
    }

    /**
     * Return my opponents score.
     */
    public int getOpponentsScore() {
        if (mySide == playerOne) {
            return playerTwoScore;
        } else {
            return playerOneScore;
        }
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
     * This method fills the location of all the pieces that are on the board for a particular.
     * side
     */
    private void getLocations(int side) {
        for (int i = 0; i < getBoardSize(); i++) {
            for (int p = 0; p < getBoardSize(); p++) {
                if (board[i][p] == side) {
                    locations.add((getPosition(i, p)));
                }
            }
        }
    }

    /**
     * This determines all the moves possible with a specific piece. This method is used for every
     * piece for a particular side
     */
    private void doIHaveMoves(int index, int side) {
        int row = index / 8;
        int column = index % 8;
        horizontalMoves(side, row, column);
        verticalMoves(side, row, column);
        diagonalMoves(side, row, column);
    }

    /**
     * This method's keeps updating all the consequences of a specific move. This can be used later
     * to flip everything after a move
     */
    private void addPotentialToFlip(ArrayList<Integer> toflip, int move) {
        if (toFlip.containsKey(move)) {
            toFlip.get(move).add(toflip);
        } else {
            ArrayList<ArrayList<Integer>> listOfFlipes = new ArrayList<>();
            listOfFlipes.add(toflip);
            toFlip.put(move, listOfFlipes);
        }
    }

    /**
     * This method checks all the potential horizontal moves.
     */
    private void horizontalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row, column + 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row][column + 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row, column + 1));
                potentialMove = movesOnTheRight(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row, column - 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row][column - 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row, column - 1));
                potentialMove = movesOnTheLeft(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
    }

    //Horizontal Moves

    /**
     * Loops through all the values right of the original coordinates and returns a valid move on the
     * right of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheRight(int side, int row, int originalColumn, ArrayList<Integer> flipList) {
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                flipList.add(getPosition(row, column));
            }
            column++;
        }
        return -1;
    }

    /**
     * Loops through all the values left of the original coordinates and returns a valid move on the
     * left of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheLeft(int side, int row, int originalColumn, ArrayList<Integer> flipList) {
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                flipList.add(getPosition(row, column));
            }
            column--;
        }
        return -1;
    }

    /**
     * This method checks all the potential vertical moves.
     */
    private void verticalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row - 1, column)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row - 1, column));
                potentialMove = movesAbove(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row + 1][column] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row + 1, column));
                potentialMove = movesDownUnder(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }

    }

    //Vertical Moves

    /**
     * Checks for all the moves above a piece.
     */
    private int movesAbove(int side, int originalRow, int column, ArrayList<Integer> toBeFlipped) {
        int row = originalRow - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row--;
        }
        return -1;
    }

    /**
     * Checks for all the moves under a piece.
     */
    private int movesDownUnder(int side, int originalRow, int column, ArrayList<Integer> toBeFlipped) {
        int row = originalRow + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row++;
        }
        return -1;
    }

    /**
     * This finds all the diagonal moves.
     */
    private void diagonalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row - 1, column - 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column - 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row - 1, column - 1));
                potentialMove = diagonalLeftUp(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row - 1, column + 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column + 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row - 1, column + 1));
                potentialMove = diagonalRightUp(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column + 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row + 1][column + 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row + 1, column + 1));
                potentialMove = diagonalRightDown(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
        if (inBounds(row + 1, column - 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row + 1][column - 1] == getOpponent(side)) {
                piecesToFlip.add(getPosition(row + 1, column - 1));
                potentialMove = diagonalLeftDown(side, row, column, piecesToFlip);
                if (potentialMove >= 0) {
                    potentialMoves.add(potentialMove);
                    addPotentialToFlip(piecesToFlip, potentialMove);
                }
            }
        }
    }

    //diagonal Moves

    /**
     * Find a move that is located diagonal left up.
     */
    private int diagonalLeftUp(int side, int originalRow, int originalColumn, ArrayList<Integer> toBeFlipped) {
        int row = originalRow - 2;
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row--;
            column--;

        }
        return -1;
    }

    /**
     * Find a move that is located diagonal right up.
     */
    private int diagonalRightUp(int side, int originalRow, int originalColumn, ArrayList<Integer> toBeFlipped) {
        int row = originalRow - 2;
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row--;
            column++;
        }
        return -1;
    }

    /**
     * Find a move that is located diagonal left down.
     */
    private int diagonalLeftDown(int side, int originalRow, int originalColumn, ArrayList<Integer> toBeFlipped) {
        int row = originalRow + 2;
        int column = originalColumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row++;
            column--;
        }
        return -1;
    }

    /**
     * Find a move that is located diagonal right down.
     */
    private int diagonalRightDown(int side, int originalRow, int originalColumn, ArrayList<Integer> toBeFlipped) {
        int row = originalRow + 2;
        int column = originalColumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == empty) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row++;
            column++;
        }
        return -1;
    }

    /**
     * Print out how many moves there are.
     */
    public void printPotentialMoves() {
        System.out.println(potentialMoves.size() + " potential move(s)");
    }
}
