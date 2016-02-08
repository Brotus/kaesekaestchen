package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.AI;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

/**
 * And AI provides suggestions of Edges to be chosen.
 *
 */
public abstract class AI {

	/**
	 * Used to get current game status.
	 */
	protected Map gameMap;

	public AI(Map gameMap) {
	
		this.gameMap = gameMap;
		
	}
	
	public abstract int suggestTurn();
	
	
	
}
