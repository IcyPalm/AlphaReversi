package alphareversi.game.reversimodule;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {
    private int[][] board;
    private int side;
    private int heat;
    private int move;

    /**
     * Create a new Node.
     *
     * @param board The current board state.
     * @param side  The player who did the most recent move.
     * @param move  The most recent move.
     * @param heat  The heat score of this state. A higher score means this
     *              state is more desirable.
     */
    public Node(int[][] board, int side, int move, int heat) {
        super();
        this.board = board;
        this.side = side;
        this.move = move;
        this.heat = heat;
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

    public int[][] getBoard() {
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
}
