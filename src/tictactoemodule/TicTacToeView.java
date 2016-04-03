package tictactoemodule;

/**
 * Created by daant on 25-Mar-16.
 */
public class TicTacToeView {
    private int[][] board;
    private int self;
    private int opponent;
    private TicTacToeModel model;
    private char selfChar;
    private char opponentChar;

    /**
     * Constructor for the view
     * should not be important for final 1.0 version
     */
    public TicTacToeView(int self, int opponent, TicTacToeModel model) {
        this.board = model.getBoard();
        this.self = self;
        this.opponent = opponent;
        this.model = model;
    }

    /**
     * Prints the current board of TicTacToe.
     */
    public void print() {
        this.selfChar = model.getSelfChar();
        this.opponentChar = model.getOpponentChar();
        String string = "";
        // for every row
        for (int x = 0; x < 3; x++) {
            string += ('\n');
            // for every col in row
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == self) {
                    string += selfChar;
                } else if (board[x][y] == opponent) {
                    string += opponentChar;
                } else {
                    string += '*';
                }
            }
        }
        System.out.println(string);
    }
}
