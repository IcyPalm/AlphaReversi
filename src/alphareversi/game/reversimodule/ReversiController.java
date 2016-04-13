package alphareversi.game.reversimodule;

import alphareversi.Connection;
import alphareversi.commands.send.SendMoveCommand;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ReversiController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label ourName;
    @FXML
    private Label opponentName;
    @FXML
    private Label turn;
    @FXML
    private Label playerStones;
    @FXML
    private Label enemyStones;
    @FXML
    private ProgressBar ourTime;
    @FXML
    private ProgressBar opponentTime;

    private ReversiModel reversiModel;
    private Player player;

    public ReversiController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    public void initialize() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Canvas) {
                Canvas canvas = (Canvas) node;
                canvas.setId("blank");
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.GREEN);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                canvas.addEventFilter(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                int row = GridPane.getRowIndex(canvas);
                                int col = GridPane.getColumnIndex(canvas);
                                int move = convertMove(row, col);
                                if (player instanceof Human
                                        && !reversiModel.gameOver()
                                        && ((Human) player).allowedToPlay()
                                        && reversiModel.moveOk(move, reversiModel.getMySide())) {
                                    ((Human) player).playMove(move);
                                }
                            }
                        });
            }
        }
        //updateBoard(reversiModel.getBoard());
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

    private int convertMove(int row, int col) {
        return (row * 8) + col;
    }

    /**
     * Set the reversi model with details of the match.
     * @param reversiModel reversiModel instance
     */
    public void setReversiModel(ReversiModel reversiModel) {
        this.reversiModel = reversiModel;
        this.opponentName.setText(reversiModel.getOpponentUsername());
        this.ourName.setText(reversiModel.getOurUsername());
        new Thread(new StartTimer(reversiModel.getTurnTime())).start();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Update the GUI so that it represents the Board.
     *
     * @param board The board that needs to be updated.
     */
    public void updateBoard(int[][] board) {
        System.out.println("I should have updated the board");
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                Canvas canvas = getCanvasFromGridPane(row, col);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                int piece = board[col][row];
                if (piece == reversiModel.getEmpty()) {
                    canvas.setId("blank");
                } else {
                    if (piece == 1) {
                        gc.setFill((Color.WHITE));
                        gc.fillOval(10, 20, canvas.getWidth() - 40, canvas.getHeight() - 40);

                        canvas.setId("filled");
                    } else {
                        gc.setFill((Color.BLACK));
                        gc.fillOval(10, 20, canvas.getWidth() - 40, canvas.getHeight() - 40);
                        canvas.setId("filled");
                    }
                }
            }
        }

        Platform.runLater(() -> {
            String turn = "";
            if (reversiModel.getPlayerOnTurn() == reversiModel.getMySide()) {
                turn = "My turn!";
            } else {
                turn = "Opponents turn";
            }
            this.turn.setText(turn);
        });

        setStonesOnGui();

    }

    private Canvas getCanvasFromGridPane(int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (Canvas) node;
            }
        }
        return null;
    }

    /**
     * Get the stone count from model and update in GUI.
     */
    public void setStonesOnGui() {
        Platform.runLater(() -> {
            int mySide = reversiModel.getMySide();
            playerStones.setText(String.valueOf(reversiModel.getBoardInstance().getScore(mySide)));
            enemyStones.setText(String.valueOf(
                    reversiModel.getBoardInstance().getScore(reversiModel.getOpponent(mySide))));
        });
    }

    /**
     * Runnable inner Class that requests a new playerList every 5 seconds, if we are connected with
     * the server.
     */
    private class StartTimer implements Runnable {

        double time;
        double timeDivider;
        double currentProgress;
        boolean gameBusy = true;

        StartTimer(int time) {
            this.time = time * 1000.0;
            this.timeDivider = 100.0 / this.time;
        }

        @Override
        public void run() {
            boolean lastPlayerSelf = reversiModel.getMySide() == reversiModel.getPlayerOnTurn();
            while (gameBusy) {
                ProgressBar progressBar = null;
                if (reversiModel.getMySide() == reversiModel.getPlayerOnTurn()) {
                    if (lastPlayerSelf == false) {
                        currentProgress = 0.00;
                    }
                    lastPlayerSelf = true;
                    progressBar = ourTime;
                    opponentTime.setProgress(0.0);
                    if (currentProgress >= 1) {
                        currentProgress = 0.00;
                    }
                } else {
                    if (lastPlayerSelf == true) {
                        currentProgress = 0.00;
                    }
                    lastPlayerSelf = false;
                    progressBar = opponentTime;
                    ourTime.setProgress(0.0);
                    if (currentProgress >= 1) {
                        currentProgress = 0.00;
                    }
                }

                currentProgress = currentProgress + timeDivider;
                progressBar.setProgress(currentProgress);
                gameBusy = !reversiModel.gameOver();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
