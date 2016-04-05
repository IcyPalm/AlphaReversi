package alphareversi.game;

/**
 * created by Robert on 27-3-2016.
 * This class houses all basis logic for square based gameBoard game.
 */
public class GameBasicSquareBasedModel {
    protected final int empty = 0;
    protected final int playerOne = 1;
    protected final int playerTwo = 2;
    protected final int mySide;

    /**
     * Standard constructor for a gameModule. The super of the game modules contains all shared
     * properties of the gamemodules.
     */
    protected GameBasicSquareBasedModel(int mySide) {
        this.mySide = mySide;
    }

    /**
     * return the value of the Opponent.
     *
     * @return Opponent
     */
    protected int getOpponent(int side) {
        if (side == playerOne) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }

    /**
     * Method to return the position based on the row and column.
     */
    protected int getPosition(int row, int column) {
        return row * 8 + column;
    }

    /**
     * A simple check to see if row and column are inbounds of the gameBoard. I made this into a
     * separate method because the sheer amount of times this has to be checked.
     */
    protected boolean inBounds(int row, int column, int[][] board) {
        if (row >= 0 && row < board.length && column >= 0 && column < board[row].length) {
            return true;
        }
        return false;
    }

    /**
     * A simple method to print the gameBoard in the console.
     */
    public void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }




}
