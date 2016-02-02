package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Edge;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI.SimpleAI;

public abstract class EarthQuakeStrategy implements FancyHandle {

	@Override
	public int action(Map gameMap, Player markingPlayer) {
		
		int points = 0;
		
		int edgesToMark = gameMap.getUnmarkedEdges().size();
		for (int edgeID = 0; edgeID < gameMap.getEdges().length; edgeID++) {
			gameMap.undo(edgeID);
		}
		
		SimpleAI ai = new SimpleAI(gameMap);
		for (int i = 0; i < edgesToMark; i++) {
			points += gameMap.markEdge(ai.suggestTurn(), markingPlayer, false);
		}
		
		return points;
	}

}
