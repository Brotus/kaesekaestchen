package entity;

/**
 * This is a Field, edgeAmount is the number of marked neighbor Edges and if all
 * edges are marked owner is set to the Player owning this Field from then on.
 * 
 * @author paddy
 *
 */

public class Field extends Entity {

	private Player owner;
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

}
