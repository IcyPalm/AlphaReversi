package alphareversi.game.reversimodule;

import java.util.HashSet;
import java.util.Iterator;


/*
 * MiniMax AI test
 * BASIC IDEA, uses only heat from the heatMap
 * @author Maarten le Clercq
 */
public class ReversiMinimaxPlayer {
    
    // Current side = the player who's turn it is
    // Default starting player 0 = white
    int currentSide = 0;
    
    // Import a model
    ReversiModel model = new ReversiModel(currentSide);
    
    //Current time
    protected long starttime=0;
    
    // How many moves do we think ahead?
    public static final int MAX_DEPTH = 3;
    
    // Time to think, in milliseconds
    // Default 2000 = 2 seconds
    public static final int THINK_TIME = 2000; 
    
    // The current actual board that the AI will work with
    int[][] currentBoard = new int[8][8];
    
    // source: http://www.riscos.com/support/developers/agrm/images/fig19.gif
    private int[] heatMap   = { 7, 2, 5, 4, 4, 5, 2, 7,
                                2, 1, 3, 3, 3, 3, 1, 2,
                                5, 3, 6, 5, 5, 6, 3, 5,
                                4, 3, 5, 6, 6, 5, 3, 4,
                                4, 3, 5, 6, 6, 5, 3, 4,
                                5, 3, 6, 5, 5, 6, 3, 5,
                                2, 1, 3, 3, 3, 3, 1, 2,
                                7, 2, 5, 4, 4, 5, 2, 7  };
    
    int heat = 0;
    
    // Potential moves
    HashSet potentialMoves;
    
    // Potential depth moves
    HashSet potentialDepthMoves;
    
    
    public ReversiMinimaxPlayer() {
        
    }
    
    
    /*
     * Initialization of the AI.
     * Prepares and calls the minimax method.
     * @return calculatedMove The best move according to the heatmap.
     * @param board The board to work with.
     * @param side The side whose turn it is.
     * TODO: This method needs a prevention from executing at all if side+board has NO valid moves, 
     * will now return 0.
     */
    public int doMinimax(int side, int[][] board) {
        
        currentSide = side;
        currentBoard = board;
        
        // This will be the calculated move.
        int calculatedMove = 0;
        
        starttime = System.currentTimeMillis();
        int oldHeatValue = 0;
        int heatValue = 0;
        
        // Get all directly possible moves and iterate through them
        // TODO model.getPotentialMoves()
        potentialMoves = model.getValidMoves(currentSide, currentBoard);
        Iterator it = potentialMoves.iterator();
        while (it.hasNext()) {
            
            int move = (int) it.next();
            
            // Calculate heatValue for every move. Save hottest move.
            // TODO boardAfterMove()
            heatValue = heatMap[move];
            heatValue += minimax(currentSide, move, 1);
            
            if (heatValue > oldHeatValue) {
                calculatedMove = move;
            }
            oldHeatValue = heatValue;
        }
        
        return calculatedMove;
    }
    
    /*
     * Recursive heat-finding AI with base cases:
     * Depth of moves reached
     * Time limit reached
     * Game finished 
     * @return heat The heat of a move
     */
    private int minimax(int side, int move, int depth) {
        

        // Current depth
        int currentDepth = depth;
        
        // The board after this move has been made
        int[][] boardAfterMove = model.afterMove(move, currentSide, currentBoard);
        
        
        // Base cases ----------------------------
        
        // If depth threshold has been reached, 
        // stop and return move
        if (currentDepth >= MAX_DEPTH){
            System.out.println("Maximum depth reached!");
            return heat;
        }
        // Time up
        if (getTimeLeft()<=0) {
            System.out.println("Time's up!");
            return heat;
        }
        // Game finished - has anyone won?
        if (isGameFinished(boardAfterMove)) {
            // TODO - some action to perform like:
            // model.getPieces(mySide)
        }
        
        // End Base cases -----------------------

        
        // Recursive MiniMax
        potentialDepthMoves = model.getValidMoves(currentSide, boardAfterMove);
        Iterator it = potentialMoves.iterator();
        int newSide = flipSide(side);
        while (it.hasNext()) {
            
            // Subtract the heat if the heat is meant for your opponent.
            if (newSide != currentSide) {
                heat -= minimax(newSide, (int) it.next(), currentDepth+1);
            } else {
                heat += minimax(newSide, (int) it.next(), currentDepth+1);
            }
            
        }
        
        return heat;
    }
    
    public boolean isGameFinished(int[][] board) {
        if ((model.getValidMoves(1, currentBoard).size() == 0) && (model.getValidMoves(2, currentBoard).size() == 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public int flipSide(int side) {
        if (side == 1) {
            return 2;
        } else {
            return 1;
        }
    }
    

    private long getTimeLeft() {
        return THINK_TIME - (System.currentTimeMillis() - starttime);
    }
    
}
