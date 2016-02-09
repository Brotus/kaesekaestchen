package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

public class MapTest {

	private Map simpleMap;

	@Before
	public void setUp() {
		simpleMap = AllTests.mapSetup(1, 1, new int[] { 0, 1, 2, 3 });
	}

	@Test
	public void undoTest() {
		// all edges are marked, we undo Edge 0 which should be marked
		assertTrue(simpleMap.undo(0));
		// The edge is now free to mark
		assertFalse(simpleMap.getEdges()[0].isMarked());
		// The Field is displayed to be owned by the default player
		assertEquals(AllTests.defaultPlayer, simpleMap.getFieldArray()[0].getOwner());
		// But the Field is not owned i.e. can be marked again (see next test)
		assertFalse(simpleMap.getFieldArray()[0].isOwned());
	}

	// This is how remarking an edge impacts
	@Test
	public void undoAndRedoImpactTest() {
		// System.out.println("undoandredo with impact");
		// simpleMap.plot();
		assertEquals(simpleMap.getFieldArray()[0].getOwner(), AllTests.defaultPlayer);
		simpleMap.undo(0);
		// simpleMap.plot();
		assertEquals(simpleMap.getFieldArray()[0].getOwner(), AllTests.defaultPlayer);
		simpleMap.markEdge(0, AllTests.otherPlayer, true);
		// simpleMap.plot();
		assertEquals(AllTests.otherPlayer, simpleMap.getFieldArray()[0].getOwner());
	}

	// This happens if an edge is unmarked and remarked again by a fancy event
	@Test
	public void undoAndRedoNOImpactTest() {
		// System.out.println("undoandredo with no impact");
		// simpleMap.plot();
		assertEquals(AllTests.defaultPlayer, simpleMap.getFieldArray()[0].getOwner());
		simpleMap.undo(0);
		assertEquals(AllTests.defaultPlayer, simpleMap.getFieldArray()[0].getOwner());
		// simpleMap.plot();
		simpleMap.markEdge(0, AllTests.otherPlayer, false);
		// simpleMap.plot();
		assertEquals(AllTests.defaultPlayer, simpleMap.getFieldArray()[0].getOwner());
	}
}
