package alphareversi.game.reversimodule;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

public class ReversiMinimaxGetBestMoveTest extends TestCase {

    boolean running = false;
    ReversiModel model;
    ReversiMinimaxPlayer minimax;

    public ReversiMinimaxGetBestMoveTest() {
        model = new ReversiModel(1);
        // Create a test board
        int[][] testBoard = testBoardThree();
        model.setBoard(testBoard);
        minimax = new ReversiMinimaxPlayer(model);
    }


    @Test
    public void testBestMove() throws InterruptedException {

        // Test started and minimax is computing a tree
        System.out.println("Waiting 5 seconds . . .");

        // Wait 5 seconds
        Thread.sleep(2);
        System.out.println("Finished waiting 5 seconds . . .");

        // Fetch the best move from the minimax player.
        int move = minimax.getBestMove();

        if(move >= 0) {
            System.out.println("Found a move: " + move);
        }

        // Print the board after the found move
        model.printBoard(model.afterMove(move, 1, model.getBoard()));
    }


    public int[][] testBoardOne() {
        int[][] board = new int[8][8];
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        return board;
    }

    public int[][] testBoardTwo() {
        int[][] board = new int[8][8];
        board[1][3] = 2;
        board[1][5] = 2;
        board[2][2] = 1;
        board[2][3] = 2;
        board[2][4] = 2;
        board[2][5] = 1;
        board[3][2] = 1;
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][2] = 1;
        board[4][3] = 2;
        board[4][4] = 1;
        board[5][1] = 2;
        board[5][2] = 2;
        board[5][3] = 1;
        board[6][1] = 2;
        board[6][2] = 1;
        return board;
    }

    public int[][] testBoardThree() {
        int[][] board = new int[8][8];
        board[0][0] = 1;
        board[0][1] = 2;
        board[0][2] = 1;
        board[0][3] = 2;
        board[0][4] = 2;
        board[0][5] = 1;
        board[0][6] = 1;
        board[0][7] = 0;
        board[1][0] = 2;
        board[1][1] = 1;
        board[1][2] = 2;
        board[1][3] = 1;
        board[1][4] = 2;
        board[1][5] = 1;
        board[1][6] = 2;
        board[1][7] = 0;
        board[2][0] = 1;
        board[2][1] = 2;
        board[2][2] = 1;
        board[2][3] = 2;
        board[2][4] = 1;
        board[2][5] = 1;
        board[2][6] = 1;
        board[2][7] = 0;
        board[3][0] = 2;
        board[3][1] = 1;
        board[3][2] = 2;
        board[3][3] = 1;
        board[3][4] = 2;
        board[3][5] = 2;
        board[3][6] = 2;
        board[3][7] = 1;
        board[4][0] = 2;
        board[4][1] = 2;
        board[4][2] = 1;
        board[4][3] = 2;
        board[4][4] = 1;
        board[4][5] = 2;
        board[4][6] = 1;
        board[4][7] = 2;
        board[5][0] = 1;
        board[5][1] = 2;
        board[5][2] = 2;
        board[5][3] = 1;
        board[5][4] = 2;
        board[5][5] = 1;
        board[5][6] = 2;
        board[5][7] = 1;
        board[6][0] = 0;
        board[6][1] = 1;
        board[6][2] = 1;
        board[6][3] = 2;
        board[6][4] = 1;
        board[6][5] = 2;
        board[6][6] = 1;
        board[6][7] = 2;
        board[7][0] = 2;
        board[7][1] = 0;
        board[7][2] = 2;
        board[7][3] = 1;
        board[7][4] = 2;
        board[7][5] = 1;
        board[7][6] = 2;
        board[7][7] = 1;
        return board;
    }

}
