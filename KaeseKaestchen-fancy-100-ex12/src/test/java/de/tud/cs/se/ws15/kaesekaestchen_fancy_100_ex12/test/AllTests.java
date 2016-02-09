package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.test;

import java.util.Observer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.EmptyStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy.FancyHandle;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

@RunWith(Suite.class)
@SuiteClasses({ FancyTests.class, OurAdvancedAITest.class })
public class AllTests {
	
	public static Player defaultPlayer = new Player("DefaultPlayer", 0, null, true);
	public static Player otherPlayer = new Player("other", 1, null, false);
	public static FancyHandle defaultFancy = new EmptyStrategy();

	public static Map mapSetup(int width, int length, int[] edgesAllreadyMarked, FancyHandle fancy) {
		resetPlayers();
		Map map = new Map(length, width, fancy);
		for (int i : edgesAllreadyMarked) {
			map.markEdge(i, defaultPlayer);
		}
		return map;
	}
	
	public static Map mapSetup(int width, int length, int[] edgesAllreadyMarked) {
		return AllTests.mapSetup(width, length, edgesAllreadyMarked, defaultFancy);
	}
	
	public static Map mapSetup(int width, int length, int[] edgesAllreadyMarked, FancyHandle fancy, int fancyEdge) {
		resetPlayers();
		Map map = new Map(length, width, fancy, fancyEdge);
		for (int i : edgesAllreadyMarked) {
			map.markEdge(i, defaultPlayer);
		}
		return map;
	}
	
	public static Map mapSetup(int width, int length, int[] edgesAllreadyMarked, FancyHandle fancy, int fancyEdge, Observer[] achievements) {
		resetPlayers();
		Map map = new Map(length, width, fancy, fancyEdge, achievements);
		for (int i : edgesAllreadyMarked) {
			map.markEdge(i, defaultPlayer);
		}
		return map;
	}
	
	private static void resetPlayers(){
		defaultPlayer = new Player("DefaultPlayer", 0, null, true);
		otherPlayer = new Player("other", 1, null, false);
	}
	
}
