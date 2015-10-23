package entity;

public class Edge extends Entity{
	
	

	private int ID;

	private boolean selected;
	
	public Edge(int ID) {
		this.ID = ID;
		selected = false;
	}
	@Override
	public String toString(){
		return "";
		
	}
	

	public int getID() {
		return ID;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected() {
		this.selected = true;
	}

}
