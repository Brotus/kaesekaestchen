package entity;
/**
 * This is a vertical or horizontal Edge entity that can be marked throughout the Game.
 * @author paddy
 *
 */
public class Edge extends Entity {

	private boolean selected, vertical;

	Edge(int id, boolean vertical) {
		this.id = id;
		selected = false;
		this.vertical = vertical;
	}

	boolean isVertical() {
		return vertical;
	}

	int getId() {
		return id;
	}

	boolean isSelected() {
		return selected;
	}

	void setSelected() {
		this.selected = true;
	}

}
