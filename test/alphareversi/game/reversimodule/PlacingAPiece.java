package alphareversi.game.reversimodule;

import org.junit.Test;

import alphareversi.game.reversimodule.ReversiModel;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Robert on 31-3-2016. Tests for placing pieces
 */
public class PlacingAPiece  {





    /**
     * Test for placing a piece at the top left corner. The expected result is that all the pieces
     * will be turned.
     *
     */
    @Test
    public void createLeftTopCornerTestCase() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = reversiModel.getBoard();
        clearBoard(board);
        int row = 0;
        int column = 1;
        while (column != 7) {
            board[row][column] = 2;
            column++;
        }
        board[row][7] = 1;
        row = 1;
        column = 0;
        while (row != 7) {
            board[row][column] = 2;
            row++;
        }
        board[7][column] = 1;
        row = 1;
        column = 1;
        while (row != 7) {
            board[row][column] = 2;
            row++;
            column++;
        }
        board[7][7] = 1;
        reversiModel.printBoard(board);
        reversiModel.placePiece(0, 1);
        System.out.println("Piece placed");
        reversiModel.printBoard(board);
        assertEquals(22, reversiModel.getScore(1, board));

    }

    /**
     * Test case for placing pieces.
     */
    @Test
    public void createRightDownCornerTestCase() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = reversiModel.getBoard();
        clearBoard(board);
        int row = 7;
        int column = 1;
        while (column != 7) {
            board[row][column] = 2;
            column++;
        }
        board[row][0] = 1;
        row = 1;
        column = 7;
        while (row != 7) {
            board[row][column] = 2;
            row++;
        }
        board[0][column] = 1;
        row = 6;
        column = 6;
        while (row != 0) {
            board[row][column] = 2;
            row--;
            column--;
        }
        board[0][0] = 1;
        reversiModel.printBoard(board);
        reversiModel.placePiece(63, 1);
        System.out.println("Piece placed");
        reversiModel.printBoard(board);
        assertEquals(22, reversiModel.getScore(1, board));
    }

    /**
     * This clears the gameBoard so we can work with a empty gameBoard we can fill to our needs.
     */
    public void clearBoard(int[][] board) {
        board[3][3] = 0;
        board[3][4] = 0;
        board[4][3] = 0;
        board[4][4] = 0;
    }

}
