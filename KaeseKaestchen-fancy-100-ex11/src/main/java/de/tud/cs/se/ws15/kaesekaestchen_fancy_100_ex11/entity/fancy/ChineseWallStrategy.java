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
		int ec = gameMap.getEdgeCount();
		int closedFields = 0;
		int edgeIndexDiff = col + row;
		int i = fancyID;
		
		while (i < ec){
			i += edgeIndexDiff;
			edges[i].setMarked();
			if(gameMap.removeUnmarkedIndex(i)){
				closedFields += gameMap.countMarkedFields(i, markingPlayer);
			}
		}
		i = fancyID;
		while(i >= 0){
			i -= edgeIndexDiff;
			edges[i].setMarked();
			if(gameMap.removeUnmarkedIndex(i)){
				closedFields += gameMap.countMarkedFields(i, markingPlayer);
			}
		}
		
		return closedFields;
	}

}
