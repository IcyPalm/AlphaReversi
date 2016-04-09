package alphareversi.game.reversimodule;

import javax.swing.tree.DefaultMutableTreeNode;

public class Rnode extends DefaultMutableTreeNode {

    int[][] board;
    
    public Rnode(int[][] board) {
    this.board = board;
    }
    
    public int[][] getBoard() {
        return this.board;
    }
}
