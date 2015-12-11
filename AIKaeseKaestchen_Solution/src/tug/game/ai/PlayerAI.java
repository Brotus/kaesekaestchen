package tug.game.ai;

import tud.game.Board;
import tud.game.Player;


public abstract class PlayerAI extends Player {

	public PlayerAI(String name) {
		super(name);
	}

	public abstract String getNextTurn(Board board);

}
