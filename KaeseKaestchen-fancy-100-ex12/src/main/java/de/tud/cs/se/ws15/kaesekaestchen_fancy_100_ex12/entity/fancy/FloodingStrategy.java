package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Edge;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game.Map;

/**
 * Implementation of Flooding. This event unmarks all edges in the row/column of
 * the fancy edge.
 */
public class FloodingStrategy implements FancyHandle {

	@Override
	public int action(Map gameMap, Player markingPlayer) {
		System.out.println("A devastating flood washes over the map");
		// id of the edge causing the action
		int fancyID = gameMap.getFancyId();
		// amount of columns
		int col = gameMap.getColumns();
		// amount of rows
		int row = gameMap.getRows();
		// all edges of the map
		Edge[] edges = gameMap.getEdges();
		// whether or not the edge causing the action is vertical
		boolean vertical = edges[fancyID].isVertical();
		// the total amount of edges
		int ec = gameMap.getEdgeCount();
		// the amount of fields the player closed with this action
		int closedFields = 0;
		// if some edge has id x, the next edge directly below has id x +
		// edgeIndexDiff
		int edgeIndexDiff = col + row + 1;
		// another index variable needed to count some rows
		int i = fancyID;

		if (vertical) {
			// these two loops look unnecessary at first sight but it's a way of
			// avoiding to mark fancyID without a more complicated algorithm
			// mark every edge below
			i = fancyID + edgeIndexDiff;
			while (i < ec) {
				gameMap.undo(edges[i].getId());
				i += edgeIndexDiff;
			}
			// and every edge above
			i = fancyID - edgeIndexDiff;
			while (i >= 0) {
				gameMap.undo(edges[i].getId());
				i -= edgeIndexDiff;
			}

		} else {
			// let j point to the last row but the same column
			int j = i;
			while (j + edgeIndexDiff < ec) {
				j += edgeIndexDiff;
			}
			// the columnIndex'th amount of columns is where fancyID is at
			// the leftmost columnIndex is 0, the rightmost col-1
			int columnIndex = col - (ec - j);
			// the index of the leftmost edge in this row
			int leftIndex = i - columnIndex;
			// iterate over every edge in this row except for the one causing
			// the fancy action
			for (int k = leftIndex; k < leftIndex + col; k++) {
				if (k != fancyID) {
					gameMap.undo(edges[k].getId());
				}
			}
		}

		return closedFields;
	}

}
