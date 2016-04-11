package alphareversi.game.reversimodule;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {

    //Local variables stored in this node
    int[][] board;
    int currentSide;
    int heat;
    
    public Node(int[][] board, int side, int heat) {
        this.board = board;
        this.currentSide = side;
        this.heat = heat;
    }
    
    public int[][] getBoard() {
        return this.board;
    }
}
