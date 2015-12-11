package tud.game;

public class Player {

	protected String name;

	protected String playerName;
	protected boolean playerAI;
	private int playerId;
	private static int count = 1;
	{
		playerName = "p" + count;
		playerId = count;
		count++;
	}

	public Player(String name) {
		this.name = name;
		this.playerAI = false;
	}

	public String getName() {
		return name;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerId() {
		return playerId;
	}
}
