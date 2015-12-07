package testAufgabe1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tud.game.Board;
import tud.game.Player;
import tug.game.ai.AdvancedAI;

public class TudAdvancedAITest {

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
	public void adaptiveTreeDepth() {
		Board bigBoard = new Board(5);
		assertEquals(2, AdvancedAI.getMaxDepth(bigBoard));
		bigBoard.nextAction(1, "basic");
		assertEquals(3, AdvancedAI.getMaxDepth(bigBoard));
		Board mediumBoard = new Board(3);
		mediumBoard.nextAction(1, "basic");
		assertEquals(4, AdvancedAI.getMaxDepth(mediumBoard));
		Board smallBoard = new Board(2);
		smallBoard.nextAction(1, "basic");
		assertEquals(6, AdvancedAI.getMaxDepth(smallBoard));
		Board square = new Board(1);
		square.nextAction(1, "basic");
		assertEquals(12, AdvancedAI.getMaxDepth(square));
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
