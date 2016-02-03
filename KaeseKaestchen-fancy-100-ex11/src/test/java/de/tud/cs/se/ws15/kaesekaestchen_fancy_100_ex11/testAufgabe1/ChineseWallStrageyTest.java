package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.ChineseWallStrategy;

public class ChineseWallStrageyTest {
	Map testMap;
	Map chineseMap;
	ChineseWallStrategy chineseStrategy;

	@Before
	public void setUp() {
		chineseStrategy = new ChineseWallStrategy();
	}

	@Test
	public void testHorizontal() {
		chineseMap = AllTests.mapSetup(3, 3, new int[] { 0,3,4 }, chineseStrategy, 8);
		chineseMap.setFancyVisible();
		chineseMap.plot();
		chineseMap.markEdge(8, AllTests.otherPlayer);
		chineseMap.plot();
		testMap = AllTests.mapSetup(3, 3, new int[] { 0,3,4,7,8,9 });
	
		assertEquals(chineseMap.getUnmarkedEdges(), testMap.getUnmarkedEdges());
	}

	@Test
	public void testVertical() {
		chineseMap = AllTests.mapSetup(3, 3, new int[] { 2,6,9 }, chineseStrategy, 12);
		chineseMap.setFancyVisible();
		chineseMap.plot();
		chineseMap.markEdge(12, AllTests.otherPlayer);
		chineseMap.plot();
		testMap = AllTests.mapSetup(3, 3, new int[] { 2,5,6,12,19 });
		assertEquals(chineseMap.getUnmarkedEdges(), testMap.getUnmarkedEdges());
	}
}
