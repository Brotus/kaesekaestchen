package entity;

public class Field extends Entity {

	private Player owner;
	private int edgeAmount;

	Field(int id) {
		this.id = id;
		edgeAmount = 0;
	}

	Player getOwner() {
		return owner;
	}

	void setOwner(Player owner) {
		this.owner = owner;
	}

	boolean increment(Player markingPlayer) {
		if (++edgeAmount == 4) {
			this.owner = markingPlayer;
			return true;
		}
		return false;
	}

	boolean isOwned() {
		return edgeAmount == 4;
	}

}
