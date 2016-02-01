package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Edge;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Player;

public class ChineseWallStrategy implements FancyHandle {

	@Override
	public int action(Map gameMap, Player markingPlayer) {
		int fancyID = gameMap.getFancyId();
		int col = gameMap.getColumns();
		int row = gameMap.getRows();
		Edge[] edges = gameMap.getEdges();
		boolean vertical = edges[fancyID].isVertical();
		int ec = gameMap.getEdgeCount();
		int closedFields = 0;
		int edgeIndexDiff = col + row + 1;
		int i = fancyID;

		if (vertical) {
			
			i = fancyID + edgeIndexDiff;
			while (i < ec) {
				edges[i].setMarked();
				if (gameMap.removeUnmarkedIndex(i)) {
					closedFields += gameMap.countMarkedFields(i, markingPlayer);
				}
				i += edgeIndexDiff;
			}
			i = fancyID - edgeIndexDiff;
			while (i >= 0) {
				edges[i].setMarked();
				if (gameMap.removeUnmarkedIndex(i)) {
					closedFields += gameMap.countMarkedFields(i, markingPlayer);
				}

				i -= edgeIndexDiff;
			}
		} else {
			int j = i;
			while(j + edgeIndexDiff < ec){
				j += edgeIndexDiff;
			}
			int columnIndex = col - (ec - j);
			int leftIndex = i - columnIndex;
			
			for (int k = leftIndex; k < leftIndex + col; k++){
				if(k != fancyID){
					edges[k].setMarked();
					if (gameMap.removeUnmarkedIndex(i)) {
						closedFields += gameMap.countMarkedFields(i, markingPlayer);
					}
				}
			}
		}

		return closedFields;
	}

}
