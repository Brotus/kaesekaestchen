package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity;
/**
 * This is a vertical or horizontal Edge entity that can be marked throughout the Game.
 *
 */
public class Edge extends Entity {

	/**
	 * An Edge is marked iff a Player chose to mark it.
	 */
	private boolean marked, 
	/**
	 * vertical describes the alignment in the map
	 */
	vertical;

	/**
	 * Initializes an unmarked edge
	 * @param id the id of the edge (inherited from Entity)
	 * @param vertical whether the edge is aligned vertically or horizontally
	 */
	Edge(int id, boolean vertical) {
		this.id = id;
		marked = false;
		this.vertical = vertical;
	}

	boolean isVertical() {
		return vertical;
	}

	public int getId() {
		return id;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked() {
		this.marked = true;
	}
	
	public Edge copy() {
		Edge newEdge = new Edge(id, vertical);
		newEdge.marked = this.marked;
		return newEdge;
	}

}
