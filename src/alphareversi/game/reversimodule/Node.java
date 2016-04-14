package alphareversi.game.reversimodule;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {
    private Board board;
    private int side;
    private int heat;
    private int move;
    private boolean endState = false;
    private int type = 0;
    private int winHeat = 0;

    /**
     * Create a new Node.
     *
     * @param board The current board state.
     * @param side  The player who did the most recent move.
     * @param move  The most recent move.
     * @param heat  The heat score of this state. A higher score means this
     *              state is more desirable.
     */
    public Node(Board board, int side, int move, int heat) {
        super();
        this.board = board;
        this.side = side;
        this.move = move;
        this.heat = heat;
    }

    /**
     * Bubble up a heat value.
     *
     * @param heat Heat value.
     */
    public void updateHeat(int heat) {
        this.heat += heat;
        if (!this.isRoot()) {
            ((Node) this.getParent()).updateHeat(heat);
        }
    }

    /**
     * Retrieve all immediate children of this node.
     *
     * @return This Node's children.
     */
    public Node[] getChildren() {
        int count = this.getChildCount();
        Node[] children = new Node[count];

        Enumeration<Node> walkChildren = this.children();
        int idx = 0;
        while (walkChildren.hasMoreElements()) {
            children[idx] = walkChildren.nextElement();
            idx++;
        }

        return children;
    }

    /**
     * Retrieve all the leaves of the tree with this Node as the root.
     *
     * @return The leaves of this tree.
     */
    public List<Node> getLeaves() {
        Enumeration<Node> children = this.children();
        List<Node> leaves = new LinkedList<>();
        while (children.hasMoreElements()) {
            Node child = children.nextElement();
            if (child.isLeaf()) {
                leaves.add(child);
            } else {
                leaves.addAll(child.getLeaves());
            }
        }
        if (leaves.size() == 0) {
            leaves.add(this);
        }
        return leaves;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getMove() {
        return this.move;
    }

    public int getSide() {
        return this.side;
    }

    public int getHeat() {
        return this.heat;
    }

    /*
     * Sets the endstate variable and the type of the endstate to
     * 1: white wins, 2: black wins, 3: draw
     *
     * @param type The type of the endstate, white wins, black wins or draw
     */
    public void markEndState(int type) {
        this.endState = true;
        this.type = type;
    }

    /*
     * Returns true if this board is an endstate
     */
    public boolean isEndState() {
        return this.endState;
    }

    /*
     * Returns the type of the endstate
     * 1: white wins, 2: black wins, 3: draw
     */
    public int getEndType() {
        return this.type;
    }

    /**
     * Children of this node can add 1 winHeat if their endstate is a win.
     *
     * @param winHeat The win state value.
     */
    public void addWinHeat(int winHeat) {
        if (this.isRoot()) {
            return;
        }
        if (this.isEndState()) {
            switch (this.type) {
                case 1:
                    this.winHeat += 2;
                    break;
                case 2:
                    this.winHeat -= 2;
                    break;
                case 3:
                    this.winHeat--;
                    break;
                default:
                    break;
            }
        }
        this.winHeat += winHeat;
        ((Node) this.getParent()).addWinHeat(this.winHeat);
    }

    public int getWinHeat() {
        return this.winHeat;
    }

    public String toString() {
        return (this.side == Board.SELF ? "O" : "X") + " " + this.move;
    }
}
