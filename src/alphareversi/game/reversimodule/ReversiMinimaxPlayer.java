package alphareversi.game.reversimodule;

import alphareversi.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static final int MAX_LEAVES = 3000;

    private Logger logger = new Logger("Reversi/AI");

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
     * start turn.
     */
    public void startTurn() {
        this.starttime = System.currentTimeMillis();

        int move = this.model.getMostRecentMove();
        if (move >= 0) {
            this.incomingNewMove(move);
        }

        String text = "Available moves: [";
        for (Node child : this.root.getChildren()) {
            text += child.getMove() + ", ";
        }
        this.logger.log(text + "]");

        if (this.minimaxer.getDoneCalculating()) {
            this.playMove();
        }
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
     * start the timer for the turn.
     */
    private void startTimerThread() {
        this.logger.log("Starting Turn");
        Thread timer = new Thread(() -> {
            try {
                this.logger.log("Sleeping...");
                Thread.sleep(THINK_TIME - 200);
                this.logger.log("Awakeness! ✨");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            // Attempt to play the best move, even if the thread was
            // interrupted.
            // this.playMove();
        });
        timer.setPriority(Thread.MAX_PRIORITY);
        timer.start();
    }

    private void playMove() {
        int bestMove = this.getBestMove();
        // Also parse this move locally.
        this.incomingNewMove(bestMove);
        this.notifyActionListeners(bestMove);
    }

    /**
     * Process an incoming move, by the opponent or by us. Discards the parts of the move tree that
     * are no longer useful.
     *
     * @param move The new move.
     */
    public synchronized void incomingNewMove(int move) {
        Node[] children = this.root.getChildren();

        String text = "Incoming move: " + move
                + ", previous = " + this.root.getMove()
                + ", possible = [";
        for (Node child : children) {
            text += child.getMove() + ", ";
        }
        this.logger.log(text + "]");

        for (Node child : children) {
            if (child.getMove() == move) {
                String logGrandChildren = "Next moves: [";
                for (Node grandChild : child.getChildren()) {
                    logGrandChildren += grandChild.getMove() + ", ";
                }
                this.logger.log(logGrandChildren + "]");
                this.setRoot(child);
                return;
            }
        }

        this.logger.log("Did not predict move");

        this.setRoot(new Node(
                this.model.getBoardInstance(),
                this.model.getPlayerOnTurn(),
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
        this.logger.log("getBestMove()");

        // BASE CASE
        if (minimaxer.getDoneCalculating()) {
            this.logger.log("Praying for winssss");
            Node newRoot = minimaxer.getHighestHeatChild(this.root);
            return newRoot.getMove();
        } else {
            this.lock.lock();

            this.logger.log("Retrieving best move");
            List<Node> leaves = this.minimaxer.getLeaves();

            String text = "Available moves: [";
            for (Node child : this.root.getChildren()) {
                text += child.getMove() + ", ";
            }
            this.logger.log("Retrieving best move out of " + leaves.size());
            this.logger.log(text + "]");

            Node best = leaves.get(0);
            for (Node leaf : leaves) {
                if (leaf.getHeat() > best.getHeat()) {
                    best = leaf;
                }
            }

            if (!this.root.isNodeDescendant(best)) {
                this.logger.err("ATTEMPTING TO PLAY ORPHAN NODE");
            }

            this.logger.log("Current state:");
            this.logger.log("--------");
            this.logger.log(this.model.getBoardInstance() + "");
            this.logger.log("--------");

            this.logger.log("Desired state:");
            this.logger.log("--------");
            this.logger.log(best.getBoard() + "");
            this.logger.log("--------");

            this.logger.log("Required path:");
            TreeNode[] path = best.getPath();
            String pathText = "";
            for (TreeNode node : path) {
                pathText += " » " + node;
            }
            this.logger.log(pathText);

            best = this.getNextMove(best);

            this.lock.unlock();

            return best == null ? -1 : best.getMove();
        }
    }

    /**
     * Find the next move to get to the given end state.
     *
     * @param next End state node.
     * @return The next move.
     */
    private Node getNextMove(Node next) {
        while (!this.root.isNodeChild(next)) {
            next = (Node) next.getParent();
        }
        return next;
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
        new Thread(this.minimaxer, "Minimaxer").start();
    }

    /**
     * Set the current move/board state.
     */
    public void setRoot(Node root) {
        this.logger.log("Going to set root after " + this.lock.getQueueLength());
        if (this.lock.isLocked()) {
            this.logger.log(
                "But is locked by " +
                (this.lock.isHeldByCurrentThread() ? "Me" : "Minimaxer")
            );
        }
        this.lock.lock();

        this.logger.log("Setting root");

        this.root = root;
        // Discard the rest of the tree
        root.removeFromParent();

        this.minimaxer.setRoot(root);

        this.lock.unlock();
    }

    private class Minimax implements Runnable {
        private boolean running = true;
        private boolean leavesWereReset = false;

        private ReentrantLock lock;
        private List<Node> leaves;

        private boolean doneCalculating = false;

        /**
         * Create a new Minimax tree computation thread.
         *
         * @param lock Lock used to lock on the current computed leaves.
         * @param root Initial state node.
         */
        public Minimax(ReentrantLock lock, Node root) {
            this.lock = lock;
            this.leaves = root.getLeaves();
        }

        /**
         * Stop computing the tree entirely.
         */
        public void stop() {
            this.running = false;
        }

        /**
         * Configure the thread to work out the tree of a different Node. Consumers should wait for
         * this thread's lock first to avoid concurrency fun.
         *
         * @param root The new root state.
         */
        public void setRoot(Node root) {
            this.leaves = root.getLeaves();
            logger.log("Root updated: " + this.leaves.size() + " leaves");
            this.leavesWereReset = true;
        }

        /**
         * Retrieve the current leaf states. Consumers should wait for this thread's lock first to
         * avoid concurrency fun.
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
            int step = 0;
            while (this.running && !model.gameOver()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ie) {
                    // ehh...
                }
                if (!model.amIOnTurn()) {
                    continue;
                }

                LinkedList<Node> temp = new LinkedList<Node>(this.leaves);
                boolean didProcessLeaf = false;
                this.leavesWereReset = false;
                logger.log(
                    "startTime: " + starttime + ", " +
                    "My Turn: " + model.amIOnTurn() + ", " +
                    "Time left: " + getTimeLeft() + ", " +
                    "Leaves: " + temp.size()
                );
                for (Node leaf : temp) {
                    if (starttime != 0 && model.amIOnTurn() &&
                            getTimeLeft() > -1000 && getTimeLeft() < 500) {
                        logger.log("Playing b/c of time limit");
                        didProcessLeaf = true;
                        playMove();
                        break;
                    }

                    if (leaf.isEndState()) {
                        continue;
                    }

                    didProcessLeaf = true;

                    // The lock is INSIDE the loop so the thread can be interrupted in
                    // an interval equal to the step() burst time
                    this.lock.lock();

                    // If the leaves were changed, we need to refresh our
                    // `temp` leaves list too, or we'll be adding new leaves
                    // for parent leaves that no longer exist.
                    if (this.leavesWereReset) {
                        ReversiMinimaxPlayer.this.logger.log(
                            "Leaves were reset - Old: " + temp.size() +
                            " - New: " + this.leaves.size()
                        );
                        this.lock.unlock();
                        break;
                    }

                    this.step(leaf);
                    this.lock.unlock();
                }

                this.lock.lock();

                if (!didProcessLeaf) {
                    logger.log("No known leaves");
                    this.running = false;
                    processWinStates();
                    if (model.amIOnTurn()) {
                        playMove();
                    }
                    this.lock.unlock();
                    return;
                }

                this.prune();
                this.lock.unlock();

                // step++;
                // if (step % 2 == 0) {
                //     this.lock.lock();
                //     logger.log("Start prune");
                //     this.prune();
                //     logger.log("End prune");
                //     this.lock.unlock();
                // }
            }
        }

        /**
         * Recursive heat-finding AI with base cases:
         * Depth of moves reached Time limit reached Game finished.
         * @return heat The heat of a move
         */
        private void step(Node parent) {
            int newSide = this.flipSide(parent);

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
                    ReversiMinimaxPlayer.this.logger.err("Attempted invalid move: " + move);
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
            TreeSet<Node> leaves = new TreeSet<>((anode, bnode) -> {
                return anode.getHeat() > bnode.getHeat() ? 1 : -1;
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
        private int flipSide(Node prev) {
            int player = model.getOpponent(prev.getSide());
            if (prev.getBoard().getAvailableMoves(player).size() == 0) {
                player = model.getOpponent(player);
            }
            return player;
        }

        /*
         * Bubble up the winHeat to the root from every leaf
         */
        private void processWinStates() {
            List<Node> endLeaves = this.leaves; // root.getLeaves();
            for (Node endLeave : endLeaves) {
                endLeave.addWinHeat(0);
            }
            this.doneCalculating = true;
        }

        private boolean getDoneCalculating() {
            return this.doneCalculating;
        }

        /*
         * Finds the child of the parameter Node parent with the highest winHeat
         *
         * @return the child of the parameter Node with the highest winHeat
         */
        private Node getHighestHeatChild (Node parent) {
            Node highest = parent;
            int maxHeat = Integer.MIN_VALUE;
            Node[] children = parent.getChildren();
            for (Node child : children) {
                if (child.getWinHeat() > maxHeat) {
                    maxHeat = child.getHeat();
                    highest = child;
                }
            }
            return highest;
        }
    }
}
