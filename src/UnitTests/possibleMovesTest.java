package UnitTests;

import alphareversi.game.reversimodule.ReversiModel;

/**
 * Created by Robert on 28-3-2016.
 * Tests for finding possible moves
 */
public class possibleMovesTest {
    /**
     * Constructor that does all the tests.
     */
    public possibleMovesTest() {
        //  testPossibleHorizontalMoves();
        //  testPossibleVerticalMoves();
        testDiagonalMoves();
    }

    /**
     * This method does all the horizontal tests.
     */
    private void testPossibleHorizontalMoves() {
        System.out.println("START HORIZONTAL TEST");
        rightTest();
        leftTest();
        System.out.println("END HORIZONTAL TEST");
    }

    /**
     * Horizontal test.
     */
    private void rightTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int row = 4;
        for (int column = 1; column < 7; column++) {
            board[row][column] = 2;
        }
        board[row][7] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * Horizontal test.
     */
    private void leftTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int row = 5;
        for (int column = 1; column < 7; column++) {
            board[row][column] = 2;
        }
        board[row][0] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * This method does all the vertical tests.
     */
    private void testPossibleVerticalMoves() {
        System.out.println("START VERTICAL TEST");
        downTest();
        topTest();
        System.out.println("END VERTICAL TEST");
    }

    /**
     * Vertical test.
     */
    private void downTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 0;
        for (int row = 1; row < 7; row++) {
            board[row][column] = 2;
        }
        board[7][7] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * Vertical test.
     */
    private void topTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 0;
        for (int row = 1; row < 7; row++) {
            board[row][column] = 2;
        }
        board[0][column] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    private void testDiagonalMoves() {
        System.out.println("START DIAGONAL TEST");
        leftUpTest();
        rightDownTest();
        rightUpTest();
        leftDownTest();
        System.out.println("END DIAGONAL TEST");
    }

    /**
     * Diagonal test.
     */
    private void leftUpTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 1;
        for (int row = 1; row < 8; row++) {
            board[row][column] = 2;
            column++;
        }
        board[7][7] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * Diagonal test.
     */
    private void rightUpTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 6;
        for (int row = 1; row < 7; row++) {
            board[row][column] = 2;
            column--;
        }
        board[7][0] = 1;
        reversiModel.getValidMoves(1,board);
        reversiModel.printBoard(board);
    }

    /**
     * Diagonal test.
     */
    private void rightDownTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 1;
        for (int row = 1; row < 7; row++) {
            board[row][column] = 2;
            column++;
        }
        board[0][0] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * Diagonal test.
     */
    private void leftDownTest() {
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 6;
        for (int row = 1; row < 7; row++) {
            board[row][column] = 2;
            column--;
        }
        board[0][7] = 1;
        reversiModel.getValidMoves(1, board);
        reversiModel.printBoard(board);
    }

    /**
     * This clears the gameBoard so we can work with a empty gameBoard we can fill to our needs.
     */
    private void clearBoard(int[][] board) {
        board[3][3] = 0;
        board[3][4] = 0;
        board[4][3] = 0;
        board[4][4] = 0;
    }

}
