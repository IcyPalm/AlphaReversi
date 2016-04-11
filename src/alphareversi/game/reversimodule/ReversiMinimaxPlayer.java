package alphareversi.game.reversimodule;

import java.util.Collection;
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
    ReversiModel model;
    protected long starttime = 0;
    public static final int MAX_DEPTH = 2;
    public static final int THINK_TIME = 2000;
    int[][] currentBoard = new int[8][8];
    ReversiHeatmap heatMap = new ReversiHeatmap();
    Collection<Node> leaves = new LinkedList<>();
    int moveNumber = 0;
    Node root;

    public ReversiMinimaxPlayer(ReversiModel model) {
        this.model = model;
        int side;
        if(model.amIOnTurn()) {
            side = 1;
        } else {
            side = 2;
        }
        this.root = new Node(currentBoard, side, 0, 0);
        this.leaves.add(this.root);
    }

    public void setRoot(Node root) {
        this.root = root;
        this.leaves = root.getLeaves();
    }

    public void incomingNewMove(int move) {
        Node[] children = this.root.getChildren();
        for (Node child : children) {
            if (child.getMove() == move) {
                this.setRoot(child);
                return;
            }
        }
    }

    //public int getBestMove() {

//    }

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

        minimax(root, 0);
        moveNumber++;
        return calculatedMove;
    }

    private void tempBaseCases() {

              // Base cases ----------------------------

              // If depth threshold has been reached,
              // stop and return move
              //if (depth > MAX_DEPTH) {
              //    System.out.println("Maximum depth reached! " + depth + MAX_DEPTH + " Heat = " + heat);
              //    // return heat;
              //}
              // Time up
              //if (getTimeLeft()<=0) {
              //    System.out.println("Time's up!");
                  // return heat;
              //}

              // Game finished - has anyone won?
              //if (isGameFinished(boardAfterMove)) {
                  // TODO - some action to perform like:
                  // model.getPieces(mySide)
              //}

              // End Base cases -----------------------

    }

    /*
     * Recursive heat-finding AI with base cases:
     * Depth of moves reached
     * Time limit reached
     * Game finished
     * @return heat The heat of a move
     */
    private void minimax(Node parent, int depth) {
        // Recursive MiniMax
        int newSide = flipSide(parent.getSide());
        HashSet validMoves = model.getValidMoves(newSide, parent.getBoard());
        Iterator it = validMoves.iterator();

        System.out.println("Calculating depth moves, depth = " + depth + " Heat = " + parent.getHeat());
        leaves.remove(parent);
        while (it.hasNext()) {
            int move2 = (int) it.next();

            int[][] newBoard = model.afterMove(move2, newSide, parent.getBoard());

            int heat;
            if (newSide == currentSide) {
                heat = parent.getHeat() + heatMap.getHeat(move2);
            } else {
                // Subtract the heat if the heat is meant for your opponent.
                heat = parent.getHeat() - heatMap.getHeat(move2);
            }

            Node child = new Node(newBoard, newSide, move2, heat);
            parent.add(child);
            leaves.add(child);
        }
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
