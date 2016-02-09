package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.test;

import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.FloodingStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement.SurvivorAchievement;

public class SurvivorAchievementTest {
	
	private Map map;

	@Before
	public void setUp() {
		map = AllTests.mapSetup(3, 2, new int[] {1,2,3,4,5,6,7,8,9,10,12,13,16}, new FloodingStrategy(), 0, new Observer[]{ new SurvivorAchievement()});
	}
	
	@Test
	public void testUpdateGameEnd(){
		map.markEdge(0, AllTests.defaultPlayer);
		map.markEdge(11, AllTests.defaultPlayer);
		map.markEdge(14, AllTests.otherPlayer);
		map.markEdge(15, AllTests.otherPlayer);
	}

}
