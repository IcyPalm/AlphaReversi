package alphareversi.model;

import alphareversi.views.ticTacToeView.TicTacToeViewController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TicTacToeModel{
	
	private StringProperty opponent;
	private IntegerProperty wins;
	private IntegerProperty losses;
	private IntegerProperty round;
	private StringProperty serverMessage;
	private int[][] board;
	private int self = 0;
	private int opp = 1;
	private int empty = 2;
	private char selfChar = 'o';
	private char oppChar = 'x';
	private int side;
	private TicTacToeViewController viewController;
	
	public TicTacToeModel(String opponent){
		wins = new SimpleIntegerProperty(0);
		losses = new SimpleIntegerProperty(0);
		round =  new SimpleIntegerProperty(0);
		this.opponent = new SimpleStringProperty(opponent);
		serverMessage = new SimpleStringProperty("Your turn!");
		board = new int[3][3];
		side = self;
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				board[x][y] = empty;
			}
		}
	}
	
	public void playMove(int move) {
        board[move / 3][move % 3] = this.side;
        /*if(side == self){
            this.side = opp;
        }
        else{
            this.side = self;
        }*/
        viewController.updateBoard(board);
    }
	
	public boolean moveOk(int move){
        return (move >= 0 && move <= 8 && board[move / 3][move % 3] == empty);
    }
	
	public void setViewController(TicTacToeViewController vieController){
		this.viewController = vieController;
	}
	
	public IntegerProperty getWins(){
		return wins;
	}
	
	public IntegerProperty getLosses(){
		return losses;
	}
	
	public IntegerProperty getRound(){
		return round;
	}
	
	public StringProperty getOpponent(){
		return opponent;
	}
	
	public StringProperty getServerMessage(){
		return serverMessage;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getSelf(){
		return self;
	}
	
	public int getOpp(){
		return opp;
	}
	
	public int getEmpty(){
		return empty;
	}
	
	public char getSelfChar(){
		return selfChar;
	}
	
	public char getOppChar(){
		return oppChar;
	}
	
	public int getSide(){
		return side;
	}
}
