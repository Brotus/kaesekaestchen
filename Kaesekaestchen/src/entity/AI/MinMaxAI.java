package entity.AI;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import entity.Map;
import entity.Player;

public class MinMaxAI extends AI {

	/**
	 * Here the Ratings will be stored. This is cleared every time suggestTurn()
	 * is run.
	 */
	HashMap<Integer, Integer> edgeHash = new HashMap<>();
	/**
	 * Pseudo player - needed to run Map.markEdge(EdgeID, Player);
	 */
	private Player AIPlayer;
	/**
	 * Layers of recursion, we decided that two is adequately.
	 */
	int layers = 2;

	/**
	 * Creating an artificial intelligence for the game using the MinMax-Algorithm
	 * @param gameMap
	 */
	public MinMaxAI(Map gameMap) {
		super(gameMap);
	}

	/**
	 * This method rates the minimum score a path would give and chooses the
	 * maximum of all possible choices.
	 */
	public int suggestTurn() {
		edgeHash.clear();

		// Rating all possible Edges
		for (Integer i : gameMap.getUnmarkedEdges()) {
			edgeHash.put(i, rate(new LinkedList<Integer>(), i, gameMap, layers));
		}

		// picking one with the highest rating
		for (Integer i : edgeHash.keySet()) {
			int max = Collections.max(edgeHash.values());

			if (edgeHash.get(i).equals(max)) {
				return i;
			}
		}

		// should not get until here
		return 0;
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
	 * @param n
	 *            the layers of recursion not yet reached
	 * @return a rating of choosing this edge
	 */
	private int rate(LinkedList<Integer> path, int edge, Map MapAtThisPoint,
			int n) {

		// sign is used to subtract the score if its the other players turn
		int sign = 1;
		// points is the score made with this decision
		int points = 0;
		// we clone the map, so that the actual map remains unchanged
		Map newMap = MapAtThisPoint.clone();

		// the clone Map gets the edge marked and returns if a point was scored
		switch (newMap.markEdge(edge, AIPlayer)) {
		case INVALID:
			System.err.println("There should be no marked Edges to check");
			// return 0;
			// points += 0;
			// sign = 1;
			break;

		case MARKED:
			// points will not change, but it is the other player's turn
			sign = -1;
			--n;
			break;

		case ONE:
			// getting one point - still players turn
			points = 1;
			break;

		case TWO:
			points = 2;
			break;
		}

		// if there are too many layers of recursion we will break here
		// returning
		// the points
		if (n == 0) {
			return points;
		}

		// We create a clone of the path and add the recently marked edge to it
		LinkedList<Integer> nextPath = new LinkedList<>();
		nextPath.addAll(path);
		nextPath.add(edge);

		// We make a list to save all values of the edges that may be selected
		// next
		LinkedList<Integer> values = new LinkedList<>();

		// recursively this method is called with the new path, new map, the
		// levels of recursion left on each of the edges not marked yet.
		for (int nextEdge : newMap.getUnmarkedEdges()) {
			values.add(rate(nextPath, nextEdge, newMap, n));
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
