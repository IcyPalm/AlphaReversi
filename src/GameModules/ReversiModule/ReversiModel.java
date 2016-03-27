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

    public  ReversiModel(int mySide){
        super(mySide);
        board = new int[8][8];
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        potentialMoves = new HashSet();
        locations = new ArrayList<>();
    }

    private void placePiece(int move, int side){
        if(legalMove(move, side)){
            board[move/3][ move%3] = side;
        }
    }

    private boolean legalMove(int move, int side){
        int currentOccupant = board[move/3][ move%3];
        if(currentOccupant == EMPTHY ){
            if(true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Basicly if
     * @param index
     * @param side
     */
    private void doIHaveMoves(int index, int side){
        int row = index/3;
        int column = index%3;
        horizontalMoves(side, row, column);
        verticalMoves(side, row, column);
        if(potentialMoves.size() >0){

        }

    }

    /**
     * This methode checks all the potential horizontal moves
     * @param side
     * @param row
     * @param column
     */
    private void horizontalMoves(int side, int row, int column){
        int potentialMove;
        if(row+1 >=0 && row+1 < returnBoadSize())
        {
            if(board[row+1][column] == getOpponnent() ){
                potentialMove = movesOnTheRight(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
        if(row-1 >=0 && row-1 < returnBoadSize()){
            if(board[row-1][column] == getOpponnent()){
                potentialMove = movesOnTheLeft(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }
    }

    private void verticalMoves(int side, int row, int column){
        int potentialMove;
        if(column+1>= 0 && column+1 < returnBoadSize()){
            if(board[row][column+1] == getOpponnent()){
                potentialMove = movesAbove(side, row, column);
                if(potentialMove >= 0){
                    potentialMoves.add(potentialMove);
                }
            }
        }

    }

    /**
     * Loops through all the values right of the orginal coordinates and returns a valid move on the right of this
     * piece. If there isn't a valid move, return -1;
     * @param side
     * @param orginalRow
     * @param column
     * @return
     */
    private int movesOnTheRight(int side, int orginalRow, int column){
        int row = orginalRow+1;
        while(true){
            if(row == 8 || board[row][column] == side){ //The second part of the OR shouldn't be possible if we our job right
                break;
            }
            else if(board[row][column]== EMPTHY ){
                return getPosition(row, column);
            }
            row++;
        }
        return -1;
    }

    /**
     * Loops through all the values left of the orginal coordinates and returns a valid move on the left of this
     * piece. If there isn't a valid move, return -1;
     * @param side
     * @param orginalRow
     * @param column
     * @return
     */
    private int movesOnTheLeft(int side, int orginalRow, int column){
        int row = orginalRow+1;
        while(true){
            if(row < 0 ){
                break;
            }else if(board[row][column] == side){ //Shouldn't be possible, but just incase
                break;
            }
            else if(board[row][column]== EMPTHY ){
                return getPosition(row, column);
            }
            row--;
        }
        return -1;
    }

    private int movesAbove(int side, int row, int orginalcolumn){
        return -1;
    }
    private void getValidMoves(int side){
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
        for(int i = 0; i < returnBoadSize(); i++){
            for(int p = 0; p < returnBoadSize(); p++){
                if(board[i][p] == side){
                    locations.add((getPosition(i,p)));
                }
            }
        }
    }


}
