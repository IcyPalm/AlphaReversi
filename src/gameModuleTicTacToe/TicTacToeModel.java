package gameModuleTicTacToe;

/**
 * Created by Daniël on 25-Mar-16.
 */
public class TicTacToeModel {
    private int [ ] [ ] board = new int[ 3 ][ 3 ];

    private static final int self        = 0;
    private static final int opponent     = 1;
    public  static final int EMPTY        = 2;

    public  static final int HUMAN_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int COMPUTER_WIN = 3;

    private int position=UNCLEAR;

    private char side;
    private char selfChar, opponentChar;

    private TicTacToeView view;

    public TicTacToeModel() {
        view = new TicTacToeView(board, self, opponent);
        clearBoard( );
        initSide();
    }


    // play move
    public void playMove(int move, char side) {
        board[move/3][ move%3] = this.side;
        if (side==self) this.side=opponent;  else this.side=self;
        view.print(selfChar, opponentChar);
    }
    // Simple supporting routines
    private void clearBoard( )
    {
        // over elk vakje heen gaan en leegmaken.
        for (int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                board[x][y] = EMPTY;
            }
        }
    }
    private boolean boardIsFull( )
    {
        for (int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                if (board[x][y] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAWin( int side )
    {
        // verticaal
        for(int y = 0; y < 3; y++) {
            if (board[0][y] == board[1][y] && board[1][y] == board[2][y] && board[0][y] == side) {
                return true;
            }
        }
        // horizontaal
        for(int x = 0; x < 3; x++) {
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
    // Compute static value of current position (win, draw, etc.)
    public int positionValue() {
        {
            if (isAWin(opponent)) {return COMPUTER_WIN;}
            if (isAWin(self)) {return HUMAN_WIN;}
            if (boardIsFull()) {return DRAW;}
            return UNCLEAR;
        }
    }
    public boolean gameOver()
    {
        this.position=positionValue();
        return this.position!=UNCLEAR;
    }

    //check if move ok
    public boolean moveOk(int move) {
        return ( move>=0 && move <=8 && board[move/3 ][ move%3 ] == EMPTY );
    }

    private void initSide()
    {
        if (this.side==self) { selfChar='X'; opponentChar='O'; }
        else                     { selfChar='O'; opponentChar='X'; }
    }
    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece )
    {
        board[ row ][ column ] = piece;
    }

    private boolean squareIsEmpty( int row, int column )
    {
        return board[ row ][ column ] == EMPTY;
    }

    public void setOpponentPlays()
    {
        this.side=opponent;
        initSide();
    }

    public void setSelfPlays()
    {
        this.side=self;
        initSide();
    }


}
