package alphareversi.game.tictactoemodule;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class TicTacToeViewController{

	@FXML private GridPane gridPane;
	@FXML private Label opponent;
    @FXML private Label opponentWins;
    @FXML private Label opponentLosses;
    @FXML private Label playerWins;
    @FXML private Label playerLosses;
    @FXML private Label serverMessage;
    @FXML private Label round;
    private TicTacToeModel ticTacToeModel;
    
    public TicTacToeViewController(){}
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
    	for(Node node: gridPane.getChildren()){
            if(node instanceof Canvas){
            	Canvas canvas = (Canvas) node;
            	canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                    	int row = GridPane.getRowIndex(canvas).intValue();
                    	int col = GridPane.getColumnIndex(canvas).intValue();
                    	int move = convertMove(row,col);
                    	if(ticTacToeModel.getSide() == ticTacToeModel.getSelf() && ticTacToeModel.moveOk(move)){
                    		ticTacToeModel.playMove(move);
                    	}
                    }
                });
            }
        }
    }
    
    private int convertMove(int row, int col){
    	return (row * 3) + col;
    }
    
    public void setTicTacToeModel(TicTacToeModel ticTacToeModel){
    	this.ticTacToeModel = ticTacToeModel;
    	bindLabels();
    	updateBoard(this.ticTacToeModel.getBoard());
    }
   
    private void bindLabels(){
    	//opponent.textProperty().bind(ticTacToeModel.getOpponent());
        //opponentWins.textProperty().bind(ticTacToeModel.getLosses().asString());
        //opponentLosses.textProperty().bind(ticTacToeModel.getWins().asString());
        //playerWins.textProperty().bind(ticTacToeModel.getWins().asString());
        //playerLosses.textProperty().bind(ticTacToeModel.getLosses().asString());
        //serverMessage.textProperty().bind(ticTacToeModel.getServerMessage());
        //round.textProperty().bind(ticTacToeModel.getRound().asString());
    }
    
	public void updateBoard(int[][] board){
		for(int col = 0; col < board.length; col++){
			for(int row = 0; row < board.length; row++){
				Canvas canvas = getCanvasFromGridPane(col,row);
				if(canvas != null){
					GraphicsContext gc = canvas.getGraphicsContext2D();
					int piece = board[col][row];
					if(piece == ticTacToeModel.getSelf()){
						gc.strokeLine(20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
						gc.strokeLine(canvas.getWidth() - 20, 20, 20, canvas.getHeight() - 20);
					}
					else if(piece == ticTacToeModel.getOpponent()){
						gc.strokeOval(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20);
					}
				}
			}
		}
	}
	
	private Canvas getCanvasFromGridPane(int col, int row){
	    for(Node node : gridPane.getChildren()){
	        if(node instanceof Canvas && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row){
	            return (Canvas) node;
	        }
	    }
	    return null;
	}
}
