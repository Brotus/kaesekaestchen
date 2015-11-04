package entity.AI;

import entity.Map;
import java.util.Random;

public class SimpleAI extends AI {

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
