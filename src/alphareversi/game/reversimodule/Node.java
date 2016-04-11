package alphareversi.game.reversimodule;

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
