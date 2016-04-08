package alphareversi.game.tictactoemodule;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class TicTacToeViewController{

	@FXML private GridPane gridPane;
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
            	canvas.setId("blank");
            	canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                    	int row = GridPane.getRowIndex(canvas).intValue();
                    	int col = GridPane.getColumnIndex(canvas).intValue();
                    	int move = convertMove(row, col);
                    	if(!ticTacToeModel.gameOver() && ticTacToeModel.getSide() == ticTacToeModel.getSelf() && ticTacToeModel.moveOk(move)){
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
    }
    
	public void updateBoard(int[][] board){
		for(int col = 0; col < board.length; col++){
			for(int row = 0; row < board.length; row++){
				Canvas canvas = getCanvasFromGridPane(row,col);
				GraphicsContext gc = canvas.getGraphicsContext2D();
				int piece = board[col][row];
				if(piece == ticTacToeModel.getEmpty()){
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					canvas.setId("blank");
				}
				else if(canvas.getId().equals("blank")){
					if((piece == ticTacToeModel.getSelf() && ticTacToeModel.getSelfChar() == 'X') || (piece == ticTacToeModel.getOpponent() && ticTacToeModel.getOpponentChar() == 'X')){
						gc.strokeLine(20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
						gc.strokeLine(canvas.getWidth() - 20, 20, 20, canvas.getHeight() - 20);
						canvas.setId("filled");
					}
					else{
						gc.strokeOval(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20);
						canvas.setId("filled");
					}
				}
			}
		}
	}
	
	private Canvas getCanvasFromGridPane(int col, int row){
	    for(Node node : gridPane.getChildren()){
	        if(GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row){
	            return (Canvas) node;
	        }
	    }
	    return null;
	}
}
