package alphareversi.game.tictactoemodule;

import alphareversi.Connection;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.Player;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class TicTacToeViewController {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label opponentName;
    @FXML
    private Label opponentPlayerType;
    @FXML
    private Label opponentCharacter;
    @FXML
    private Label playerPlayerType;
    @FXML
    private Label playerCharacter;
    @FXML
    private Label message;
    private TicTacToeModel ticTacToeModel;
    private Player player;

    public TicTacToeViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    private void initialize() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Canvas) {
                Canvas canvas = (Canvas) node;
                canvas.setId("blank");
                canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int row = GridPane.getRowIndex(canvas).intValue();
                        int col = GridPane.getColumnIndex(canvas).intValue();
                        int move = convertMove(row, col);
                        if (player instanceof Human
                                && !ticTacToeModel.gameOver()
                                && ((Human) player).allowedToPlay()
                                && ticTacToeModel.moveOk(move)) {
                            ticTacToeModel.playMove(move);
                            updateMoveCommand(move);
                            ((Human) player).setAllowedToPlayFalse();
                        }
                    }
                });
            }
        }
    }

    private int convertMove(int row, int col) {
        return (row * 3) + col;
    }

    /**
     * Bind the tictactoe model to the view.
     * @param ticTacToeModel the model to bind
     * @param playerType type of player
     * @param opponentName name of the opponent
     */
    public void setTicTacToeModel(TicTacToeModel ticTacToeModel,
                                  String playerType, String opponentName) {
        this.ticTacToeModel = ticTacToeModel;
        opponentCharacter.setText(String.valueOf(this.ticTacToeModel.getOpponentChar()));
        playerCharacter.setText(String.valueOf(this.ticTacToeModel.getSelfChar()));
        playerPlayerType.setText(playerType);
        this.opponentName.setText(opponentName);
    }

    /**
     * update the boars visual.
     * @param board state of the game
     */
    public void updateBoard(int[][] board) {
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                Canvas canvas = getCanvasFromGridPane(row, col);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                int piece = board[col][row];
                if (piece == ticTacToeModel.getEmpty()) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    canvas.setId("blank");
                } else if (canvas.getId().equals("blank")) {
                    if ((piece == ticTacToeModel.getSelf() && ticTacToeModel.getSelfChar() == 'X')
                            || (piece == ticTacToeModel.getOpponent()
                            && ticTacToeModel.getOpponentChar() == 'X')) {
                        gc.strokeLine(20, 20, canvas.getWidth() - 20, canvas.getHeight() - 20);
                        gc.strokeLine(canvas.getWidth() - 20, 20, 20, canvas.getHeight() - 20);
                        canvas.setId("filled");
                    } else {
                        gc.strokeOval(10, 10, canvas.getWidth() - 20, canvas.getHeight() - 20);
                        canvas.setId("filled");
                    }
                }
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Updates the current SendMoveCommand with the latest move.
     *
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        System.out.println("send command");
        Connection connection = Connection.getInstance();
        SendMoveCommand command = new SendMoveCommand(move);
        connection.sendMessage(command);
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
