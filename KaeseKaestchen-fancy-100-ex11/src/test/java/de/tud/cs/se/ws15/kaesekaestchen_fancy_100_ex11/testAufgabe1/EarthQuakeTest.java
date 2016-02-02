package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EarthQuakeStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FancyHandle;

public class EarthQuakeTest {
	
	Map testMap;
	EarthQuakeStrategy earthQuake; 

	@Before
	public void setUp() {
		testMap = AllTests.mapSetup(3, 3, new int[]{0,1,2,3,4,5,6,7,8,9});
		earthQuake = new EarthQuakeStrategy() {
		}; 
		
	}
	@Test
	public void test() {
		System.out.println("before earthquake");
		testMap.plot();
		earthQuake.action(testMap, AllTests.otherPlayer);
	System.out.println("after earthquake");
	testMap.plot();
	}

}
