package alphareversi.game.reversimodule;

import alphareversi.Connection;
import alphareversi.commands.RecvCommand;
import alphareversi.commands.receive.RecvGameMoveCommand;
import alphareversi.commands.receive.RecvGameYourturnCommand;
import alphareversi.commands.send.SendMoveCommand;
import alphareversi.game.GameModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import alphareversi.game.Player;
import javafx.scene.layout.BorderPane;

/**
 * Created by Robert on 7-4-2016.
 */
public class ReversiModule extends GameModule {
    private Player player;
    private ReversiModel model;
    private String opponent;
    private String playerType;
    private static final String[] playerTypes = {"HUMAN"};

    private int selfSide;
    private int opponentSide;
    private static final String gameName = "Reversi";
    private BorderPane reversiView;


    /**
     * Reversi module for sever communication.
     * @param playerType The kind of player we want to play with (ex. AI or Human).
     * @param opponent The opponent's name.
     * @param playerToMove The player that should move first
     */
    public ReversiModule(String playerType, String opponent,
                         String playerToMove) throws Exception {

        this.opponent = opponent;
        if (!decidePlayer(playerType)) {
            System.out.println("Wrong Command");
        }
        decideWhoBegins(playerToMove);
        this.model = new ReversiModel(selfSide);
        reversiView = setReversiView();
        Connection connection = Connection.getInstance();
        connection.commandDispatcher.addListener(this);
        if (selfSide == 2) {
            if (player instanceof Human) {
                player.chooseMove();
            }
            //if (player instanceof artificialIntelligence) {
            //    int move = player.chooseMove();
            //    model.placePiece(move, selfSide);
            //    updateMoveCommand(move);
            //}
        }
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
        System.out.println("send command");
        Connection connection = Connection.getInstance();
        SendMoveCommand command = new SendMoveCommand(move);
        connection.sendMessage(command);
    }

    /**
     * Checks who should begin.
     */
    private void decideWhoBegins(String playerToMove) throws Exception {
        if (!opponent.equals(playerToMove)) {
            selfSide = 2;
            opponentSide = 1;
            if (player instanceof Human) {
                ((Human) player).setSide(selfSide);
            }
        } else {
            selfSide = 1;
            opponentSide = 2;
            if (player instanceof Human) {
                ((Human) player).setSide(selfSide);
            }

        }
    }

    /**
     * Decides if the string in the class constructor matches. with on off the classes with the
     * interface Player.
     */
    private boolean decidePlayer(String playerType) {
        if (playerType.equals("HUMAN")) {
            this.player = new Human();
            this.playerType = playerType;
            return true;
        }
        return false;
    }

    private int processMove(RecvGameMoveCommand command) {
        return Integer.parseInt(command.getMove());
    }

    @Override
    public SendMoveCommand send(SendMoveCommand command) {
        //TODO: yeah, this is fucked
        // the implementation of this is in another method. This is also fucked on TicTacToe
        return null;
    }

    @Override
    public BorderPane getView() {
        return reversiView;
    }

    @Override
    public void commandReceived(RecvCommand command) {
        if (command instanceof RecvGameMoveCommand) {
            RecvGameMoveCommand com = (RecvGameMoveCommand) command;
            System.out.println(com.getPlayer() + " : made move : " + com.getMove().toString());
            if (this.opponent.equals(((RecvGameMoveCommand) command).getPlayer())) {
                model.placePiece(processMove((RecvGameMoveCommand) command), opponentSide);
            }
        } else if (command instanceof RecvGameYourturnCommand) {
            RecvGameYourturnCommand com = (RecvGameYourturnCommand) command;
            System.out.println("it is now my turn");
            if (player instanceof Human) {
                this.player.chooseMove();
            }
            //            if (player instanceof artificialIntelligence) {
            //                int move = player.chooseMove();
            //                model.placePiece(move, selfSide);
            //                updateMoveCommand(move);
            //            }
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

    public static String[] getPlayerTypes() {
        return playerTypes;
    }

    public static String getGameName() {
        return gameName;
    }
}
