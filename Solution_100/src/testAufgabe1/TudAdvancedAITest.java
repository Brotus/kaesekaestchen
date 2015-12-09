package testAufgabe1;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import tud.game.Board;
import tug.game.ai.AdvancedAI;

public class TudAdvancedAITest {

	Board smallBoard;
	Board basicBoard;
	AdvancedAI advancedAi = new AdvancedAI();

	@Before
	public void setUp() {
		this.smallBoard = new Board(2);
		this.basicBoard = new Board(3);
	}

	@Test
	public void adaptiveTreeDepth() {
		Board bigBoard = new Board(5);
		assertEquals(2, AdvancedAI.getMaxDepth(bigBoard));
		bigBoard.nextAction(1, "basic");
		assertEquals(3, AdvancedAI.getMaxDepth(bigBoard));
		basicBoard.nextAction(1, "basic");
		assertEquals(4, AdvancedAI.getMaxDepth(basicBoard));
		smallBoard.nextAction(1, "basic");
		assertEquals(6, AdvancedAI.getMaxDepth(smallBoard));
		Board square = new Board(1);
		square.nextAction(1, "basic");
		assertEquals(12, AdvancedAI.getMaxDepth(square));
	}

	@Test
	public void selectsFirstBest() {
		assertEquals("1", advancedAi.getNextTurn(basicBoard));
	}

	@Test
	public void selectsBestSimple() {
		int[] edges = new int[] { 1, 3, 4 };
		for (int e : edges) {
			smallBoard.nextAction(e, "foo");
		}
		assertEquals("6", advancedAi.getNextTurn(smallBoard));
	}

	@Test
	public void selectBestAdvanced() {
		int[] edges = new int[] { 1, 2, 3, 5, 8, 10, 12 };
		for (int e : edges) {
			smallBoard.nextAction(e, "foo");
		}
		assertEquals("11", advancedAi.getNextTurn(smallBoard));
	}

	@Test(timeout = 10000)
	public void advancedStrategyTest() {
		int[] edges = new int[] { 1, 2, 3, 4, 9, 10, 11, 15, 16, 17, 22, 23, 24 };
		for (int e : edges) {
			basicBoard.nextAction(e, "foo");
		}
		String sug = advancedAi.getNextTurn(basicBoard);
		assertEquals("18", sug);
	}

	@Test(timeout = 10000)
	public void advancedStrategyTestLarge() {
		int[] edges = new int[] { 1, 2, 3, 4, 5, 11, 12, 13, 14, 18, 19, 20,
				21, 27, 28, 29, 30, 31, 37, 38, 39, 40 };
		Board mediumBoard = new Board(4);
		for (int e : edges) {
			mediumBoard.nextAction(e, "foo");
		}
		String sug = advancedAi.getNextTurn(mediumBoard);
		assertEquals("32", sug);
	}

}
