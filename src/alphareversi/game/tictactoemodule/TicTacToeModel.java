package alphareversi.game.tictactoemodule;

/**
 * Created by Daniël on 25-Mar-16.
 */
public class TicTacToeModel {
    public static final int self = 0;
    public static final int opponent = 1;
    public static final int EMPTY = 2;
    public static final int SELF_WIN = 0;
    public static final int DRAW = 1;
    public static final int UNCLEAR = 2;
    public static final int OPPONENT_WIN = 3;
    private int[][] board = new int[3][3];
    private int position = UNCLEAR;

    private int side;

    private char selfChar;
    private char opponentChar;

    private TicTacToeViewController viewController;

    /**
     * Constructor for TicTacToeModel. creates a view.
     */
    public TicTacToeModel() {
        //view = new TicTacToeView(self, opponent, this);
        clearBoard();
        initSide();
    }

    /**
     * Plays a move on the board. Switches the turn to the opposite player. Calls the
     * TicTacToeView.
     */
    public void playMove(int move) {
        board[move / 3][move % 3] = this.side;
        if (side == self) {
            this.side = opponent;
        } else {
            this.side = self;
        }

        if (viewController != null) {
            viewController.updateBoard(board);
        }
    }

    /**
     * Clears the board.
     */
    private void clearBoard() {
        // over elk vakje heen gaan en leegmaken.
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y] = EMPTY;
            }
        }
    }

    /**
     * Checks if a board is full.
     */
    private boolean boardIsFull() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a move is good to set.
     */
    public boolean moveOk(int move) {
        return (move >= 0 && move <= 8 && board[move / 3][move % 3] == EMPTY);
    }

    /**
     * Checks if a side has won.
     *
     * @param side The side to check.
     */
    public boolean isAWin(int side) {
        // verticaal
        for (int y = 0; y < 3; y++) {
            if (board[0][y] == board[1][y] && board[1][y] == board[2][y] && board[0][y] == side) {
                return true;
            }
        }
        // horizontaal
        for (int x = 0; x < 3; x++) {
            if (board[x][0] == board[x][1] && board[x][1] == board[x][2] && board[x][0] == side) {
                return true;
            }
        }
        // diagonalen
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] == side) {
            return true;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] == side) {
            return true;
        }
        return false;
    }

    /**
     * Compute static value of current position. (win, draw, etc.).
     */
    public int positionValue() {
        if (isAWin(opponent)) {
            return OPPONENT_WIN;
        }
        if (isAWin(self)) {
            return SELF_WIN;
        }
        if (boardIsFull()) {
            return DRAW;
        }
        return UNCLEAR;
    }

    /**
     * Returns the opponent.
     */
    public int getOpponentBySide(int side) {
        if (side == opponent) {
            return self;
        } else {
            return opponent;
        }
    }

    public int getOpponent() {
        return opponent;
    }

    public boolean gameOver() {
        this.position = positionValue();
        return this.position != UNCLEAR;
    }

    private void initSide() {
        if (this.side == self) {
            selfChar = 'X';
            opponentChar = 'O';
        } else {
            selfChar = 'O';
            opponentChar = 'X';
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean squareIsEmpty(int row, int column) {
        return board[row][column] == EMPTY;
    }

    public void setOpponentPlays() {
        this.side = opponent;
        initSide();
    }

    public char getOpponentChar() {
        return opponentChar;
    }

    public char getSelfChar() {
        return selfChar;
    }

    public void setSelfPlays() {
        this.side = self;
        initSide();
    }

    public void setViewController(TicTacToeViewController viewController) {
        this.viewController = viewController;
    }

    public int getSelf() {
        return self;
    }

    public int getEmpty() {
        return EMPTY;
    }

    public int getSide() {
        return side;
    }
}
