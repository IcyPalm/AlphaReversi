package alphareversi.game.reversimodule;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReversiMinimaxGetBestMoveTest {

    boolean running = false;
    ReversiModel model;
    ReversiMinimaxPlayer minimax;
    
    public ReversiMinimaxGetBestMoveTest() {
        model = new ReversiModel(1);
        minimax = new ReversiMinimaxPlayer(model);
    }
    
    
    @Test
    public void test() throws InterruptedException {
        
        // Create a test board
        int[][] testBoard = testBoardOne();
        
        // Print the test board
        //model.printBoard(testBoard);
        System.out.println("");
        
        System.out.println("Waiting 5 seconds . . .");
        System.out.println("");
        // Wait 5 seconds
        Thread.sleep(1000);
        
        System.out.println("Finished waiting 5 seconds . . .");
        
        // Fetch the best move from the minimax player.
        // This interrupt its local thread
        int move = minimax.getBestMove();
        
        if(move > 0) {
            System.out.println("Found a move: " + move);
        }
        
        // Print the board after the found move
        model.printBoard(model.afterMove(move, 1, testBoard));
        
    }
    
    
    public int[][] testBoardOne() {
        int[][] board = new int[8][8];
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        return board;
    }

}
