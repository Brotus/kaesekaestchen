package de.tud.cs.se.ws15.kaesekaestchen_team100.entity.AI;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.Player;

public class AdvancedAI extends AI {

	/**
	 * Here the Ratings will be stored. This is cleared every time suggestTurn()
	 * is run.
	 */
	HashMap<Integer, Integer> edgeHash = new HashMap<Integer, Integer>();
	/**
	 * Pseudo player - needed to run Map.markEdge(EdgeID, Player);
	 */
	private Player AIPlayer;
	/**
	 * Layers of recursion (This is how often the players alternated), we decided that two is adequately.
	 */
	private static final int MAX_LAYERS = 4;
	
	/**
	 * Maximum Depth of the decision tree.
	 */
	private static final int MAX_DEPTH = 5;

	/**
	 * Creating an artificial intelligence for the game using the MinMax-Algorithm
	 * @param gameMap the gamemap
	 */
	public AdvancedAI(Map gameMap) {
		super(gameMap);
	}

	/**
	 * This method rates the minimum score a path would give and chooses the
	 * maximum of all possible choices.
	 */
	public int suggestTurn() {
		edgeHash.clear();

		System.out.print("AI is thinking");
		// Rating all possible Edges
		for (Integer i : gameMap.getUnmarkedEdges()) {
			edgeHash.put(i, rate(new LinkedList<Integer>(), i, gameMap, MAX_LAYERS, MAX_DEPTH));
		System.out.print(".");
		}
		System.out.println();
		
		int max = Collections.max(edgeHash.values());
		int bestChoice = 0;
		for(Entry<Integer,Integer> entry: edgeHash.entrySet()){
			if(entry.getValue().equals(max) ){
				bestChoice = entry.getKey();
			}
		}
		// picking one with the highest rating
		/*for (Integer i : edgeHash.keySet()) {

			if (edgeHash.get(i).equals(max)) {
				bestChoice = i;
			}
		}*/

		// should not get until here
		return bestChoice;
	}

	/**
	 * Here the MinMax Algorithm finds the minimal score the Player will get in
	 * the future by choosing edge. We assume both players choose to make as
	 * much points for themselves as possible.
	 * 
	 * @param path
	 *            the List of EdgeIDs chosen in the past
	 * @param edge
	 *            the EdgeID that will be selected now
	 * @param MapAtThisPoint
	 *            a clone of the Map at this Point
	 * @param maxLayers
	 *            the layers of recursion not yet reached
	 * @param maxDepth the maximum depth
	 * @return a rating of choosing this edge
	 */
	public int rate(LinkedList<Integer> path, int edge, Map MapAtThisPoint,
			int maxLayers, int maxDepth) {

		// sign is used to subtract the score if its the other players turn
		int sign = 1;
		// points is the score made with this decision
		int points = 0;
		// we clone the map, so that the actual map remains unchanged
		Map newMap = MapAtThisPoint.copy();

		// the clone Map gets the edge marked and returns if a point was scored
		int fs = newMap.markEdge(edge, AIPlayer);
		switch (fs) {
		case -1:
			throw new IllegalArgumentException("There should be no marked Edges to check!");

		case 0:
			// points will not change, but it is the other player's turn
			sign = -1;
			--maxLayers;
			break;
			
		default:
			points = fs;
			if(!newMap.anotherTurn()){
				sign = -1;
				--maxLayers;
			}
		}

		// if there are too many layers of recursion we will break here
		// returning
		// the points
		if (maxLayers == 0 || maxDepth-- == 0) {
			return points;
		}

		// We create a clone of the path and add the recently marked edge to it
		LinkedList<Integer> nextPath = new LinkedList<Integer>();
		nextPath.addAll(path);
		nextPath.add(edge);

		// We make a list to save all values of the edges that may be selected
		// next
		LinkedList<Integer> values = new LinkedList<Integer>();

		// recursively this method is called with the new path, new map, the
		// levels of recursion left on each of the edges not marked yet.
		for (int nextEdge : newMap.getUnmarkedEdges()) {
			values.add(rate(nextPath, nextEdge, newMap, maxLayers, maxDepth));
		}

		// we return the points made with the largest score made in the upcoming
		// level of recursion. It is being subtracted if the player changed.
		// if the list is empty, the points will be returned.
		try {	
		return points + sign * Collections.max(values);
		} catch (NoSuchElementException e) {
			return points;
		}
	}
}
