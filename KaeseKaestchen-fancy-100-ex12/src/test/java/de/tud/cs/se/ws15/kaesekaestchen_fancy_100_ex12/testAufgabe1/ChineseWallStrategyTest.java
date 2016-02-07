package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.testAufgabe1;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.ChineseWallStrategy;

public class ChineseWallStrategyTest {
	Map testMap;
	Map chineseMap;
	ChineseWallStrategy chineseStrategy;

	@Before
	public void setUp() {
		chineseStrategy = new ChineseWallStrategy();
	}

	@Test
	public void testHorizontal() {
		chineseMap = AllTests.mapSetup(6, 3, new int[] { 0,6,7,15,17 }, chineseStrategy, 16);
		chineseMap.markEdge(16, AllTests.otherPlayer);
		testMap = AllTests.mapSetup(6, 3, new int[] { 0,6,7,13,14,15,16,17,18});
	
		assertEquals(chineseMap.getUnmarkedEdges(), testMap.getUnmarkedEdges());
	}

	@Test
	public void testVertical() {
		chineseMap = AllTests.mapSetup(3, 6, new int[] { 2,6,9,19,33 }, chineseStrategy, 26);
		chineseMap.markEdge(26, AllTests.otherPlayer);
		testMap = AllTests.mapSetup(3, 6, new int[] { 2,5,6,9,12,19,26,33,40 });
		assertEquals(chineseMap.getUnmarkedEdges(), testMap.getUnmarkedEdges());
	}
	

}
