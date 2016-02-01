package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;

import java.util.Random;

public class SimpleAI extends AI {
	
	/**
	 * Creating a simple artificial intelligence for the game, choosing random edges of the edges left.
	 * @param gameMap the map of the game
	 */

	public SimpleAI(Map gameMap) {
		super(gameMap);
	}

	@Override
	public int suggestTurn() {
		int max = gameMap.getUnmarkedEdges().size();
		Random randNum = new Random();
		
		return gameMap.getUnmarkedEdges().get(randNum.nextInt(max));
	}

}
