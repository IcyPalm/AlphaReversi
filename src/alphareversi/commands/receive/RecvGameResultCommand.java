package alphareversi.commands.receive;

import alphareversi.commands.Command;

/**
 * Created by Joost van Berkel on 3/24/2016.
 *
 * Resultaat van een match die opgegeven is door een speler, bericht naar beide spelers:
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Player forfeited match"}
 * De match is afgelopen, <speler> heeft de match opgegeven.
 *
 * #####################################################################
 * Resultaat van een match, speler heeft de verbinding verbroken:
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "Client disconnected"}
 * De match is afgelopen, <speler> heeft de verbinding verbroken.
 *
 *
 * #####################################################################
 * Resultaat van een match ontvangen, bericht naar beide spelers.
 *
 * S: SVR GAME <speler resultaat> {PLAYERONESCORE: "<score speler1>", PLAYERTWOSCORE: "<score speler2>", COMMENT: "<commentaar op resultaat>"}
 * De match is afgelopen, <speler resultaat> kan de waarde 'WIN', 'LOSS' of 'DRAW' bevatten.
 */
public class RecvGameResultCommand  extends Command {
}
