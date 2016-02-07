package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;

/**
 * This interface is a handle for fancy strategies.
 *
 */
public interface FancyHandle {
	
	/**
	 * This is called when a player selects an edge which has not yet been selected and which has previously been selected to cause a fancy action.
	 * 
	 * @param gameMap the map the action is supposed to happen on
	 * @param markingPlayer	the player who caused the action. This is needed if the action causes one or more fields to close.
	 * @return the amount of fields the player closed by causing the fancy action.
	 */
	public int action(Map gameMap, Player markingPlayer);

}
