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
	Board smallBoard;
	
	@Before 
	public void setUp() {
		this.basicBoard = new Board(4);
		this.basicPlayer = new Player("basic");
		this.advancedAi = new AdvancedAI();
		this.smallBoard = new Board(2);
	}
	
	@Test
	public void selectsFirstBest() {
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
		
		smallBoard.nextAction(1, "basic");
		assertEquals(6, AdvancedAI.getMaxDepth(smallBoard));
		Board square = new Board(1);
		square.nextAction(1, "basic");
		assertEquals(12, AdvancedAI.getMaxDepth(square));
	}
	
	@Test
	public void selectsBestSimple() {
		int[] edges = new int[]{1,5,6};
		for (int e : edges) {
			basicBoard.nextAction(e, "foo");
		}
		assertEquals("10",advancedAi.getNextTurn(basicBoard));
	}
	
	@Test
	public void selectBestAdvanced() {	
		int[] edges = new int[]{1,2,3,5,8,10,12};
		for (int e : edges) {
			smallBoard.nextAction(e, "foo");
		}
		assertEquals("11",advancedAi.getNextTurn(smallBoard));
	}
	
	@Test
	public void advancedStrategyTest() {
		int[] edges = new int[]{1,2,3,4,5,11,12,13,14,18,19,20,21,27,28,29,30,31,32,37,38,39,40};
		for (int e : edges) {
			basicBoard.nextAction(e, "foo");
		}
		String sug = advancedAi.getNextTurn(basicBoard);
		assertTrue(sug.equals("32") || sug.equals("33") || sug.equals("34")|| sug.equals("35")|| sug.equals("36"));
	}
	
	
	
}
