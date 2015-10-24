package entity;

public class Edge extends Entity{
	
	

	private int ID;

	private boolean selected, vertical;
	
	public Edge(int ID, boolean vertical) {
		this.ID = ID;
		selected = false;
		this.vertical = vertical;
	}
	
	public boolean isVertical(){
		return vertical;
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
