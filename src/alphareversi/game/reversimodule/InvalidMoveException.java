package alphareversi.game.reversimodule;

import java.lang.Exception;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}
