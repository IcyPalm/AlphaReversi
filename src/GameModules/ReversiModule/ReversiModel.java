package GameModules.ReversiModule;

import GameModules.GameBaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Robert on 27-3-2016.
 */
public class ReversiModel extends GameBaseModel {
    private char white = 'W';
    private char black = 'B';
    private HashSet<Integer> potentialMoves;
    private ArrayList<Integer> locations;

    private int playerOneScore;
    private int playerTwoScore;

    private int playerOnTurn;

    private HashMap<Integer, ArrayList<ArrayList<Integer>>> toFlip = new HashMap<>();

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
     * This methode places the actual pieces onto the board. The coordinates it can place pieces are
     * determinded in an earlier stage of the program.
     */
    public boolean placePiece(int move, int side) {
        if (potentialMoves.contains(move)) {
            board[move / 8][move % 8] = side;
            flipper(move);
            playerOnTurn = getOpponnent(side);
            adjustScore(side, EMPTHY);
            return true;
        }
        System.out.println("Not a valid move");
        return false;
    }

    /**
     * This methode flips all the pieces after a piece has been placed. The HashMap in question is
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
     * A simple methode that flips the pieces on a specifike coordinate.
     */
    private void flipPiece(int move) {
        int oldOccupant = board[move / 8][move % 8];
        board[move / 8][move % 8] = oldOccupant;
        int newOccupant = board[move / 8][move % 8];
        adjustScore(newOccupant, oldOccupant);
    }

    /**
     * This methode adjusts the score board of the game. If the sideToDecrement == 0, then there is
     * only a increment
     */
    private void adjustScore(int sideToIncriment, int sideToDecrement) {
        if (sideToIncriment == playerOne) {
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
     * This methode gets all the valid moves for one side.
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
     * Return the HasHset with all the potential moves
     */
    public HashSet<Integer> getPotentialMoves() {
        return potentialMoves;
    }

    /**
     * retun the player on turn
     */
    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    /**
     * return my score
     */
    public int getMyscore() {
        if (mySide == playerOne) {
            return playerOneScore;
        } else
            return playerTwoScore;
    }

    /**
     * Return my opponents score
     */
    public int getOpponentsScore() {
        if (mySide == playerOne) {
            return playerTwoScore;
        } else {
            return playerOneScore;
        }
    }

    /**
     * Boolean determines if it's our turn or not
     */
    public boolean amIOnTurn() {
        if (mySide == playerOnTurn) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This methode fills the location of all the pieces that are on the board for a particular
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
     * This determines all the moves possible with a specifice piece. This methode is used for every
     * piece for a particulair side
     */
    private void doIHaveMoves(int index, int side) { //want to make this a boolean at somepoint
        int row = index / 8;
        int column = index % 8;
        horizontalMoves(side, row, column);
        verticalMoves(side, row, column);
        diagonalMoves(side, row, column);
    }

    /**
     * This methodes keeps updating all the consequences of a specific move. This can be used later
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
     * This methode checks all the potential horizontal moves
     */
    private void horizontalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row, column + 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row][column + 1] == getOpponnent(side)) {
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
            if (board[row][column - 1] == getOpponnent(side)) {
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
     * Loops through all the values right of the orginal coordinates and returns a valid move on the
     * right of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheRight(int side, int row, int orignalColumn, ArrayList<Integer> flipList) {
        int column = orignalColumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == EMPTHY) {
                return getPosition(row, column);
            } else {
                flipList.add(getPosition(row, column));
            }
            column++;
        }
        return -1;
    }

    /**
     * Loops through all the values left of the orginal coordinates and returns a valid move on the
     * left of this piece. If there isn't a valid move, return -1;
     */
    private int movesOnTheLeft(int side, int row, int orignalColumn, ArrayList<Integer> flipList) {
        int column = orignalColumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) { //Shouldn't be possible, but just incase
                break;
            } else if (board[row][column] == EMPTHY) {
                return getPosition(row, column);
            } else {
                flipList.add(getPosition(row, column));
            }
            column--;
        }
        return -1;
    }

    /**
     * This methode checks all the potential vertical moves
     */
    private void verticalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row - 1, column)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column] == getOpponnent(side)) {
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
            if (board[row + 1][column] == getOpponnent(side)) {
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
     * Checks for all the moves above a piece
     */
    private int movesAbove(int side, int orginalRow, int column, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) { //Shouldn't be possible, but just incase
                break;
            } else if (board[row][column] == EMPTHY) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row--;
        }
        return -1;
    }

    /**
     * Checks for all the moves under a piece
     */
    private int movesDownUnder(int side, int orginalRow, int column, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) { //Shouldn't be possible, but just incase
                break;
            } else if (board[row][column] == EMPTHY) {
                return getPosition(row, column);
            } else {
                toBeFlipped.add(getPosition(row, column));
            }
            row++;
        }
        return -1;
    }

    /**
     * This finds all the diagonal moves
     */
    private void diagonalMoves(int side, int row, int column) {
        int potentialMove;
        if (inBounds(row - 1, column - 1)) {
            ArrayList<Integer> piecesToFlip = new ArrayList<>();
            if (board[row - 1][column - 1] == getOpponnent(side)) {
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
            if (board[row - 1][column + 1] == getOpponnent(side)) {
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
            if (board[row + 1][column + 1] == getOpponnent(side)) {
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
            if (board[row + 1][column - 1] == getOpponnent(side)) {
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
     * Find a move that is located diagonal left up
     */
    private int diagonalLeftUp(int side, int orginalRow, int orginalcolumn, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow - 2;
        int column = orginalcolumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == EMPTHY) {
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
     * Find a move that is located diagonal right up
     */
    private int diagonalRightUp(int side, int orginalRow, int orginalcolumn, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow - 2;
        int column = orginalcolumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == EMPTHY) {
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
     * Find a move that is located diagonal left down
     */
    private int diagonalLeftDown(int side, int orginalRow, int orginalcolumn, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow + 2;
        int column = orginalcolumn - 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == EMPTHY) {
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
     * Find a move that is located diagonal right down
     */
    private int diagonalRightDown(int side, int orginalRow, int orginalcolumn, ArrayList<Integer> toBeFlipped) {
        int row = orginalRow + 2;
        int column = orginalcolumn + 2;
        while (true) {
            if (!inBounds(row, column)) {
                break;
            } else if (board[row][column] == side) {
                break;
            } else if (board[row][column] == EMPTHY) {
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
