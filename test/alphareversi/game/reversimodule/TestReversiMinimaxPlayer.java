package alphareversi.game.reversimodule;

import static org.junit.Assert.*;

import org.junit.Test;

import alphareversi.game.reversimodule.ReversiMinimaxPlayer;
import alphareversi.game.reversimodule.ReversiModel;



/*
 * Created by Maarten le Clercq on 5-4-2016
 *
 *
 */
public class TestReversiMinimaxPlayer {

    // Test board
    int[][] testBoard = new int[8][8];


    /*
     * This will test if the minimax method in ReversiMinimaxPlayer
     * actually returns a move based on a board and a side
     */
    @Test
    public void testDoesMiniMaxReturnMove() {

        ReversiModel model = new ReversiModel(1);

        ReversiMinimaxPlayer ai = new ReversiMinimaxPlayer(model);

        createTestBoard1();

        model.printBoard(testBoard);

        System.out.println("");

        int move = ai.doMinimax(1, testBoard);
        System.out.println(move);

        System.out.println("");

        model.printBoard(model.afterMove(move, 1, testBoard));

        //int[][] newBoard = model.afterMove(move, 1, testBoard);

        //int[][] newBoard = model.getBoard();

        //model.printBoard(newBoard);

    }


    /*
     * Creates test board 1
     */
    public void createTestBoard1() {
        //Fill with pieces
        testBoard[3][3] = 2;
        testBoard[3][4] = 1;
        testBoard[4][3] = 1;
        testBoard[4][4] = 2;
    }

    public void createTestBoard2() {
        //Fill with pieces
        testBoard[2][2] = 2;
        testBoard[2][3] = 2;
        testBoard[2][4] = 2;

        testBoard[3][2] = 1;
        testBoard[3][3] = 1;
        testBoard[3][4] = 1;
        testBoard[3][5] = 1;

        testBoard[4][2] = 2;
        testBoard[4][3] = 1;
        testBoard[4][4] = 2;
        testBoard[4][5] = 2;

        testBoard[5][3] = 1;
    }


}