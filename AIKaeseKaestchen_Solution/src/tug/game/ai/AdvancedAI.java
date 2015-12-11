package tug.game.ai;

import tud.game.Board;

public class AdvancedAI extends PlayerAI {

	public AdvancedAI() {
		super("Advanced AI");
	}

	/**
	 * returns turn of the advanced AI
	 * 
	 * @param board
	 *            - current game board
	 * @return turn of the advanced AI
	 */
	@Override
	public String getNextTurn(Board board) {
		return supportPlayer(board, "p2");
	}

	/**
	 * returns turn of MINIMAX logic
	 * 
	 * @param board
	 *            - current game board
	 * @return turn of MINIMAX logic
	 */
	public static String supportPlayer(Board board, String player) {
		Node gameTreeRoot = new Node(board, MiniMax.MAX, player, 0,
				getMaxDepth(board), null);
		String turn = gameTreeRoot.getChildren().get(0).getTurn();
		int utility = gameTreeRoot.getChildren().get(0).miniMax(player);

		for (Node child : gameTreeRoot.getChildren()) {
			int newUtility = child.miniMax(player);
			if (newUtility > utility) {
				utility = newUtility;
				turn = child.getTurn();
			}
		}
		return turn;
	}

	/**
	 * decides the max tree depth of the MINIMAX algorithm, based on the number
	 * of free fields
	 * 
	 * @return max tree depth
	 */
	public static int getMaxDepth(Board board) {
		int numberOfFreeFields = board.getNumberOfFreeFields();
		if (numberOfFreeFields < 6) {
			return 12;
		}
		if (numberOfFreeFields < 12) {
			return 6;
		}
		if (numberOfFreeFields < 24) {
			return 4;
		}
		if (numberOfFreeFields < 60) {
			return 3;
		}
		return 2;
	}

}
