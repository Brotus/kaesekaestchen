package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
	private HashSet<Integer> unmarkedEdges = new HashSet<Integer>();
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
	 * @param rows
	 *            The number of rows of the Map
	 * @param columns
	 *            The number of columns of the map
	 * @param fancy
	 *            The FancyHandle when an edge with fancyID is marked
	 */
	public Map(int rows, int columns, FancyHandle fancy) {
		this.rows = rows;
		this.columns = columns;
		this.fancy = fancy;

		this.fancyID = generateFancyWallId();

		this.makeEdges();
		this.makeFields();
	}

	public Map(int rows, int columns, FancyHandle fancy, int fancyID) {
		this.rows = rows;
		this.columns = columns;
		this.fancy = fancy;
		this.fancyID = fancyID;

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
	 * This initializes the edges array and the unmarkedEdges list. The ID of an
	 * Edge is equal to it's position in the edges array of this class. This
	 * method also sets if an edge is horizontal based on the Maps size.
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
	 * Marks the Edge and increments the surrounding fields.
	 * 
	 * @param edgeID
	 *            = Edge to be marked.
	 * @param markingPlayer
	 *            = Player selecting this edge.
	 * @return the amount of points made by this selection.
	 */
	public int markEdge(int edgeID, Player markingPlayer) {
		return this.markEdge(edgeID, markingPlayer, true);
	}

	/**
	 * Marks an edge and calculates the amount of fields doing so closed.
	 * 
	 * @param edgeID
	 *            the ID of the edge that should be marked
	 * @param markingPlayer
	 *            the player whose turn it is and who is supposed to earn the
	 *            points
	 * @param impact
	 *            decides whether surrounding fields change their owner, if a
	 *            field is closed again
	 * @return the amount of points earned or -1 if the player gets another turn
	 *         because the selected edge has already been marked
	 */
	public int markEdge(int edgeID, Player markingPlayer, boolean impact) {
		// if the edge has already been marked, the player gets another turn
		if (edges[edgeID].isMarked()) {
			anotherTurn = true;
			return -1;
		}

		// fancy action has to happen before the wall is marked
		int c1 = 0;
		if (edgeID == fancyID) {
			c1 += fancy.action(this, markingPlayer);
			fancyID = -1;
		}

		// mark the unmarked edge
		edges[edgeID].setMarked(true);

		// remove its index from the list of marked edges
		removeUnmarkedIndex(edgeID);

		// counting marked Fields
		int c2 = countMarkedFields(edgeID, markingPlayer, impact);

		// Player gets another turn if the marked edge closed a field.
		// He doesn't get one if a fancy action caused new closed fields.
		anotherTurn = (c2 > 0);

		return c1 + c2;
	}

	/**
	 * * Counts the amount of marked fields around the edge with edgeID.
	 * 
	 * 
	 * @param edgeID
	 *            the edge marked
	 * @param markingPlayer
	 *            the currently active player who will receive the points
	 * @param impact
	 *            decides whether surrounding fields change their owner, if a
	 *            field is closed again
	 * @return the amount of points gained by the marking player
	 */
	public int countMarkedFields(int edgeID, Player markingPlayer, boolean impact) {
		int c = 0;
		for (int fieldID : this.hashFunction(edgeID)) {
			if (fieldID != -1) {
				if (fields[fieldID].getOwner() == null || fields[fieldID].getOwner() == markingPlayer || impact) {
					if (fields[fieldID].increment(markingPlayer))
						c++;
				}
			}
		}
		return c;
	}

	/**
	 * Tries to remove an ID of an edge from the list of unmarked edges.
	 * 
	 * @param edgeID
	 *            the ID of the edge to be unmarked
	 * @return true iff removing was successful i.e. if the edge was unmarked
	 *         before
	 */
	public boolean removeUnmarkedIndex(int edgeID) {
		return unmarkedEdges.remove(Integer.valueOf(edgeID));
	}

	/**
	 * This undoes the marking of an edge if it has been marked and decrements
	 * the number of marked edges of a field.
	 * 
	 * @param edgeID
	 *            = The ID of the edge to be unmarked.
	 * @return true if the edge has been marked before.
	 */
	public boolean undo(int edgeID) {

		if (this.unmarkedEdges.add(edgeID)) {
			edges[edgeID].setMarked(false);
			for (int f : this.hashFunction(edgeID)) {
				if (f > -1)
					this.fields[f].decrement();
			}
			return true;
		}
		return false;
	}

	/**
	 * FindBugs claims this might "expose internal representation" but it needs
	 * to be public for the tests
	 * 
	 * @return the array of the edges
	 */
	public Edge[] getEdges() {
		return edges;
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

					if (f.hasBeenOwned())
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
					if (edgep == fancyID && fancyVisible)
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
			result[0] = edgeID - Math.floorDiv(edgeID + columns + 1, edgesPerLine) * (columns + 1);
		}

		// applying 2nd function
		if ((edgeID + 1) % edgesPerLine == 0) {
			result[1] = -1;
		} else {
			result[1] = edgeID - columns - Math.floorDiv(edgeID + 1, edgesPerLine) * (columns + 1);
		}

		// removing fields out of boundaries
		for (int i = 0; i < 2; i++) {
			if (result[i] < -1 || result[i] >= columns * rows) {
				result[i] = -1;
			}
		}

		return result;
	}

	/**
	 * @return a clone of the current map
	 */
	public Map copy() {
		Map map = new Map(rows, columns, fancy);
		int p = 0;
		map.unmarkedEdges.clear();
		for (Edge edge : edges) {
			map.edges[p] = edge.copy();
			p++;
			if (!edge.isMarked()) {
				map.unmarkedEdges.add(edge.id);
			}

		}
		int i = 0;
		for (Field field : this.fields) {
			map.fields[i] = field.copy();
			i++;
		}
		return map;
	}

	/**
	 * Calculates a random id of an unmarked edge that shall cause fancy
	 * actions.
	 * 
	 * NOTE: this is called at the beginning of the game so no edge is marked.
	 * For the sake of flexibility it is implemented that way.
	 * 
	 * @return the id
	 */
	private int generateFancyWallId() {
		return new Random().nextInt(rows * columns);
	}
	
	public LinkedList<Player> getWinner() {
		HashMap<Player, Integer> points = new HashMap<Player, Integer>(5);
		for (Field f : fields) {
			Player p = f.getOwner();
			if (points.get(p) != null) {
				points.put(p, points.get(p)+1 );
			} else {
				points.put(f.getOwner(), 1);
			}
		}
		int max = Collections.max(points.values());
		LinkedList<Player> winningPlayers = new LinkedList<>();
		for (Player p : points.keySet()) {
			if (points.get(p) == max) {
				winningPlayers.add(p);
			}
		}
		return winningPlayers;
	}
	
	/**
	 * @return if the game is done
	 */
	public boolean isEnd() {
		for (Field f : fields) {
			if (!f.hasBeenOwned()){
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return the total amount of edges existent on this map
	 */
	public int getEdgeCount() {
		return edges.length;
	}

	public void setFancyVisible() {
		fancyVisible = true;
	}

	public boolean anotherTurn() {
		return anotherTurn;
	}

	public int getFancyId() {
		return fancyID;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public Field[] getFieldArray() {
		return fields;
	}

	public HashSet<Integer> getUnmarkedEdges() {
		return unmarkedEdges;
	}
}
