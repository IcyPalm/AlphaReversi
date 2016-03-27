package src.GameModules.ReversiModule;
import src.GameModules.GameBaseModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Robert on 27-3-2016.
 */
public class ReversiModel extends GameBaseModel {
    private char white = 'W';
    private char black = 'B';
    private int OnMove;
    private HashMap<Integer, Integer> legalMoves;
    private ArrayList<Integer> locations;

    public  ReversiModel(int mySide){
        super(mySide);
        board = new int[8][8];
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        legalMoves = new HashMap<>();
    }

    private void placePiece(int move, int side){
        if(legalMove(move, side)){
            board[move/3][ move%3] = side;
        }
    }

    private boolean legalMove(int move, int side){
        int currentOccupant = board[move/3][ move%3];
        if(currentOccupant == EMPTHY ){
            if(doIGetPieces(move/3,move%3, side )) {
                return true;
            }
        }
        return false;
    }

    private boolean doIGetPieces(int row, int column, int side){
        return false;
    }

    private void getValidMoves(int side){
        legalMoves.clear();

    }

    private void getLocations(int side){
        for(int i = 0; i < returnBoadSize(); i++){
            for(int p = 0; p < returnBoadSize(); p++){
                if(board[i][p] == side){
                    locations.add((i*3+p));
                }
            }
        }
    }


}
