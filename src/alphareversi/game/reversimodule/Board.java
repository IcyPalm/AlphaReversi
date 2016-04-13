package alphareversi.game.reversimodule;

import java.util.ArrayList;
import java.util.Collection;

public class Board {
    /**
     * Piece/Player types.
     */
    public static final int EMPTY = 0;
    public static final int SELF = 1;
    public static final int OPPONENT = 2;

    public static final int DRAW = 3;

    /**
     * The size of the board (amount of rows = amount of columns).
     */
    private static final int SIZE = 8;

    /**
     * All directions in which pieces might be vulnerable.
     */
    private static final Direction[] DIRECTIONS = new Direction[] {
        new Direction(0, -1),
        new Direction(-1, -1),
        new Direction(-1, 0),
        new Direction(-1, 1),
        new Direction(0,  1),
        new Direction(1,  1),
        new Direction(1,  0),
        new Direction(1, -1),
    };

    /**
     * The board storage.
     */
    private int[][] board;

    /**
     * Create a default board.
     */
    public Board() {
        this(SELF);
    }

    /**
     * Create a board from the viewpoint of the given player.
     *
     * @param self POV.
     */
    public Board(int self) {
        int opponent = self == SELF ? OPPONENT : SELF;
        this.board = new int[][] {
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, opponent, self, 0, 0, 0 },
            new int[] { 0, 0, 0, self, opponent, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
            new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
        };
    }

    /**
     * Create a board with the given pieces.
     */
    public Board(int[][] board) {
        this.board = board;
    }

    /**
     * Retrieve the raw board layout.
     *
     * @return Two-dimensional array containing pieces, indexed first by row and
     *         second by column: `board[row][column]`.
     */
    public int[][] get() {
        return this.board;
    }

    /**
     * Find available moves for a player. A move is available when it's in an
     * empty spot, and if it will attack at least one opposing piece.
     *
     * @param player Player to check for.
     * @return A list of possible moves.
     */
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
     * Check if a certain move is possible on the current board.
     *
     * @param player Mover.
     * @param move   Position to attempt to place a piece.
     * @return True if the piece can be placed in the given position, False otherwise.
     */
    public boolean isValidMove(int player, int move) {
        return this.board[move / 8][move % 8] == EMPTY &&
                this.canAttack(player, move / 8, move % 8);
    }

    /**
     * Checks if a piece placed at a given position attacks at least one
     * opposing piece.
     *
     * @param player Piece owner.
     * @param row    Row position.
     * @param col    Column position.
     * @return True if a piece placed in the given position would attack other
     *         pieces, false otherwise.
     */
    private boolean canAttack(int player, int row, int col) {
        for (Direction d : DIRECTIONS) {
            if (this.attackablePiecesInDirection(player, row, col, d) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether a piece placed at a given position would attack at least
     * one opposing piece in a single direction.
     */
    private int attackablePiecesInDirection(int player, int row, int col, Direction direction) {
        int opponent = player == SELF ? OPPONENT : SELF;
        int hitting = 0;
        row += direction.col;
        col += direction.row;
        while (row >= 0 && row < 8
                && col >= 0 && col < 8) {
            if (this.board[row][col] == opponent) {
                hitting++;
            } else if (this.board[row][col] == player) {
                return hitting;
            } else {
                // Empty spaces can not be attacked
                return 0;
            }
            row += direction.col;
            col += direction.row;
        }
        return 0;
    }

    /**
     * Get the current amount of pieces for a player.
     *
     * @param player The player.
     * @return The amount of pieces the player owns.
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

    /**
     * gets the win state of the board.
     * @return int state
     */
    public int getWinState() {
        int selfScore = this.getScore(Board.SELF);
        int opponentScore = this.getScore(Board.OPPONENT);
        if (selfScore > opponentScore) {
            return SELF;
        } else if (opponentScore > selfScore) {
            return OPPONENT;
        }
        return DRAW; //draw
    }

    /**
     * Place a piece on the board.
     *
     * @param player   Piece owner.
     * @param position Position to place the piece at.
     */
    public void place(int player, int position) throws InvalidMoveException {
        int row = position / 8;
        int col = position % 8;
        this.place(player, row, col);
    }

    /**
     * Internal piece-placing with row & column goodness.
     */
    private void place(int player, int row, int col) throws InvalidMoveException {
        int totalFlipped = 0;
        for (Direction d : DIRECTIONS) {
            int flip = this.attackablePiecesInDirection(player, row, col, d);

            // No attackable pieces found.
            if (flip == 0) {
                continue;
            }

            // Move `flip` places into the Direction, replacing all pieces with
            // ours.
            int flipRow = row + d.col;
            int flipCol = col + d.row;
            for (int i = 0; i < flip; i++) {
                this.board[flipRow][flipCol] = player;
                flipRow += d.col;
                flipCol += d.row;
            }

            totalFlipped += flip;
        }

        if (totalFlipped == 0) {
            throw new InvalidMoveException("Invalid move: Move does not flip any pieces");
        }

        this.board[row][col] = player;
    }

    /**
     * Create a full clone of this board. Altering either one of the current or
     * the cloned board will not affect the other board.
     *
     * @return A clone of the current board.
     */
    public Board clone() {
        int[][] clone = new int[SIZE][];
        for (int row = 0; row < SIZE; row++) {
            clone[row] = this.board[row].clone();
        }
        return new Board(clone);
    }

    /**
     * Get a fairly readable version of the current board state as text.
     *
     * @return A string with 8 lines.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(SIZE * SIZE + SIZE);
        for (int row = 0; row < SIZE; row++) {
            // Only add newlines in between lines, not at the end, for more
            // intuitive `.println()`ing.
            if (row > 0) {
                builder.append("\n");
            }

            for (int col = 0; col < SIZE; col++) {
                if (this.board[row][col] == SELF) {
                    builder.append("X");
                } else if (this.board[row][col] == OPPONENT) {
                    builder.append("O");
                } else {
                    builder.append("·");
                }
            }
        }
        return builder.toString();
    }

    /**
     * Internal class for representing directions that pieces can threaten other
     * pieces in.
     */
    private static class Direction {
        public int row;
        public int col;

        public Direction(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
