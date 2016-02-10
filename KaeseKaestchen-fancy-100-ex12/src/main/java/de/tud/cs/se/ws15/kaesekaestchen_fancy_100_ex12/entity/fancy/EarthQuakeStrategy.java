package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.AI.SimpleAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

public class EarthQuakeStrategy implements FancyHandle {

	@Override
	public int action(Map gameMap, Player markingPlayer) {
		System.out.println("What do you think? 5.1?");
		
		int points = 0;
		
		int edgesToMark = gameMap.getEdges().length - gameMap.getUnmarkedEdges().size();
		for (int edgeID = 0; edgeID < gameMap.getEdges().length; edgeID++) {
			gameMap.undo(edgeID);
		}
		
		SimpleAI ai = new SimpleAI(gameMap);
		for (int i = 0; i < edgesToMark; i++) {
			int suggestedTurn = ai.suggestTurn();
			points += gameMap.markEdge(suggestedTurn, markingPlayer, false);			
		}
		
		return points;
	}

}
