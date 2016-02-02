package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Field;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EarthQuakeStrategy;

public class EarthQuakeTest {

	Map testMap;
	Map earthQuakeMap;
	EarthQuakeStrategy earthQuake;

	@Before
	public void setUp() {
		testMap = AllTests.mapSetup(3, 3, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		earthQuakeMap = AllTests.mapSetup(3, 3, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		earthQuake = new EarthQuakeStrategy() {
		};
		for (int edge : new int[] { 10, 11, 12, 13, 14, 15, 16 }) {
			testMap.markEdge(edge, AllTests.otherPlayer);
			earthQuakeMap.markEdge(edge, AllTests.otherPlayer);
		}

		earthQuake.action(earthQuakeMap, AllTests.otherPlayer);

	}

	@Test
	public void test() {
//		System.out.println("before earthquake");
//		testMap.plot();
//		System.out.println("after earthquake");
//		earthQuakeMap.plot();
	}

	@Test
	public void amountOfMarkings() {
		assertEquals(earthQuakeMap.getUnmarkedEdges().size(), testMap.getUnmarkedEdges().size());
//		counting marked Fields
		int counter = 0;
		for (Field f : earthQuakeMap.getFieldArray()) {
			if (f.hasBeenOwned()){
				counter++;
			}
		}
//		There should be more or equally many Fields with an owner as before the EartQuake
		assertTrue(counter >= 6);
		}

}
