package testAufgabe1;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import entity.Map;
import entity.AI.AdvancedAI;

public class MinMaxTest {
	
	Map simpleSetUp;

	@Before
	public void setUp() {
		this.simpleSetUp = new Map(2, 2);
	}
	
	@Test
	public void constructorTest() {
		AdvancedAI AI = new AdvancedAI(simpleSetUp);
		assertEquals(AI.rate(new LinkedList<Integer>(), simpleSetUp.getEdges()[0].getId(), simpleSetUp, 2, 2), 0);
	}
}
