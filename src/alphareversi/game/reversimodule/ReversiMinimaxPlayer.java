package alphareversi.game.reversimodule;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.tree.TreeNode;

/*
 * MiniMax AI test
 * BASIC IDEA, uses only heat from the heatMap
 * @author Maarten le Clercq
 */
public class ReversiMinimaxPlayer implements Player {
    public static final int MAX_DEPTH = 2;
    public static final int THINK_TIME = 600;

    private List<ActionListener> actionListeners = new LinkedList<>();

    private ReversiModel model;
    private ReversiHeatmap heatMap = new ReversiHeatmap();

    private int currentSide = 0;

    private Node root;

    private ReentrantLock lock;
    private Minimax minimaxer;
    private long starttime = 0;
    private int moveNumber = 0;

    /**
     * Create a Minimax AI player.
     *
     * @param model The Model (board etc.) to play with.
     */
    public ReversiMinimaxPlayer(ReversiModel model) {
        this.model = model;
        int side;
        if (model.amIOnTurn()) {
            this.currentSide = 1;
        } else {
            this.currentSide = 2;
        }
        this.root = new Node(model.getBoard(), this.currentSide, 0, 0);

        this.startMinimax();
    }

    /**
     *
     */
    public void startTurn() {
        this.starttime = System.currentTimeMillis();
        this.startTimerThread();
        this.incomingNewMove(this.model.getMostRecentMove());
    }

    /**
     * Add an action listener.
     */
    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }

    /**
     * Remove an action listener.
     */
    public void removeActionListener(ActionListener listener) {
        this.actionListeners.remove(listener);
    }

    private void notifyActionListeners(int move) {
        for (ActionListener listener : this.actionListeners) {
            listener.actionPerformed(
                new ActionEvent(this, move, "")
            );
        }
    }

    /**
     *
     */
    private void startTimerThread() {
        new Thread(() -> {
            try {
                Thread.sleep(THINK_TIME - 200);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } finally {
                // Attempt to play the best move, even if the thread was
                // interrupted.
                this.notifyActionListeners(this.getBestMove());
            }
        }).start();
    }

    /**
     * Process an incoming move, by the opponent or by us. Discards the parts of
     * the move tree that are no longer useful.
     *
     * @param move The new move.
     */
    public void incomingNewMove(int move) {
        Node[] children = this.root.getChildren();
        for (Node child : children) {
            if (child.getMove() == move) {
                this.setRoot(child);
                return;
            }
        }
    }

    /**
     * Get the best available move that has been computed so far.
     *
     * @return The best move according to the current move tree.
     */
    public int getBestMove() {
        this.lock.lock();
        List<Node> leaves = this.minimaxer.getLeaves();
        Node best = leaves.get(0);
        for (Node leaf : leaves) {
            if (leaf.getHeat() > best.getHeat()) {
                best = leaf;
            }
        }
        this.lock.unlock();

        best = getNextMove(best);
        return best == null ? -1 : best.getMove();
    }

    /**
     * Find the next move to get to the given end state.
     *
     * @param endState
     * @return The next move.
     */
    private Node getNextMove(Node endState) {
        Node next = (Node) endState.getParent();
        Node parent = (Node) next.getParent();
        while (!parent.isRoot()) {
            next = parent;
            parent = (Node) parent.getParent();
        }
        return next;
    }

    /**
     * Check whether a player can place any pieces on a board.
     *
     * @param side  The player to check
     * @param board The board state.
     * @return Whether the player has valid moves on the given board.
     */
    private boolean canPlayerMove(int side, int[][] board) {
        return this.model.getValidMoves(side, board).size() > 0;
    }

    /**
     * Check whether the game is over on a board.
     *
     * @param board The board state.
     * @return True if no more moves are possible, false otherwise.
     */
    public boolean isGameFinished(int[][] board) {
        if (!this.canPlayerMove(1, board) || !this.canPlayerMove(2, board)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The remaining time in the current turn.
     */
    private long getTimeLeft() {
        return THINK_TIME - (System.currentTimeMillis() - starttime);
    }

    /**
     * Start computing the moves tree.
     */
    public void startMinimax() {
        this.lock = new ReentrantLock(true);
        this.minimaxer = new Minimax(this.lock, this.root);
        new Thread(this.minimaxer).start();
    }

    /**
     * Set the current move/board state.
     */
    public void setRoot(Node root) {
        this.lock.lock();

        System.out.println("AI: Setting root");

        this.root = root;
        // Discard the rest of the tree
        root.removeFromParent();

        this.minimaxer.setRoot(root);

        this.lock.unlock();
    }

    private class Minimax implements Runnable {
        private boolean running = true;

        private ReentrantLock lock;
        private List<Node> leaves;

        /**
         * Create a new Minimax tree computation thread.
         *
         * @param lock Lock used to lock on the current computed leaves.
         * @param root Initial state node.
         */
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

        /**
         * Configure the thread to work out the tree of a different Node.
         * Consumers should wait for this thread's lock first to avoid
         * concurrency fun.
         *
         * @param root The new root state.
         */
        public void setRoot(Node root) {
            this.leaves = root.getLeaves();
        }

        /**
         * Retrieve the current leaf states. Consumers should wait for this
         * thread's lock first to avoid concurrency fun.
         *
         * @return The current leaf states.
         */
        public List<Node> getLeaves() {
            return this.leaves;
        }

        /**
         * Calculate the moves tree.
         */
        public void run() {
            while (this.running) {
                System.out.println("AI: Deepening - " + leaves.size() + " leaves");
                LinkedList<Node> temp = new LinkedList<Node>(leaves);
                for (Node leaf : temp) {
                    // The lock is INSIDE the loop so the thread can be interrupted in
                    // an interval equal to the step() burst time
                    lock.lock();
                    this.step(leaf);
                    lock.unlock();
                }
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
            int newSide = this.flipSide(parent.getSide());

            HashSet validMoves = model.getValidMoves(parent.getSide(), parent.getBoard());
            Iterator it = validMoves.iterator();

            leaves.remove(parent);
            while (it.hasNext()) {
                int move2 = (int) it.next();

                int[][] newBoard = model.afterMove(move2, parent.getSide(), parent.getBoard());

                int heat;
                if (parent.getSide() == model.getMySide()) {
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

        /*
         * Flips the side the the next player
         *
         * @return side The side of the next player
         */
        private int flipSide(int side) {
            if (side == 1) {
                return 2;
            } else {
                return 1;
            }
        }
    }
}
