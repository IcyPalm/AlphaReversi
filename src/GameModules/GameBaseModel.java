package src.GameModules;

/**
 * Created by Robert on 27-3-2016.
 */
public class GameBaseModel {
    protected final int EMPTHY = 0;
    protected final int playerOne = 1;
    protected final int playerTwo = 2;
    protected int[][] board;
    protected int mySide;

    /**
     * Standard constructer for a gameModule. The super of the game modules contains all shared properties of the
     * GameModules.*/
    protected GameBaseModel(int mySide){
        this.mySide = mySide;
    }

    /**
     * Not sure yet if this methode is usefull.
     * @return board.length
     */
    protected int returnBoadSize(){
        return board.length;
    }

    /**
     * return the value of the Opponent
     * @return Opponent
     */
    protected int getOpponnent(){
        if(mySide == playerOne){
            return playerTwo;
        }else{
            return playerOne;
        }
    }

    /**
     * Methode to return the position based on the row and column
     * @param row
     * @param column
     * @return
     */
    protected int getPosition(int row, int column){
        return row*3+column;
    }
}
