package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity;
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
	
	private Player owner;

	/**
	 * Initializes an unmarked edge
	 * @param id the id of the edge (inherited from Entity)
	 * @param vertical whether the edge is aligned vertically or horizontally
	 */
	public Edge(int id, boolean vertical) {
		this.id = id;
		this.marked = false;
		this.vertical = vertical;
	}

	public boolean isVertical() {
		return vertical;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(Player markingPlayer) {
		this.marked = true;
		this.owner = markingPlayer;
		if(markingPlayer != null){
			markingPlayer.increment();
		}
	}
	
	public void setUnmarked(){
		if(owner != null){
		this.owner.decrement();
		}
		this.marked = false;
		this.owner = null;
	}
	
	public Edge copy() {
		Edge newEdge = new Edge(id, vertical);
		newEdge.marked = this.marked;
		newEdge.owner = this.owner;
		return newEdge;
	}

}
