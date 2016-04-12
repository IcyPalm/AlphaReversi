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
        int[][] testBoard = testBoardTwo();
        model.setBoard(testBoard);
        minimax = new ReversiMinimaxPlayer(model);
    }


    @Test
    public void testBestMove() throws InterruptedException {

        // Test started and minimax is computing a tree
        System.out.println("Waiting 5 seconds . . .");

        // Wait 5 seconds
        Thread.sleep(2000);
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

}
