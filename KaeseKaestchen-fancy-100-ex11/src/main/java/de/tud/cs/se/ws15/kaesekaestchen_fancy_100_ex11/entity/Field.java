package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity;

/**
 * This is a Field, edgeAmount is the number of marked neighbor Edges and if all
 * edges are marked owner is set to the Player owning this Field from then on.
 *
 */

public class Field extends Entity{

	/**
	 * The player that owns this field. Can be null.
	 */
	private Player owner;
	/**
	 * The amount of edges of this field that are currently marked. Some value
	 * from 0 (incl) to 4 (incl)
	 */
	private int edgeAmount;

	/**
	 * Creating a Field with given ID
	 * 
	 * @param id
	 */
	Field(int id) {
		this.id = id;
		edgeAmount = 0;
	}

	/**
	 * @return The current owner of this field or null if it is not yet owned
	 */
	Player getOwner() {
		return owner;
	}

	/**
	 * This increments the number of marked neighbor Edges and sets the owner to
	 * marking Player if all edges are marked.
	 * 
	 * @param markingPlayer
	 * @return true if owner was set
	 */
	boolean increment(Player markingPlayer) {
		if (++edgeAmount == 4) {
			this.owner = markingPlayer;
			return true;
		}
		return false;
	}

	/**
	 * @return true if this is hold by one of the players.
	 */
	boolean isOwned() {
		return edgeAmount == 4;
	}

	public Field copy() {
		Field newField = new Field(id);
		newField.edgeAmount = this.edgeAmount;
		return newField;
	}

}
