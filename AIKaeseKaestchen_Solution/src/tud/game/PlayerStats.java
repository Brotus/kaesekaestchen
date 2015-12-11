package tud.game;

import java.util.Map;
import java.util.TreeMap;
/**
 * data class to save max conquered boxes, one of the winning players and the player statistics
 * @author Admin
 *
 */
public class PlayerStats {

	Map<String, Integer> map = new TreeMap<String, Integer>();
	public Map<String, Integer> getMap() {
		return map;
	}

	public String getPlayer() {
		return player;
	}

	public int getMaxCount() {
		return maxCount;
	}

	String player;
	int maxCount;

	public PlayerStats(Map<String, Integer> map, String player, int maxCount) {
		this.map = map;
		this.player = player;
		this.maxCount = maxCount;
	}

}
