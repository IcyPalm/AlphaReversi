package alphareversi.game.reversimodule;

import java.util.ArrayList;
import java.util.Collection;

public class Board {
    final public static int EMPTY = 0;
    final public static int SELF = 1;
    final public static int OPPONENT = 2;

    final private static int SIZE = 8;

    private int[][] board;

    final private static Direction[] DIRECTIONS = new Direction[] {
        new Direction(0, -1),
        new Direction(-1, -1),
        new Direction(-1, 0),
        new Direction(-1, 1),
        new Direction(0,  1),
        new Direction(1,  1),
        new Direction(1,  0),
        new Direction(1, -1),
    };

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
        Collection<Integer> available = new ArrayList<>(60);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (this.board[row][col] == EMPTY) {
                    if (this.canAttack(player, row, col)) {
                        available.add(row * 8 + col);
                    }
                }
            }
        }
        return available;
    }

    /**
     *
     */
    public boolean isValidMove(int player, int move) {
        return this.canAttack(player, move / 8, move % 8);
    }

    private boolean canAttack(int player, int row, int col) {
        for (Direction d : DIRECTIONS) {
            if (this.attackablePiecesInDirection(player, row, col, d) > 0) {
                return true;
            }
        }
        return false;
    }

    private int attackablePiecesInDirection(int player, int row, int col, Direction d) {
        int opponent = player == SELF ? OPPONENT : SELF;
        int hitting = 0;
        row += d.y;
        col += d.x;
        while (row >= 0 && row < 8 &&
               col >= 0 && col < 8) {
            if (this.board[row][col] == opponent) {
                hitting++;
            } else if (this.board[row][col] == player) {
                return hitting;
            } else {
                // Empty spaces can not be attacked
                return 0;
            }
            row += d.y;
            col += d.x;
        }
        return 0;
    }

    /**
     *
     */
    public int getScore(int player) {
        int score = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (this.board[row][col] == player) {
                    score++;
                }
            }
        }
        return score;
    }

    public void place(int player, int position) throws InvalidMoveException {
        int row = position / 8;
        int col = position % 8;
        this.place(player, row, col);
    }

    public void place(int player, int row, int col) throws InvalidMoveException {
        int totalFlipped = 0;
        for (Direction d : DIRECTIONS) {
            int flip = this.attackablePiecesInDirection(player, row, col, d);

            // No attackable pieces found.
            if (flip == 0) {
                continue;
            }

            // Move `flip` places into the Direction, replacing all pieces with
            // ours.
            int flipRow = row + d.y;
            int flipCol = col + d.x;
            for (int i = 0; i < flip; i++) {
                this.board[flipRow][flipCol] = player;
                flipRow += d.y;
                flipCol += d.x;
            }

            totalFlipped += flip;
        }

        if (totalFlipped == 0) {
            throw new InvalidMoveException("Invalid move: Move does not flip any pieces");
        }

        this.board[row][col] = player;
    }

    private static class Direction {
        public int x;
        public int y;

        public Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
