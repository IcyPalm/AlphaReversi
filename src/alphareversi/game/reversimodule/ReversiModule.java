package alphareversi.game.reversimodule;

import alphareversi.Connection;
import alphareversi.Logger;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.SendCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameResultCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.send.SendMessageCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.GameModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 * Created by Robert on 7-4-2016.
 */
public class ReversiModule extends GameModule {
    private static final String[] playerTypes = {"HUMAN", "AI", "RANDOM-AI", "ZONE-AI"};
    private static final String gameName = "Reversi";
    private Player player;
    private ReversiModel model;
    private String opponent;
    private String playerType;
    private BorderPane reversiView;
    private Logger logger = new Logger("Reversi");

    /**
     * Reversi module for sever communication.
     *
     * @param playerType   The kind of player we want to play with (ex. AI or Human).
     * @param opponent     The opponent's name.
     * @param playerToMove The player that should move first
     */
    public ReversiModule(String playerType, String opponent, String playerToMove)
            throws Exception {
        this.opponent = opponent;

        this.model = new ReversiModel(
                opponent.equals(playerToMove) ? Board.OPPONENT : Board.SELF
        );

        if (!this.decidePlayer(playerType)) {
            this.logger.err("Wrong Command");
        }

        Connection connection = Connection.getInstance();
        connection.commandDispatcher.addListener(this);

        this.player.addActionListener(event -> {
            int move = event.getID();
            if (move >= 0) {
                this.updateMoveCommand(move);
            }
        });

        if (this.model.getPlayerOnTurn() == Board.SELF) {
            this.player.startTurn();
        }
    }

    public static String[] getPlayerTypes() {
        return playerTypes;
    }

    public static String getGameName() {
        return gameName;
    }

    @Override
    public boolean gameOver() {
        return model.gameOver();
    }

    /**
     * Updates the current SendMoveCommand with the latest move.
     *
     * @param move the move to set the command to
     */
    public void updateMoveCommand(int move) {
        this.logger.log("Send command");
        this.send(new SendMoveCommand(move));
    }

    /**
     * Decides if the string in the class constructor matches. with on off the classes with the
     * interface Player.
     */
    private boolean decidePlayer(String playerType) {
        if (playerType.equals("HUMAN")) {
            this.player = new Human(this.model);
        } else if (playerType.equals("AI")) {
            this.player = new ReversiMinimaxPlayer(this.model);
        } else if (playerType.equals("RANDOM-AI")) {
            this.player = new RandomAi(this.model);
        } else if (playerType.equals("ZONE-AI")) {
            this.player = new ZoneAi(this.model);
        }

        if (this.player == null) {
            return false;
        }

        this.playerType = playerType;
        return true;
    }

    private int processMove(RecvGameMoveCommand command) {
        return Integer.parseInt(command.getMove());
    }

    public void send(SendCommand command) {
        Connection connection = Connection.getInstance();
        connection.sendMessage(command);
    }

    @Override
    public BorderPane getView() {
        try {
            reversiView = setReversiView();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return reversiView;
    }

    @Override
    public void commandReceived(RecvCommand command) {
        this.logger.log(
                "Receiving: " + command.getType() + " " + command.getMethod()
                        + "  " + command.getClass()
        );
        if (command instanceof RecvGameMoveCommand) {
            RecvGameMoveCommand com = (RecvGameMoveCommand) command;

            this.logger.log("Old board:");
            this.logger.log("--------");
            this.logger.log(this.model.getBoardInstance() + "");
            this.logger.log("--------");

            int position = processMove(com);
            int player = this.opponent.equals(com.getPlayer()) ? Board.OPPONENT : Board.SELF;

            this.logger.log(
                    com.getPlayer() + " (" + (player == Board.SELF ? "X" : "O") + ") "
                            + "made move: " + com.getMove() + " [" + (position / 8) + ", " + (position % 8) + "]"
            );

            try {
                model.placePiece(position, player);
            } catch (InvalidMoveException ime) {
                this.send(new SendMessageCommand(this.opponent, "Cheater!"));
                this.logger.err("Unsupported move!");
            }

            this.logger.log("New board:");
            this.logger.log("--------");
            this.logger.log(this.model.getBoardInstance() + "");
            this.logger.log("--------");
        } else if (command instanceof RecvGameYourturnCommand) {
            RecvGameYourturnCommand com = (RecvGameYourturnCommand) command;
            this.logger.log("it is now my turn");
            this.player.startTurn();
        } else if (command instanceof RecvGameResultCommand) {
            this.logger.log("Game over");
            this.model.setGameOver();
        }
    }

    private BorderPane setReversiView() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReversiModule.class.getResource("ReversiView.fxml"));
        BorderPane reversiView = (BorderPane) loader.load();

        ReversiController controller = loader.getController();
        model.setViewController(controller);
        setController(controller);

        return reversiView;
    }

    private void setController(ReversiController controller) {
        controller.setReversiModel(model);
        controller.setPlayer(player);
        controller.updateBoard(model.getBoard());
    }
}
