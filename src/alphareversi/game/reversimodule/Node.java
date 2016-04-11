package alphareversi.game.reversimodule;

import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {

    //Local variables stored in this node
    int[][] board;
    int side;
    int heat;
    int move;

    public Node(int[][] board, int side, int move, int heat) {
        super();
        this.board = board;
        this.side = side;
        this.move = move;
        this.heat = heat;
    }

    public Node[] getChildren() {
        int count = this.getChildCount();
        Node[] children = new Node[count];
        Enumeration<Node> e = this.children();
        int i = 0;
        while (e.hasMoreElements()) {
            children[i] = e.nextElement();
            i++;
        }
        return children;
    }

    public Collection<Node> getLeaves() {
        Enumeration<Node> e = this.children();
        LinkedList<Node> leaves = new LinkedList<>();
        while (e.hasMoreElements()) {
            Node child = e.nextElement();
            if (child.isLeaf()) {
                leaves.add(child);
            } else {
                leaves.addAll(child.getLeaves());
            }
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
