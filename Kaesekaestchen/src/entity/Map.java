package entity;

public class Map {

	private int lines;
	private int columns;
	private Edge[] edges;
	private Field[] fields;

	public Map(int lines, int columns) {

		this.lines = lines;
		this.columns = columns;

		this.makeEdges();
		this.makeFields();
	}

	/**
	 * This initializes the fields array
	 */
	private void makeFields() {
		int nOfFields = lines * columns;
		fields = new Field[nOfFields];
		for (int i = 0; i < nOfFields; i++) {
			fields[i] = new Field(i);
		}
	}

	/**
	 * This initializes the edges array
	 */
	private void makeEdges() {
		int nOfEdges = lines + columns + 2 * lines * columns;
		edges = new Edge[nOfEdges];
		int c = 0;
		boolean vertical = false;
		for (int i = 0; i < nOfEdges; i++) {
			edges[i] = new Edge(i, vertical);
			if(!vertical && c == columns -1){
				vertical = true;
				c= 0;
			} else if(vertical && c == columns){
				vertical = false;
				c= 0;
			} else {
				c++;
			}			
		}
	}

	public String toString() {
		return "ab";
	}

//	/**
//	 * This uses hashFunction to set the Nghbs of all Edges
//	 */
//	private void setFieldRefs() {
//		for (Edge e : this.edges) {
//			Field[] nghbs = new Field[2];
//			int c = 0;
//			for (int i : 
//				this.hashFunction(e.getID())
//				) {
//				if (i != -1) {
//					nghbs[c] = fields[i];
//					c++;
//				}
				
//			}
//		}
	
	
	/**
	 * This marks an  edge of a given ID and if it is not marked yet. In this case this method returns false
	 * @param edgeID
	 * @return
	 */
	public FieldStates markEdge(int edgeID, Player markingPlayer) {
		if (edges[edgeID].isSelected()) {
			return FieldStates.INVALID;
		}
		
		edges[edgeID].setSelected();
		// counting marked Fields
		int c = 0;
		for (int fieldID : this.hashFunction(edgeID)) {			
			if (fieldID != -1 && fields[fieldID].increment(markingPlayer)){
					c++;
			}
		}
		
		if ( c==0) {
			return FieldStates.MARKED;
		} else if (c==1) {
			return FieldStates.ONE;
		} else {
			return FieldStates.TWO;
		}
	}

	/**
	 * Basic print of the map of entities to the console
	 */
	public void plot() {
		int edgep = 0;
		int fieldp = 0;

		for (int linep = 0; linep < lines * 2 + 1; linep++) {
			for (int colp = 0; colp < columns * 2 + 1; colp++) {
				if (colp % 2 == 1 && linep % 2 == 1) {
					System.out.print("\t[" + fieldp++ + "]");
				} else if (colp % 2 == 1 || linep % 2 == 1) {
					System.out.print("\t" + edgep++);
					//System.out.print("\t" + (edges[edgep++].isVertical() ? "|" : "-"));
				} else {
					System.out.print("\t *");
				}
			}

			System.out.println();
		}

	}

	/**
	 * This maps the edgeID to an array of two integers that mark the matching
	 * field entities, where -1 means there is none for this neighbor
	 * 
	 * @param edgeID
	 * @return
	 */
	private int[] hashFunction(int edgeID) {
		int[] result = new int[2];
		int edgesPerLine = this.columns * 2 + 1;

		// applying 1st function
		if ((edgeID + columns +1) % edgesPerLine == 0) {
			result[0] = -1;
		} else {
			result[0] = edgeID - Math.floorDiv(edgeID + 5, edgesPerLine)
					* (columns + 1);
		}

		// applying 2nd function
		if ((edgeID + 1) % edgesPerLine == 0) {
			result[1] = -1;
		} else {
			result[1] = edgeID - columns - Math.floorDiv(edgeID, edgesPerLine)
					* (columns + 1);
		}

		// removing fields out of boundaries
		for (int i = 0; i < 2; i++) {
			if (result[i] < -1 || result[i] >= columns * lines) {
				result[i] = -1;
			}
		}
		
		return result;
	}
}
