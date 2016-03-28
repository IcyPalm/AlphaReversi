package src.UnitTests;
import src.GameModules.ReversiModule.ReversiModel;

/**
 * Created by Robert on 28-3-2016.
 */
public class possibleMovesTest {
    public possibleMovesTest(){
      //  testPossibleHorizontalMoves();
      //  testPossibleVerticalMoves();
        testDiagonalMoves();
    }


    private void testPossibleHorizontalMoves(){
        System.out.println("START HORIZONTAL TEST");
        rightTest();
        leftTest();
        System.out.println("END HORIZONTAL TEST");
    }

    private void rightTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int row =4;
        for(int column =1; column < 7; column++){
            board[row][column] = 2;
        }
        board[row][7] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private void leftTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int row = 5;
        for(int column =1; column < 7; column++) {
            board[row][column] = 2;
        }
        board[row][0] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private void testPossibleVerticalMoves(){
        System.out.println("START VERTICAL TEST");
        downTest();
        topTest();
        System.out.println("END VERTICAL TEST");
    }

    private void downTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 0;
        for(int row = 1; row < 7; row++){
            board[row][column] = 2;
        }
        board[7][7] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private void topTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 0;
        for(int row = 1; row < 7; row++){
            board[row][column] = 2;
        }
        board[0][column] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private  void testDiagonalMoves(){
        System.out.println("START DIAGONAL TEST");
        leftUpTest();
        rightDownTest();
        rightUpTest();
        leftDownTest();
        System.out.println("END DIAGONAL TEST");
    }

    private void leftUpTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 1;
        for(int row =1; row < 8; row++){
            board[row][column] = 2;
            column++;
        }
        board[7][7] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private void rightUpTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 6;
        for(int row =1; row < 7; row++){
            board[row][column] = 2;
            column--;
        }
        board[7][0] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private  void rightDownTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 1;
        for(int row =1; row < 7; row++){
            board[row][column] = 2;
            column++;
        }
        board[0][0] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private void leftDownTest(){
        ReversiModel reversiModel = new ReversiModel(1);
        int[][] board = new int[8][8];
        board = reversiModel.getBoard();
        clearBoard(board);
        int column = 6;
        for(int row =1; row < 7; row++){
            board[row][column] = 2;
            column--;
        }
        board[0][7] = 1;
        reversiModel.getValidMoves(1);
        reversiModel.printBoard();
        reversiModel.printPotentialMoves();
    }

    private  void clearBoard(int[][] board ){
        board[3][3] = 0;
        board[3][4] = 0;
        board[4][3] = 0;
        board[4][4] = 0;
    }

}
