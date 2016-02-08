package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.AI;

import java.util.LinkedList;
import java.util.Random;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

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
		LinkedList<Integer> selectableEdges = new LinkedList<>(gameMap.getUnmarkedEdges());
		
		return selectableEdges.get(randNum.nextInt(max));
	}

}
