package alphareversi.game.reversimodule;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ReversiController {
	@FXML private GridPane gridPane;
	@FXML private Label opponent;
	@FXML private Label opponentWins;
	@FXML private Label opponentLosses;
	@FXML private Label playerWins;
	@FXML private Label playerLosses;
	@FXML private Label serverMessage;
	@FXML private Label round;

    private ReversiModel reversiModel;

/*    public ReversiController(ReversiModel reversiModel) {
		this.reversiModel = reversiModel;
	}*/

	public static void main(String[] args) {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource
						("C:\\Users\\Robert\\Documents\\GitHub\\AlphaReversi\\src\\alphareversi\\game\\reversimodule"));


	}


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
	public void initialize() {
		for (Node node: gridPane.getChildren()) {
			if ( node instanceof Canvas ) {
				Canvas canvas = (Canvas) node;
				canvas.setId("blank");
				canvas.addEventFilter(MouseEvent.MOUSE_PRESSED,
						new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e ) {
						int row = GridPane.getRowIndex( canvas ) ;
						int col = GridPane.getColumnIndex( canvas ) ;
						int move = convertMove(row, col);
						if (! reversiModel.gameOver( reversiModel.getBoard() )
								&& reversiModel.moveOk(move, reversiModel.getMySide())) {
							reversiModel.placePiece(move, reversiModel.getMySide());
						}
					}
				});
			}
		}
	}
    
    private int convertMove(int row, int col){
    	return (row * 8) + col;
    }
    
    public void setReversiModel(ReversiModel reversiModel) {
		this.reversiModel = reversiModel;
    	//bindLabels();
    }
   
    private void bindLabels() {
		//opponent.textProperty().bind(reversiModel.getOpponent());
        //opponentWins.textProperty().bind(reversiModel.getLosses().asString());
        //opponentLosses.textProperty().bind(reversiModel.getWins().asString());
        //playerWins.textProperty().bind(reversiModel.getWins().asString());
        //playerLosses.textProperty().bind(reversiModel.getLosses().asString());
        //serverMessage.textProperty().bind(reversiModel.getServerMessage());
        //round.textProperty().bind(reversiModel.getRound().asString());
    }
    
	public void updateBoard(int[][] board) {
		for (int col = 0; col < board.length; col++) {
			for(int row = 0; row < board.length; row++) {
				Canvas canvas = getCanvasFromGridPane(row,col);
				GraphicsContext gc = canvas.getGraphicsContext2D();
				int piece = board[col][row];
				if (piece == reversiModel.getEmpty() ) {
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					canvas.setId("blank");
				}
				else if (canvas.getId().equals("blank")) {
					if ((piece == reversiModel.getMySide() && reversiModel.getMyCharacter() == 'B') ||
							(piece == reversiModel.getOpponent(reversiModel.getMySide())
									&& reversiModel.getOpponentCharacter() == 'B')) {
						gc.strokeLine(20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
						gc.strokeLine(canvas.getWidth() - 20, 20, 20, canvas.getHeight() - 20);
						canvas.setId("filled");
					}
					else {
						gc.strokeOval(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20);
						canvas.setId("filled");
					}
				}
			}
		}
	}
	
	private Canvas getCanvasFromGridPane(int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return (Canvas) node;
	        }
	    }
	    return null;
	}
}
