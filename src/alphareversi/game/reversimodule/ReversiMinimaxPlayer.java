package alphareversi.game.reversimodule;

import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.tree.TreeNode;

/*
 * MiniMax AI test
 * BASIC IDEA, uses only heat from the heatMap
 * @author Maarten le Clercq
 */
public class ReversiMinimaxPlayer {
    public static final int MAX_DEPTH = 2;
    public static final int THINK_TIME = 2000;

    private ReversiModel model;
    private ReversiHeatmap heatMap = new ReversiHeatmap();

    private int currentSide = 0;
    private int[][] currentBoard = new int[8][8];

    private Node root;
    private List<Node> leaves = new LinkedList<>();

    private ReentrantLock lock;
    private Minimax minimaxer;
    private long starttime = 0;
    private int moveNumber = 0;

    public ReversiMinimaxPlayer(ReversiModel model) {
        this.model = model;
        int side;
        if(model.amIOnTurn()) {
            side = 1;
        } else {
            side = 2;
        }
        this.root = new Node(currentBoard, side, 0, 0);

        this.startMinimax();
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

    public int getBestMove() {
        List<Node> leaves = this.getKnownLeaves();
        Node best = leaves.get(0);
        for (Node leaf : leaves) {
            if (leaf.getHeat() > best.getHeat()) {
                best = leaf;
            }
        }
        return best.getMove();
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

    public boolean isGameFinished(int[][] board) {
        if ((model.getValidMoves(1, currentBoard).size() == 0) && (model.getValidMoves(2, currentBoard).size() == 0)) {
            return true;
        } else {
            return false;
        }
    }

    private long getTimeLeft() {
        return THINK_TIME - (System.currentTimeMillis() - starttime);
    }

    public void startMinimax() {
        this.lock = new ReentrantLock();
        this.minimaxer = new Minimax(this.lock, this.root);
        new Thread(this.minimaxer).start();
    }

    public List<Node> getKnownLeaves() {
        this.lock.lock();
        List<Node> leaves = this.minimaxer.getLeaves();
        this.lock.unlock();
        return leaves;
    }

    private class Minimax implements Runnable {
        private boolean running = true;

        private ReentrantLock lock;
        private List<Node> leaves;

        public Minimax(ReentrantLock lock, Node root) {
            this.lock = lock;
            this.setRoot(root);
        }

        /**
         * Stop computing the tree entirely.
         */
        public void stop() {
            this.running = false;
        }

        public void setRoot(Node root) {
            this.leaves = root.getLeaves();
        }

        public List<Node> getLeaves() {
            return this.leaves;
        }

        public void run() {
            while (this.running) {
                lock.lock();
                for (Node leaf : this.leaves) {
                    this.step(leaf);
                }
                // Give the parent thread a chance to access/mutate leaves.
                lock.unlock();
            }
        }

        /*
         * Recursive heat-finding AI with base cases:
         * Depth of moves reached
         * Time limit reached
         * Game finished
         * @return heat The heat of a move
         */
        private void step(Node parent) {
            // Recursive MiniMax
            int newSide = this.flipSide(parent.getSide());
            HashSet validMoves = model.getValidMoves(newSide, parent.getBoard());
            Iterator it = validMoves.iterator();

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

        private int flipSide(int side) {
            if (side == 1) {
                return 2;
            } else {
                return 1;
            }
        }
    }
}
