package alphareversi.game.reversimodule;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.tree.TreeNode;


/*
 * MiniMax AI test
 * BASIC IDEA, uses only heat from the heatMap
 * @author Maarten le Clercq
 */
public class ReversiMinimaxPlayer {
    int currentSide = 0;
    ReversiModel model = new ReversiModel(currentSide);
    protected long starttime=0;
    public static final int MAX_DEPTH = 2;
    public static final int THINK_TIME = 2000;
    int[][] currentBoard = new int[8][8];
    int[][] secondBoard = new int[8][8];
    HashSet potentialMoves;
    HashSet potentialDepthMoves;

    ReversiHeatmap heatMap = new ReversiHeatmap();
    int moveNumber = 0;

    public ReversiMinimaxPlayer() {
        ReversiHeatmap heatmap = new ReversiHeatmap();
        LinkedList<Node> list = new LinkedList<Node>();
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

        int oldHeatValue = -100;
        int heatValue = 0;

        // Get all directly possible moves and iterate through them
        // TODO model.getPotentialMoves()

        // Set starting time
        starttime = System.currentTimeMillis();

        potentialMoves = model.getValidMoves(currentSide, currentBoard);
        Iterator it = potentialMoves.iterator();
        while (it.hasNext()) {

            int move = (int) it.next();
            System.out.println("First base move");

            // Calculate heatValue for every move. Save hottest move.
            // TODO afterMove() returns an incorrect board!
            heatValue = heatMap.getHeat(move);

            heatValue += minimax(currentSide, move, 0, heatValue, currentBoard);

            if (heatValue > oldHeatValue) {
                calculatedMove = move;
            }
            oldHeatValue = heatValue;
        }
        System.out.println("Calculated a move! " + calculatedMove);

        moveNumber++;
        return calculatedMove;
    }

    /*
     * Recursive heat-finding AI with base cases:
     * Depth of moves reached
     * Time limit reached
     * Game finished
     * @return heat The heat of a move
     */
    private int minimax(int side, int move, int depth, int heat, int[][] thisBoard) {

        // Base cases ----------------------------

        // If depth threshold has been reached,
        // stop and return move
        if (depth > MAX_DEPTH) {
            System.out.println("Maximum depth reached! " + depth + MAX_DEPTH + " Heat = " + heat);
            return heat;
        }
        // Time up
        if (getTimeLeft()<=0) {
            System.out.println("Time's up!");
            return heat;
        }


        secondBoard = thisBoard;

        // The board after this move has been made
        model.setBoard(thisBoard);
        int[][] boardAfterMove = model.afterMove(move, side, thisBoard);
        model.printBoard(boardAfterMove);
        System.out.println();

        // Game finished - has anyone won?
        //if (isGameFinished(boardAfterMove)) {
            // TODO - some action to perform like:
            // model.getPieces(mySide)
        //}

        // End Base cases -----------------------


        // Recursive MiniMax
        int newSide = flipSide(side);
        potentialDepthMoves = model.getValidMoves(newSide, boardAfterMove);
        Iterator it = potentialDepthMoves.iterator();

        System.out.println("Calculating depth moves, depth = " + depth + " Heat = " + heat);
        while (it.hasNext()) {
            // Iets met nodes en childs

            int move2 = (int) it.next();

            // Subtract the heat if the heat is meant for your opponent.
            if (newSide == currentSide) {
               return minimax(newSide, move2, depth+1, (heat + heatMap[move2]), secondBoard);
            } else {
               return minimax(newSide, move2, depth+1, (heat - heatMap[move2]), secondBoard);
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
