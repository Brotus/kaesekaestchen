package entity.AI;

import entity.Map;
import java.util.Random;

public class SimpleAI extends AI {

	public SimpleAI(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int suggestTurn() {
		System.out.println("Simple suggests");
		int max = gameMap.getUnmarkedEdges().size();
		Random randNum = new Random();
		
		return gameMap.getUnmarkedEdges().get(randNum.nextInt(max));
	}

}
