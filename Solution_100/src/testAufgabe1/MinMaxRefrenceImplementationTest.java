package testAufgabe1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tud.game.Board;
import tud.game.Player;
import tug.game.ai.AdvancedAI;

public class MinMaxRefrenceImplementationTest {

	Board basicBoard;
	Player basicPlayer;
	AdvancedAI advancedAi;
	
	@Before 
	public void setUp() {
		this.basicBoard = new Board(2);
		this.basicPlayer = new Player("basic");
		this.advancedAi = new AdvancedAI();
	}
	
	@Test
	public void selectsEdge() {
		assertEquals("1",advancedAi.getNextTurn(basicBoard));
	}
	
	@Test
	public void selectsBest() {
		int[] edges = new int[]{1,3,4};
		for (int e : edges) {
			basicBoard.nextAction(e, "foo");
		}
		assertEquals("6",advancedAi.getNextTurn(basicBoard));
	}
}
