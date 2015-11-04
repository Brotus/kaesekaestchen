package entity.AI;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import entity.Edge;
import entity.Map;
import entity.Player;

public class MinMaxAI extends AI {
	
	HashMap<Integer, Integer> edgeHash = new HashMap<>();
	private Player AIPlayer; 
	int layers = 2;

	public MinMaxAI(Map gameMap) {
		super(gameMap);
	}
	
	public int suggestTurn() {
		edgeHash.clear();
		Edge[] mapEdges = gameMap.getEdges();
		for(int i = 0; i < mapEdges.length; i++){
			if (!mapEdges[i].isMarked()) {
				edgeHash.put(i, rate(new LinkedList<Integer>(), i, gameMap, 0, 0, layers));
			}
		}
		
		for (Integer i : edgeHash.keySet()) {
			if (edgeHash.get(i).equals(Collections.max(edgeHash.values()))){
				System.out.println(edgeHash.get(i));
				return edgeHash.get(i);
			}
		}
		return 0;
	}

	private int rate(LinkedList<Integer> path, int edge, Map MapAtThisPoint, int result, int n, int N) {
		if(n == N)
			return result;
		path.addLast(edge);
		int points = result;
		Map newMap = MapAtThisPoint.clone();
		int sign = 1;
		
		switch (newMap.markEdge(path.getLast(), AIPlayer)) {
		case INVALID:
			System.err.println("There shoud be no marked Edges to check");
//			points += 0;
//			sign = 1;
			break;

		case MARKED:
			points += 0;
			sign = -1;
			break;

		case ONE:
			points += 1;
			break;

		case TWO:
			points += 2;
			break;
		}
		
		int[] results = new int[gameMap.getEdges().length];
		int i = 0;
		for (int nextEdge : edgeHash.keySet()){
			if (!path.contains(nextEdge)){
				results[i] = sign*rate(path, nextEdge, newMap, points, ++n, N);
				i++;
			}
		}
		
		points = results[0];
		for(int val : results) {
			if (val < points) {
				points = val;
			}
		}			
			return points;
	}
	
}
