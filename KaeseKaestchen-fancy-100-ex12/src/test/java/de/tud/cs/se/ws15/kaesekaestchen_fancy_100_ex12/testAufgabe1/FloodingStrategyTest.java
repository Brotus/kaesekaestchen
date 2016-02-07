package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.testAufgabe1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.FloodingStrategy;

public class FloodingStrategyTest {

	Map TestMap;
	Map FloodingMap;
	FloodingStrategy flooding; 

	@Before
	public void setUp() {
		flooding = new FloodingStrategy();
	}
	
	@Test
	public void testHorizontal() {
		FloodingMap = AllTests.mapSetup(3, 3, new int[]{7}, flooding, 8);
		FloodingMap.markEdge(8, AllTests.otherPlayer);
		TestMap =  AllTests.mapSetup(3, 3, new int[] {8});
		assertEquals(FloodingMap.getUnmarkedEdges(),TestMap.getUnmarkedEdges());
	}
	
	@Test
	public void testVertical() {
		FloodingMap = AllTests.mapSetup(3, 3, new int[]{5}, flooding, 12);
		FloodingMap.markEdge(12, AllTests.otherPlayer);
		TestMap =  AllTests.mapSetup(3, 3, new int[] {12});
		assertEquals(FloodingMap.getUnmarkedEdges(),TestMap.getUnmarkedEdges());
	}

}
