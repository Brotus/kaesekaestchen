package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.test;

import static org.junit.Assert.*;

import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.EmptyStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.FloodingStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Game;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.PlayerHandle;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement.PrivilegedAchievement;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.achievement.SurvivorAchievement;

public class PrivilegedAchievementTest {

	Map TestMap;
	
	@Before
	public void setUp() {
		Game.players = new PlayerHandle(2);
		Game.players.set(AllTests.defaultPlayer, 0);
		Game.players.set(AllTests.otherPlayer, 1);
		TestMap = AllTests.mapSetup(2, 2, new int[] {0,1,2,4,5,6,7,9,10,11}, new EmptyStrategy(), 0, new Observer[]{ new PrivilegedAchievement()});
	}

	
	@Test
	public void test() {
		TestMap.plot();
		TestMap.markEdge(3, AllTests.otherPlayer);
		TestMap.plot();
	}

}
