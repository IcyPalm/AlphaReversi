package alphareversi.game.reversimodule;

import java.util.ArrayList;
import java.util.Collection;

public class Board {
    final public static int EMPTY = 0;
    final public static int SELF = 1;
    final public static int OPPONENT = 2;

    final private static int SIZE = 8;

    private int[][] board;

    public Board() {
        this(new int[][] {
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, OPPONENT, SELF, 0, 0, 0 },
            new int[] { 0, 0, 0, SELF, OPPONENT, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
        });
    }
    public Board(int[][] board) {
        this.board = board;
    }

    public int[][] get() {
        return this.board;
    }

    public Collection<Integer> getAvailableMoves(int player) {
        return new ArrayList<>();
    }

    public int getScore(int player) {
        return 0;
    }

    public void place(int player, int position) {
        ;
    }
}
