package src.GameModules.ReversiModule;
import src.GameModules.GameBaseModel;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Robert on 27-3-2016.
 */
public class ReversiModel extends GameBaseModel {
    private char white = 'W';
    private char black = 'B';
    private HashSet potentialMoves;
    private ArrayList<Integer> locations;

    private int myScore;
    private int opponentScore;

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
        getValidMoves(mySide);
    }

    public void placePiece(int move, int side){
        if(legalMove(move, side)){
            board[move/8][ move%8] = side;
        }
    }

    private boolean legalMove(int move, int side){
        int currentOccupant = board[move/8][ move%8];
        if(currentOccupant == EMPTHY ){
            if(true) {
                return true;
            }
        }
        return false;
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
