package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.testAufgabe1;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Field;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EarthQuakeStrategy;

public class EarthQuakeStrategyTest {

	Map earthQuakeMap;
	EarthQuakeStrategy earthQuake;

	@Before
	public void setUp() {
		earthQuakeMap = AllTests.mapSetup(3, 3, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		earthQuake = new EarthQuakeStrategy();
		for (int edge : new int[] { 10, 11, 12, 13, 14, 15, 16 }) {
			earthQuakeMap.markEdge(edge, AllTests.otherPlayer);
		}

		earthQuake.action(earthQuakeMap, AllTests.otherPlayer);

	}

	@Test
	public void amountOfMarkings() {
		assertEquals(7, earthQuakeMap.getUnmarkedEdges().size());
		// counting marked Fields
		//int cOfOwnedFields = 0;
		int cOfOwnedFieldsByDefaultPlayer = 0;
		for (Field f : earthQuakeMap.getFieldArray()) {
			if (f.hasBeenOwned()) {
				//cOfOwnedFields++;
				if (f.getOwner().equals(AllTests.defaultPlayer)) {
					cOfOwnedFieldsByDefaultPlayer++;
				}
			}
		}
		assertEquals(3, cOfOwnedFieldsByDefaultPlayer);
		// There should be more or equally many Fields with an owner as before
		// the EartQuake
		//assertTrue(cOfOwnedFields >= 6);
	}

}
