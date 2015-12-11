package tug.game.ai;

import java.util.ArrayList;
import java.util.Map;

import tud.game.Board;
import tud.game.PlayerStats;

/**
 * 
 * represents node in game tree
 * 
 */
public class Node {

	private Board board;
	private MiniMax layer;
	private int depth;
	private String playerName;
	private String turn;
	private ArrayList<Node> children = new ArrayList<Node>();
	private boolean leaf = false;

	public Node(Board board, MiniMax layer, String player, int depth,
			int maxDepth, String turn) {
		this.board = board;
		this.layer = layer;
		this.depth = depth;
		this.turn = turn;
		this.playerName = player;
		createChildren(maxDepth); // builds game tree rekursively
	}

	/**
	 * creates children for game tree of the MINIMAX algorithm.
	 * 
	 * The game tree depth is restricted, because of the turn time explosion yet
	 * with small game boards
	 */
	private void createChildren(int maxDepth) {

		if (depth == maxDepth) {
			leaf = true;
			return;
		}
		String nextPlayer = "p1";
		if (playerName.equals("p1"))
			nextPlayer = "p2";

		MiniMax nextLayer = MiniMax.MAX;
		if (MiniMax.MAX == layer) {
			nextLayer = MiniMax.MIN;
			// playerName = "p2";
		}

		for (String field : board.getFields()) {
			Board boardCopy = board.copy();
			int numberOfBoxes = boardCopy.getScore(playerName);
			boardCopy.nextAction(Integer.valueOf(field), playerName);

			if (numberOfBoxes < boardCopy.getScore(playerName)) {
				children.add(new Node(boardCopy, layer, playerName, depth + 1,
						maxDepth, field));
			} else {
				children.add(new Node(boardCopy, nextLayer, nextPlayer,
						depth + 1, maxDepth, field));
			}
		}

		if (children.size() < 1) {
			leaf = true;
		}

	}

	private boolean isLeaf() {
		return leaf;
	}

	/**
	 * MINIMAX algorithm. The algorithm iterates recursively through the tree
	 * and evaluates the best turn
	 * 
	 * @param playerId
	 *            - id of player for whom the turn should be evaluted
	 * @return turn for the player
	 */
	public int miniMax(String playerId) {
		if (isLeaf())
			return evaluateUtility(playerId);

		int utility = children.get(0).miniMax(playerId);
		for (int i = 1; i < children.size(); i++) {
			if (layer == MiniMax.MAX) {
				utility = Math.max(utility, children.get(i).miniMax(playerId));
			} else {
				utility = Math.min(utility, children.get(i).miniMax(playerId));
			}
		}
		return utility;
	}

	/**
	 * evaluates the current game situation
	 * 
	 * @param playerId
	 *            - id of the player for whom the situation is evaluated.
	 * @return evaluated value of game situation for the player with playerId
	 */
	private int evaluateUtility(String playerId) {
		PlayerStats pls = board.getPlayerStats();
		Map<String, Integer> map = pls.getMap();
		int maxCount = pls.getMaxCount();

		if (map.get("p1") != null && map.get("p2") != null) {
			if (map.get("p1") == map.get("p2")) // on draw returns 0
				return 0;
			// if both players have conquered boxes, return difference
			if (playerId.equals("p1"))
				return map.get(playerId) - map.get("p2");
			else
				return map.get(playerId) - map.get("p1");

		}
		return maxCount * (pls.getPlayer().equals(playerId) ? 1 : -1);
	}

	public String getTurn() {
		return turn;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}
}
