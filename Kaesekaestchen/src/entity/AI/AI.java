package entity.AI;

import entity.Map;


public abstract class AI {

	Map gameMap;

	public AI(Map gameMap) {
	
		this.gameMap = gameMap;
		
	}
	
	public abstract int suggestTurn();
	
	
	
}