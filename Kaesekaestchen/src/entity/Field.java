package entity;

public class Field extends Entity{
	
	private int ID;
	private Player owner;
	private int edgeAmount;
	
	public Field(int ID) {
		ID = this.ID;
		edgeAmount = 0;
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean increment(Player markingPlayer) {
		if (++edgeAmount == 4) {
			this.owner = markingPlayer;
			return true;
		}
		return false;
	}
	
	public int getEdgeAmount() {
		return edgeAmount;
	}
	
	public boolean isOwned(){
		return getEdgeAmount() == 4;
	}

}
