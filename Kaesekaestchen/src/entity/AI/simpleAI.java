package entity.AI;

import entity.Map;
import java.util.Random;

public class simpleAI extends AI {

	public simpleAI(Map gameMap) {
		super(gameMap);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int suggestTurn() {
		int max = gameMap.getEdgeCount();
		Random randNum = new Random() ;
		
		return randNum.nextInt(max);
	}

}
