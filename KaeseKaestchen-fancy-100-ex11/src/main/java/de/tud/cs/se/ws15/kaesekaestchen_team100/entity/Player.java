package de.tud.cs.se.ws15.kaesekaestchen_team100.entity;

import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.AI.AI;


/**
 * This class is used to model both players who play the game.
 *
 */
public class Player extends Entity {

	private AI ai;
	private boolean human;

	/**
	 * The name of the player. Will be displayed in console messages.
	 */
	private String name;
	/**
	 * The amount of fields the player owns.
	 */
	private int ownedFields = 0;

	/**
	 * Create a new player with a name and an id (inherited from Entity)
	 * @param name name of the player
	 * @param id id of the player
	 * @param ai used ai
	 * @param human whether the player is human or ai
	 */
	public Player(String name, int id, AI ai, boolean human) {
		this.name = name;
		this.id = id;
		this.ai = ai;
		this.human = human;
	}

	public int getOwnedFields() {
		return ownedFields;
	}

	public String getName() {
		return name;
	}

	/**
	 * This method is used to make it transparent which field belongs to whom on
	 * the map. This is necessary because the entered names of the players are
	 * to long to be displayed in the console and prefixes of the names of the
	 * players might be equal. "P1" is the Player who entered his name first,
	 * the other one will be represented by "P2".
	 * 
	 * @return "P" + id
	 */
	public String getStrId() {
		return "P" + id;
	}

	/**
	 * Increases the amount of fields the player receives.
	 * @param n the amount of fields
	 */
	public void increaseOwnedFields(int n){
		if (0 < n) {
			ownedFields = ownedFields + n;
		}
	}
	
	public boolean isHuman(){
		return human;
	}
	
	public int getTurn(){
		return ai.suggestTurn();
		
	}

}
