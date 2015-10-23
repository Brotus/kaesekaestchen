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
	
	public void increment() {
		edgeAmount++;
	}
	
	public int getEdgeAmount() {
		return edgeAmount;
	}

	@Override
	public String toString(){
		return "";
		
	}

}
