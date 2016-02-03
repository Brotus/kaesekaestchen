package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Edge;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Player;

/**
 * This is an implementation of the Chinese Wall fancy event. If it gets
 * triggered, it marks all edges in the row or column of the row or column the
 * marked edge was placed (respectively).
 */
public class ChineseWallStrategy implements FancyHandle {

	@Override
	public int action(Map gameMap, Player markingPlayer) {
		// id of the edge causing the action
		int fancyID = gameMap.getFancyId();
		// amount of columns
		int col = gameMap.getColumns();
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
		int edgeIndexDiff = 2 * col +1;
		// another index variable needed to count some rows
		int i = fancyID;

		if (vertical) {
			// these two loops look unnecessary at first sight but it's a way of
			// avoiding to mark fancyID without a more complicated algorithm
			// mark every edge below
			i = fancyID + edgeIndexDiff;
			while (i < ec) {
				edges[i].setMarked(true);
				// add points if fields have been closed
				if (gameMap.removeUnmarkedIndex(i)) {
					closedFields += gameMap.countMarkedFields(i, markingPlayer, true);
				} 
				i += edgeIndexDiff;
			}
			// and every edge above
			i = fancyID - edgeIndexDiff;
			while (i >= 0) {
				edges[i].setMarked(true);
				// add points if fields have been closed
				if (gameMap.removeUnmarkedIndex(i)) {
					closedFields += gameMap.countMarkedFields(i, markingPlayer, true);
				}

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
					// and mark them
					edges[k].setMarked(true);
					// add points if fields have been closed
					if (gameMap.removeUnmarkedIndex(k)) {
						closedFields += gameMap.countMarkedFields(k, markingPlayer, true);
					}
				}
			}
		}

		return closedFields;
	}

}
