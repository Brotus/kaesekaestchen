package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI.AdvancedAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EmptyStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FancyHandle;

/**
 *  Since we have found mistakes in our code, we decided to test the
 *             reference Implementation.
 */
public class OurAdvancedAITest {

	Map simpleSetUp;
	AdvancedAI simpleAISetUp;
	Player somePlayer;
	FancyHandle handle;

	@Before
	public void setUp() {
		this.handle = new EmptyStrategy();
		this.simpleSetUp = new Map(2, 2, handle);
		this.simpleAISetUp = new AdvancedAI(simpleSetUp);
		this.somePlayer = new Player("Timmy", 0, null, true);
	}

	@Test
	public void noRatings() {
		assertEquals(0, simpleAISetUp.rate(new LinkedList<Integer>(), 0,
				simpleSetUp, 2, 2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalEdgeTest() {
		simpleSetUp.markEdge(0, somePlayer);
		simpleAISetUp.rate(new LinkedList<Integer>(), 0, simpleSetUp, 2, 2);
	}

	@Test
	public void gettingOnePointsTest() {
		int[] edges = new int[] { 0, 2, 3 };
		for (int e : edges) {
			simpleSetUp.markEdge(e, somePlayer);
		}
		assertEquals(5, simpleAISetUp.suggestTurn());
		assertEquals(1, simpleAISetUp.rate(new LinkedList<Integer>(), 5,
				simpleSetUp, 2, 2));
	}

	@Test
	public void gettingTwoPointsTest() {
		int[] edges = new int[] { 0, 1, 2, 4, 5, 6 };
		for (int e : edges) {
			simpleSetUp.markEdge(e, somePlayer);
		}
		assertEquals(3, simpleAISetUp.suggestTurn());
		assertEquals(2, simpleAISetUp.rate(new LinkedList<Integer>(), 3,
				simpleSetUp, 2, 2));
	}

	@Test(timeout = 10000)
	public void selectBestAdvanced() {
		int[] edges = new int[] { 0, 1, 2, 4, 7, 9, 11 };
		for (int e : edges) {
			simpleSetUp.markEdge(e, somePlayer);
		}
		assertEquals(10, simpleAISetUp.suggestTurn());
	}

	@Test(timeout = 30000)
	public void strategyTest() {
		Map largerSetUp = new Map(4, 4, handle);
		int[] edges = new int[] { 1, 2, 3, 4, 5, 11, 12, 13, 14, 18, 19, 20,
				21, 27, 28, 29, 30, 31, 37, 38, 39, 40 };
		for (int e : edges) {
			largerSetUp.markEdge(e - 1, somePlayer);
		}
		AdvancedAI localAI = new AdvancedAI(largerSetUp);
		int sug = localAI.suggestTurn();
		assertEquals(32, sug + 1);
	}

	@Test(timeout = 10000)
	public void strategyTest2() {
		Map largerSetUp = new Map(3, 3, handle);
		int[] edges = new int[] { 1, 2, 3, 4, 9, 10, 11, 15, 16, 17, 22, 23, 24 };
		for (int e : edges) {
			largerSetUp.markEdge(e - 1, somePlayer);
		}
		AdvancedAI localAI = new AdvancedAI(largerSetUp);
		int sug = localAI.suggestTurn();
		assertEquals(21, sug + 1);
	}
	
	

}
