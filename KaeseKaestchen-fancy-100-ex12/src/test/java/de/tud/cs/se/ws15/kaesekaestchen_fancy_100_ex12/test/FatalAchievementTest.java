package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.test;

import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.EmptyStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Game;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.PlayerHandle;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement.FatalAchievement;

public class FatalAchievementTest {

	Map map;
	@Before
	public void setUp() throws Exception {
		Game.players = new PlayerHandle(2);
		Game.players.set(AllTests.defaultPlayer, 0);
		Game.players.set(AllTests.otherPlayer, 1);
		map = AllTests.mapSetup(2, 2, new int[] {0,1,2,3,4,7,8,9}, new EmptyStrategy(), 0, new Observer[]{ new FatalAchievement()});
	}

	@Test
	public void test() {
		map.plot();
		for (int edge : new int[]{5,6})
			map.markEdge(edge, AllTests.otherPlayer);
		map.plot();
		for (int edge : new int[]{10, 11})
			map.markEdge(edge, AllTests.otherPlayer);
		map.plot();
	}

//	@Test
//	public void test2() {
//		map.plot();
//		for (int edge : new int[]{5,6})
//			map.markEdge(edge, AllTests.defaultPlayer);
//		map.plot();
//		for (int edge : new int[]{10, 11})
//			map.markEdge(edge, AllTests.otherPlayer);
//		map.plot();
//	}

}
