package entity.AI;

import entity.Map;

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
