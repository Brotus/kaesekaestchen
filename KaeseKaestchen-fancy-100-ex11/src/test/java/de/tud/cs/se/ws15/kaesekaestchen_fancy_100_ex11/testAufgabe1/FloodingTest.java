package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.ChineseWallStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EarthQuakeStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FloodingStrategy;

public class FloodingTest {

	
	Map FloodingMap;
	ChineseWallStrategy flooding; 

	@Before
	public void setUp() {
		FloodingMap = AllTests.mapSetup(3, 3, new int[]{0,1,2,3,4,5,6,7,8,9});
		flooding = new ChineseWallStrategy() {};
		for (int edge : new int[] { 10, 11, 12, 13, 14, 15, 16 }) {
			FloodingMap.markEdge(edge, AllTests.otherPlayer);
		}
		}
		
	
	
	@Test
	public void test() {
		System.out.println("before flooding");
		FloodingMap.plot();
		flooding.action(FloodingMap, AllTests.otherPlayer);
	System.out.println("after flooding");
	FloodingMap.plot();
	}

}
