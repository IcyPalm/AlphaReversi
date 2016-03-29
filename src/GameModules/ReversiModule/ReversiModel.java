package src.GameModules.ReversiModule;
import src.GameModules.GameBaseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.jar.Pack200;

/**
 * Created by Robert on 27-3-2016.
 */
public class ReversiModel extends GameBaseModel {
    private char white = 'W';
    private char black = 'B';
    private HashSet<Integer> potentialMoves;
    private ArrayList<Integer> locations;

    private int myScore;
    private int opponentScore;

    private int playerOnTurn;

    public  ReversiModel(int mySide){
        super(mySide);
        board = new int[8][8];
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        potentialMoves = new HashSet();
        locations = new ArrayList<>();
        myScore =2;
        opponentScore =2;
        playerOnTurn = playerTwo;
        getValidMoves(playerOnTurn);
    }

    public boolean placePiece(int move, int side){
        if(potentialMoves.contains(move)){
            board[move/8][ move%8] = side;
            flipper(move);
            return true;
        }
        return false;
    }

    private boolean legalMove(int move){
        int currentOccupant = board[move/8][ move%8];
        if(currentOccupant == EMPTHY ){
            return true;
        }
        return false;
    }

    private void flipper(int move){
        boolean madeMove = false;
        int rowMove = move/8;
        int columnMove = move%8;
        for(int i: locations){
            int brotherRow = i/8;
            int brotherColumn = i%8;
            if(rowMove == brotherRow){
                if(columnMove > brotherColumn){
                    //if the startpoint of the move is bigger than the brother Column start from BrotherColumn
                    flipHorizontal(rowMove, brotherColumn, columnMove);
                }else{
                    flipHorizontal(rowMove, columnMove, brotherColumn);
                }
                madeMove = true;
            }
            if(columnMove == brotherColumn){
                if(rowMove > brotherRow){
                    flipVertical(brotherRow, rowMove,columnMove);
                }else{
                    flipVertical(rowMove,brotherRow ,columnMove);
                }
                madeMove = true;
            }
            if(madeMove){
                break;
            }
        }
    }

    private void flipHorizontal(int row,int startColumn, int endColumn){
        int column = startColumn;
        while(column != endColumn){
            column++;//We start with the ++ since the startColumn already has the right piece, so does the end column
            flipPiece(row, column);
        }
    }

    private void flipVertical(int startRow, int endRow, int column){
        int row = startRow;
        while(row != endRow){
            row++; //We start with the ++ since the startRow already has the right piece, so does the end row
            flipPiece(row, column);
        }
    }

    private void flipPiece(int row, int column){
       board[row][column] = getOpponnent(board[row][column]);
    }

    //Region PossibleMoves:

    public void getValidMoves(int side){
        locations.clear();
        potentialMoves.clear();
        getLocations(side);
        for(int i = 0; i < locations.size(); i++){
            doIHaveMoves(locations.get(i), side);
        }

    }

    public HashSet<Integer> getPotentialMoves(){
        return potentialMoves;
    }

    public int getPlayerOnTurn(){
        return playerOnTurn;
    }

    public int getMyScore(){
        return myScore;
    }

    public int getOpponentScore(){
        return opponentScore;
    }

    public boolean amIOnTurn(){
        if(mySide == playerOnTurn){
            return true;
        }else{
            return false;
        }
    }

    /**
     * This methode fills the location of all the pieces that are on the board for a particular side
     * @param side
     */
    private void getLocations(int side){
        for(int i = 0; i < getBoardSize(); i++){
            for(int p = 0; p < getBoardSize(); p++){
                if(board[i][p] == side){
                    locations.add((getPosition(i,p)));
                }
            }
        }
    }

    /**
     * Basicly if
     * @param index
     * @param side
     */
    private void doIHaveMoves(int index, int side){ //want to make this a boolean at somepoint
        int row = index/8;
        int column = index%8;
        horizontalMoves(side, row, column);
        verticalMoves(side, row, column);
        diagonalMoves(side, row, column);
    }

    /**
     * This methode checks all the potential horizontal moves
     * @param side
     * @param row
     * @param column
     */
    private void horizontalMoves(int side, int row, int column){
        int potentialMove;
        if(inBounds(row, column+1))
        {
            if(board[row][column+1] == getOpponnent(side) ){
                potentialMove = movesOnTheRight(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if(inBounds(row, column-1)){
            if(board[row][column-1] == getOpponnent(side)){
                potentialMove = movesOnTheLeft(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
    }

    //Horizontal Moves

    /**
     * Loops through all the values right of the orginal coordinates and returns a valid move on the right of this
     * piece. If there isn't a valid move, return -1;
     * @param side
     * @param row
     * @param orignalColumn
     * @return
     */
    private int movesOnTheRight(int side, int row, int orignalColumn){
        int column = orignalColumn+2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side) {
                break;
            } else if(board[row][column]== EMPTHY ){
                return getPosition(row, column);
            }
            column++;
        }
        return -1;
    }

    /**
     * Loops through all the values left of the orginal coordinates and returns a valid move on the left of this
     * piece. If there isn't a valid move, return -1;
     * @param side
     * @param orignalColumn
     * @param row
     * @return
     */
    private int movesOnTheLeft(int side, int row, int orignalColumn){
        int column = orignalColumn-2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side){ //Shouldn't be possible, but just incase
                break;
            }
            else if(board[row][column]== EMPTHY ){
                return getPosition(row, column);
            }
            column--;
        }
        return -1;
    }

    private void verticalMoves(int side, int row, int column){
        int potentialMove;
        if(inBounds(row-1, column)){
            if(board[row-1][column] == getOpponnent(side)){
                potentialMove = movesAbove(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if( inBounds(row+1, column)){
            if(board[row+1][column] == getOpponnent(side)){
                potentialMove = movesDownUnder(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }

    }

    //Vertical Moves
    private int movesAbove(int side, int orginalRow, int column){
        int row = orginalRow-2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side) { //Shouldn't be possible, but just incase
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row--;
        }
        return -1;
    }

    private int movesDownUnder(int side, int orginalRow, int column){
        int row = orginalRow+2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side) { //Shouldn't be possible, but just incase
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row++;
        }
        return -1;
    }

    private void diagonalMoves(int side, int row, int column){
        int potentialMove;
        if(inBounds(row-1, column-1)){
            if(board[row-1][column-1] == getOpponnent(side)){
                potentialMove = diagonalLeftUp(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if(inBounds(row-1, column+1)){
            if(board[row-1][column+1] == getOpponnent(side)){
                potentialMove = diagonalRightUp(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if(inBounds(row+1, column+1)){
            if(board[row+1][column+1] == getOpponnent(side)){
                potentialMove = diagonalRightDown(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if(inBounds(row+1, column-1)){
            if(board[row+1][column-1] == getOpponnent(side)){
                potentialMove = diagonalLeftDown(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
    }

    //diagonal Moves

    private int diagonalLeftUp(int side, int orginalRow, int orginalcolumn){
        int row = orginalRow-2;
        int column = orginalcolumn -2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side){
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row--;
            column--;

        }
        return -1;
    }

    private int diagonalRightUp(int side, int orginalRow, int orginalcolumn){
        int row = orginalRow-2;
        int column = orginalcolumn +2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side){
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row--;
            column++;
        }
        return -1;
    }

    private int diagonalLeftDown(int side, int orginalRow, int orginalcolumn){
        int row = orginalRow+2;
        int column = orginalcolumn -2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side){
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row++;
            column--;
        }
        return -1;
    }

    private int diagonalRightDown(int side, int orginalRow, int orginalcolumn){
        int row = orginalRow+2;
        int column = orginalcolumn +2;
        while(true){
            if(!inBounds(row, column)){
                break;
            }else if(board[row][column] == side){
                break;
            }else if(board[row][column] == EMPTHY){
                return getPosition(row, column);
            }
            row++;
            column++;
        }
        return -1;
    }

    public void printPotentialMoves(){
        System.out.println(potentialMoves.size() + " potential move(s)");
    }
}
