package tug.game.ai;

import java.util.Random;

import tud.game.Board;

public class BasicAI extends PlayerAI {

	public BasicAI() {
		super("Basic AI");
	}

	/**
	 * returns turn based on random choice
	 * 
	 * @param board - current game board
	 * @return turn based on random choice
	 */
	@Override
	public String getNextTurn(Board board) {
		Random random = new Random();
		int pos = random.nextInt(board.getBoardValueSize());
		while (!board.IsUsed(pos)) {
			pos = random.nextInt(board.getBoardValueSize());
		}

		return String.valueOf(pos);
	}
}
