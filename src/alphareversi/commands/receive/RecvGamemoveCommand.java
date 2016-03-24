package alphareversi.commands.receive;

import alphareversi.commands.Command;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Resultaat van een zet ontvangen, bericht naar beide spelers.
 *
 * S: SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
 * Er is een zet gedaan, dit bericht geeft aan wie deze gezet heeft, wat de reactie van het spel erop is
 */
public class RecvGameMoveCommand extends Command {
}
