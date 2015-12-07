package testAufgabe1;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import entity.Map;
import entity.Player;
import entity.AI.AdvancedAI;

public class MinMaxTest {
	
	Map simpleSetUp;
	AdvancedAI simpleAISetUp;
	Player somePlayer;

	@Before
	public void setUp() {
		this.simpleSetUp = new Map(2, 2);
		this.simpleAISetUp = new AdvancedAI(simpleSetUp);
		this.somePlayer = new Player("Timmy", 0, null, true);
	}
	
	@Test
	public void noRatings() {
		assertEquals(0,simpleAISetUp.rate(new LinkedList<Integer>(), 0, simpleSetUp, 2, 2));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void illegalEdgeTest() {
		simpleSetUp.markEdge(0, somePlayer);
		simpleAISetUp.rate(new LinkedList<Integer>(), 0, simpleSetUp, 2, 2);
	}
	
	@Test
	public void gettingOnePointsTest(){
		int[] edges = new int[]{0,2,3};
		for(int e : edges){
			simpleSetUp.markEdge(e, somePlayer);
		}
		assertEquals(5,simpleAISetUp.suggestTurn());
		assertEquals(1,simpleAISetUp.rate(new LinkedList<Integer>(), 5, simpleSetUp, 2, 2));
	}
	
	@Test
	public void gettingTwoPointsTest(){
		int[] edges = new int[]{0,1,2,4,5,6};
		for(int e : edges){
			simpleSetUp.markEdge(e, somePlayer);
		}
		assertEquals(3,simpleAISetUp.suggestTurn());
		assertEquals(2,simpleAISetUp.rate(new LinkedList<Integer>(), 3, simpleSetUp, 2, 2));
	}
	
}
