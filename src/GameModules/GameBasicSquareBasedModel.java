package GameModules;

/**
 * created by Robert on 27-3-2016.
 * This class houses all basis logic for square based board game.
 */
public class GameBasicSquareBasedModel {
    protected final int empty = 0;
    protected final int playerOne = 1;
    protected final int playerTwo = 2;
    protected int[][] board;
    protected final int mySide;

    /**
     * Standard constructor for a gameModule. The super of the game modules contains all shared
     * properties of the GameModules.
     */
    protected GameBasicSquareBasedModel(int mySide) {
        this.mySide = mySide;
    }

    /**
     * Not sure yet if this method is useful.
     *
     * @return board.length
     */
    protected int getBoardSize() {
        return board.length;
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
     * A simple check to see if row and column are inbounds of the board. I made this into a
     * separate method because the sheer amount of times this has to be checked.
     */
    protected boolean inBounds(int row, int column) {
        if (row >= 0 && row < getBoardSize() && column >= 0 && column < getBoardSize()) {
            return true;
        }
        return false;
    }

    /**
     * A simple method to print the board in the console.
     */
    public void printBoard() {
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public int[][] getBoard() {
        return board;
    }
}
