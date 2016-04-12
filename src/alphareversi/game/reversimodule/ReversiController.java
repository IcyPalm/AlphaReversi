package alphareversi.game.reversimodule;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ReversiController {
    @FXML private GridPane gridPane;
    @FXML private Label opponent;
    @FXML private Label opponentWins;
    @FXML private Label opponentLosses;
    @FXML private Label playerWins;
    @FXML private Label playerLosses;
    @FXML private Label serverMessage;
    @FXML private Label round;
    @FXML private Label blackScore;
    @FXML private Label whiteScore;

    private ReversiModel reversiModel;

    public ReversiController() {
        reversiModel = new ReversiModel(1);
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
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.GREEN);
                gc.fillRect(10, 10, canvas.getWidth(), canvas.getHeight());
                canvas.addEventFilter(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event ) {
                                int row = GridPane.getRowIndex( canvas ) ;
                                int col = GridPane.getColumnIndex( canvas ) ;
                                int move = convertMove(row, col);
                                if (! reversiModel.gameOver( reversiModel.getBoard())
                                        &&
                                        reversiModel.moveOk(move, reversiModel.getPlayerOnTurn())) {
                                    reversiModel.placePiece(move, reversiModel.getPlayerOnTurn());
                                    updateBoard(reversiModel.getBoard());
                                }
                            }
                        });
            }
        }
        updateBoard(reversiModel.getBoard());
    }
    
    private int convertMove(int row, int col) {
        return (row * 8) + col;
    }
    
    public void setReversiModel(ReversiModel reversiModel) {
        this.reversiModel = reversiModel;
    }

    /**
     * Update the GUI so that it represents the Board.
     * @param board The board that needs to be updated.
     */
    public void updateBoard(int[][] board) {
        for ( int col = 0; col < board.length; col++ ) {
            for ( int row = 0; row < board.length; row++ ) {
                Canvas canvas = getCanvasFromGridPane(row,col);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                int piece = board[col][row];
                if (piece == reversiModel.getEmpty() ) {
                    canvas.setId("blank");
                } else {
                    if ((piece == 1)) {

                        //gc.strokeLine(20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
                        //gc.strokeLine(canvas.getWidth() - 20, 20, 20, canvas.getHeight() - 20);
                        gc.setFill((Color.BLACK));
                        gc.fillOval(10, 20, canvas.getWidth() - 40, canvas.getHeight() - 40);

                        canvas.setId("filled");
                    } else {
                        gc.setFill((Color.WHITE));
                        gc.fillOval(10, 20, canvas.getWidth() - 40, canvas.getHeight() - 40);
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
