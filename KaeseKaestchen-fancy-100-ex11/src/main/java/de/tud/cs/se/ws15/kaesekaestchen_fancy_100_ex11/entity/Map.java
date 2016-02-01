package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity;

import java.util.LinkedList;
import java.util.Random;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FancyHandle;

/**
 * This Class contains the Entities needed for the game loop, manages their ID's
 * and marks them as owned or selected.
 * 
 * @author paddy
 *
 */
public class Map {

	private int rows;
	private int columns;
	private LinkedList<Integer> unmarkedEdges = new LinkedList<Integer>();
	private Edge[] edges;
	private Field[] fields;
	/** the id of the edge causing fanciness */
	private int fancyID;
	/** true iff the wall causing fancy stuff should be visible */
	private boolean fancyVisible = false;
	
	private boolean anotherTurn;
	
	private FancyHandle fancy;

	/**
	 * Creates a Map.
	 * 
	 * @param rows The number of rows of the Map
	 * @param columns The number of columns of the map
	 */
	public Map(int rows, int columns, FancyHandle fancy) {
		this.rows = rows;
		this.columns = columns;
		this.fancy = fancy;
		
		this.fancyID = generateFancyWallId();

		this.makeEdges();
		this.makeFields();
	}


	/**
	 * This initializes the fields array. The ID of a Field in this Map is equal
	 * to its position in the fields array of this class.
	 */
	private void makeFields() {
		int nOfFields = rows * columns;
		fields = new Field[nOfFields];
		for (int i = 0; i < nOfFields; i++) {
			fields[i] = new Field(i);
		}
	}

	/**
	 * This initializes the edges array and the unmarkedEdges list. The ID of an Edge is equal to it's
	 * position in the edges array of this class. This method also sets if an
	 * edge is horizontal based on the Maps size.
	 */
	private void makeEdges() {
		int nOfEdges = rows + columns + 2 * rows * columns;
		edges = new Edge[nOfEdges];
		int c = 0;
		boolean vertical = false;
		for (int i = 0; i < nOfEdges; i++) {
			unmarkedEdges.add(i);
			edges[i] = new Edge(i, vertical);
			if (!vertical && c == columns - 1) {
				vertical = true;
				c = 0;
			} else if (vertical && c == columns) {
				vertical = false;
				c = 0;
			} else {
				c++;
			}
		}

	}

	/**
	 * This marks an edge of a given ID if it is not marked yet and marks the
	 * surrounding Field-Entities if necessary. It returns the correspondent
	 * action that took place.
	 * 
	 * @param edgeID
	 *            The ID of the marked Edge.
	 * @param markingPlayer
	 *            The Player marking this edge.
	 * 
	 * @return FieldStates.INVALID - Edge already marked
	 * @return MARKED - Edge has been marked
	 * @return ONE - Edge has been marked and markingPlayer achieved to own one
	 *         Field
	 * @return TWO - Edge has been marked and markingPlayer achieved to own two
	 *         Fields
	 */
	public int markEdge(int edgeID, Player markingPlayer) {
		if (edges[edgeID].isMarked()) {
			anotherTurn = true;
			return -1;
		}
		
		// fancy action has to happen before the wall is marked
		int c = 0;
		if(edgeID == fancyID){
			c += fancy.action(this, markingPlayer);
		}
		
		
		edges[edgeID].setMarked();
		// instead of new Integer(edgeID)
		removeUnmarkedIndex(edgeID);
		
		// counting marked Fields
		c += countMarkedFields(edgeID, markingPlayer);
		
		// TODO: handle another turn when fancy events happen
		anotherTurn = (c>0);
		
		return c;
	}
	
	public int countMarkedFields(int edgeID, Player markingPlayer){
		int c = 0;
		for (int fieldID : this.hashFunction(edgeID)) {
			if (fieldID != -1 && fields[fieldID].increment(markingPlayer)) {
				c++;
			}
		}
		return c;
	}
	
	public boolean removeUnmarkedIndex(int edgeID){
		return unmarkedEdges.remove(Integer.valueOf(edgeID));
	}

	/**
	 * FindBugs claims this might "expose internal representation" but it needs to be public for the tests
	 * @return the array of the edges
	 */
	public Edge[] getEdges() {
		return edges;
	}
	
	public LinkedList<Integer> getUnmarkedEdges() {
		return unmarkedEdges;
	}

	/**
	 * Basic print of this Map to the console.
	 */
	public void plot() {
		StringBuilder sb = new StringBuilder();
		int edgep = 0;
		int fieldp = 0;

		for (int rowp = 0; rowp < rows * 2 + 1; rowp++) {
			for (int colp = 0; colp < columns * 2 + 1; colp++) {
				if ((colp & 1) == 1 && (rowp & 1) == 1) {
					sb.append("\t");
					Field f = fields[fieldp];

					if (f.isOwned())
						sb.append(f.getOwner().getStrId());

					/**
					 * Use the following 4 lines instead of the previous 2 to
					 * print the id of the field on the map
					 */
					/*
					 * if (f.isOwned()) { sb.append(f.getOwner().getStrId()); }
					 * else sb.append("[").append(fieldp).append("]");
					 */

					fieldp++;
				} else if ((colp & 1) == 1 || (rowp & 1) == 1) {
					sb.append("\t");
					Edge e = edges[edgep];
					if (e.isMarked())
						sb.append(e.isVertical() ? "|" : "-");
					else
						sb.append(edgep);
					if(edgep == fancyID && fancyVisible)
						sb.append("@");

					edgep++;
				} else {
					sb.append("\t *");
				}
			}

			sb.append(System.lineSeparator()).append(System.lineSeparator());
		}

		System.out.println(sb);

	}

	/**
	 * This maps the edgeID to an array of two integers that mark the
	 * surrounding field entities of that edge.
	 * 
	 * @param edgeID
	 *            The Edge's ID of which the neighbors are sought
	 * @return An array of neighbor FieldIDs or -1 if there is none.
	 */
	private int[] hashFunction(int edgeID) {
		int[] result = new int[2];
		int edgesPerLine = this.columns * 2 + 1;

		// applying 1st function
		if ((edgeID + columns + 1) % edgesPerLine == 0) {
			result[0] = -1;
		} else {
			result[0] = edgeID
					- Math.floorDiv(edgeID + columns + 1, edgesPerLine)
					* (columns + 1);
		}

		// applying 2nd function
		if ((edgeID + 1) % edgesPerLine == 0) {
			result[1] = -1;
		} else {
			result[1] = edgeID - columns
					- Math.floorDiv(edgeID + 1, edgesPerLine) * (columns + 1);
		}

		// removing fields out of boundaries
		for (int i = 0; i < 2; i++) {
			if (result[i] < -1 || result[i] >= columns * rows) {
				result[i] = -1;
			}
		}

		return result;
	}

	public int getEdgeCount() {
		return edges.length;
	}

	public Map copy() {
		Map map = new Map(rows, columns, fancy);
		int p = 0;
		map.unmarkedEdges.clear();
		for (Edge edge : edges){
			map.edges[p] = edge.copy();
			p++;
			if(!edge.isMarked()) {
				map.unmarkedEdges.add(edge.id);
			}
			
		}
		int i=0;
		for (Field field : this.fields){
			map.fields[i] = field.copy();
			i++;
		}
		return map;
	}
	
	/**
	 * Calculates a random id of an unmarked edge that shall cause fancy actions.
	 * 
	 * NOTE: this is called at the beginning of the game so no edge is marked. For the sake of flexibility it is implemented that way.
	 * 
	 * @return the id
	 */
	private int generateFancyWallId(){
		return new Random().nextInt(rows * columns);
	}
	
	public void setFancyVisible(){
		fancyVisible = true;
	}
	
	public boolean anotherTurn(){
		return anotherTurn;
	}
	
	public int getFancyId(){
		return fancyID;
	}
	
	public int getColumns(){
		return columns;
	}
	
	public int getRows(){
		return rows;
	}
}
