package alphareversi.game.tictactoemodule;

/**
 * Created by daant on 25-Mar-16.
 */
public class ArtificialIntelligence implements Player {
    private TicTacToeModel model;
    private int[][] board;

    public ArtificialIntelligence(TicTacToeModel model) {
        this.model = model;
        board = model.getBoard();
    }

    @Override
    public int chooseMove() {
        Best best = chooseMove(model.opponent);
        return best.row * 3 + best.column;
    }

    /**
     * Chooses a move.
     */
    public Best chooseMove(int side) {
        int opp; // The other side
        Best reply; // Opponent's best reply
        int simpleEval; // Result of an immediate evaluation
        int bestRow = 0;
        int bestColumn = 0;
        int value = 0;

        if ((simpleEval = model.positionValue()) != model.UNCLEAR) {
            return new Best(simpleEval);
        } else {

            if (side == model.opponent) {
                value = model.SELF_WIN;
                opp = model.getOpponent(model.opponent);

            } else {
                value = model.OPPONENT_WIN;
                opp = model.getOpponent(side);
            }
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (model.squareIsEmpty(i, j)) {
                        place(i, j, side);
                        reply = chooseMove(opp);

                        place(i, j, model.EMPTY);

                        if ((side == model.opponent && reply.val > value)
                                || (side == model.self && reply.val < value)) {
                            value = reply.val;
                            bestRow = i;
                            bestColumn = j;
                        }
                    }
                }
            }
            return new Best(value, bestRow, bestColumn);
        }
    }

    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece ) {
        board[row][column] = piece;
    }

    private class Best {
        int row;
        int column;
        int val;

        public Best(int value) {
            this(value, 0, 0);
        }

        public Best(int value, int row, int column) {
            val = value;
            row = row;
            column = column;
        }
    }
}
