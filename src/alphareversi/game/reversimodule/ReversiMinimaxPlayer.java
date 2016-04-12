package alphareversi.game.reversimodule;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
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
        this.currentSide = this.model.getPlayerOnTurn();
        this.root = new Node(model.getBoardInstance(), this.currentSide, 0, 0);

        this.startMinimax();
    }

    /**
     *
     */
    public void startTurn() {
        this.starttime = System.currentTimeMillis();

        int move = this.model.getMostRecentMove();
        if (move >= 0) {
            this.incomingNewMove(move);
        }

        this.startTimerThread();
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
        System.out.println("[Reversi/AI] Starting Turn");
        new Thread(() -> {
            try {
                Thread.sleep(THINK_TIME - 200);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            // Attempt to play the best move, even if the thread was
            // interrupted.
            int bestMove = this.getBestMove();
            this.notifyActionListeners(bestMove);
            // Also parse this move locally.
            this.incomingNewMove(bestMove);
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

        System.out.print("[Reversi/AI] Incoming move: " + move + ", previous = " + this.root.getMove());
        System.out.print(", possible = [");
        for (Node child : children) {
            System.out.print(child.getMove() + ", ");
        }
        System.out.println("]");

        for (Node child : children) {
            if (child.getMove() == move) {
                this.setRoot(child);
                return;
            }
        }

        System.out.println("[Reversi/AI] Did not predict move");

        this.setRoot(new Node(
            this.model.getBoardInstance(),
            this.model.getPlayerOnTurn() == 1 ? 2 : 1,
            0,
            0
        ));
    }

    /**
     * Get the best available move that has been computed so far.
     *
     * @return The best move according to the current move tree.
     */
    public int getBestMove() {
        this.lock.lock();
        System.out.println("[Reversi/AI] Retrieving best move");
        List<Node> leaves = this.minimaxer.getLeaves();
        Node best = leaves.get(0);
        for (Node leaf : leaves) {
            if (leaf.getHeat() > best.getHeat()) {
                best = leaf;
            }
        }

        System.out.println("[Reversi/AI] Desired state:");
        System.out.println("--------");
        System.out.println(best.getBoard() + "");
        System.out.println("--------");

        this.lock.unlock();

        best = this.getNextMove(best);
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

        System.out.println("[Reversi/AI] Setting root");

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
            while (this.running && !model.gameOver()) {
                LinkedList<Node> temp = new LinkedList<Node>(leaves);
                boolean didProcessLeaf = false;
                for (Node leaf : temp) {
                    if (leaf.isEndState()) {
                        continue;
                    }

                    didProcessLeaf = true;

                    // The lock is INSIDE the loop so the thread can be interrupted in
                    // an interval equal to the step() burst time
                    lock.lock();
                    this.step(leaf);
                    lock.unlock();
                }

                if (!didProcessLeaf) {
                    this.running = false;
                }

                if (this.leaves.size() > 10000) {
                    lock.lock();
                    this.prune();
                    lock.unlock();
                }
            }
        }

        /**
         * Recursive heat-finding AI with base cases:
         * Depth of moves reached
         * Time limit reached
         * Game finished
         * @return heat The heat of a move
         */
        private void step(Node parent) {
            int newSide = this.flipSide(parent.getSide());

            Collection<Integer> validMoves = parent.getBoard().getAvailableMoves(parent.getSide());

            if (validMoves.size() == 0) {
                parent.markEndState(parent.getBoard().getWinState());
                return;
            }

            leaves.remove(parent);
            Iterator<Integer> it = validMoves.iterator();
            while (it.hasNext()) {
                int move = (int) it.next();

                Board newBoard = parent.getBoard().clone();
                try {
                    newBoard.place(parent.getSide(), move);
                } catch (InvalidMoveException ime) {
                    // Ignore this!
                    // Because these are all valid…
                    // TODO add `placeUnsafe()` method that does not throw.
                    System.err.println("[Reversi/AI] Attempted invalid move: " + move);
                    continue;
                }

                int heat;
                if (parent.getSide() == model.getMySide()) {
                    heat = parent.getHeat() + heatMap.getHeat(move);
                } else {
                    // Subtract the heat if the heat is meant for your opponent.
                    heat = parent.getHeat() - heatMap.getHeat(move);
                }

                Node child = new Node(newBoard, newSide, move, heat);
                parent.add(child);
                leaves.add(child);
            }
        }

        private void prune() {
            final int MAX_LEAVES = 10000;
            TreeSet<Node> leaves = new TreeSet<>((a, b) -> {
              return a.getHeat() > b.getHeat() ? 1 : -1;
            });

            for (Node leaf : this.leaves) {
                leaves.add(leaf);
            }

            Iterator<Node> it = leaves.descendingIterator();
            List<Node> newLeaves = new LinkedList<>();
            int len = 0;
            while (it.hasNext() && len < MAX_LEAVES) {
                newLeaves.add(it.next());
                len++;
            }

            this.leaves = newLeaves;
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
