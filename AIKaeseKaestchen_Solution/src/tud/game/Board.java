package tud.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Board {

	/**
	 * count of fields in the board
	 */
	int fieldLen = 4;
	/**
	 * game board representation multiple array
	 */
	String[][] board;
	/**
	 * length of longest position number, e.g. position 123 length = 3
	 */
	int optionLen = 1;
	/**
	 * length of longest position number, e.g. position 123 length = 3
	 */
	int count = 0;

	/**
	 * list of positions in game
	 */
	List<Position> positions = new ArrayList<Position>();

	/**
	 * number of fields with open positions
	 */
	int numberOfFreeFields = 0;

	public int getNumberOfFreeFields() {
		return numberOfFreeFields;
	}

	public Board(int fieldLen) {
		this.fieldLen = fieldLen;
		board = new String[fieldLen * 2 + 1][];
		init();
	}

	/**
	 * Initializes the board with numbers and helping stars
	 */
	private void init() {
		for (int i = 0; i < board.length; i++) {
			board[i] = new String[fieldLen * 2 + 1];

		}
		int count = 1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						board[i][j] = "*";
					} else {
						count = initPosition(count, i, j);
					}
				} else {
					if (j % 2 == 0) {
						count = initPosition(count, i, j);
					} else {

						board[i][j] = " ";
					}

				}
			}
		}
		this.count = count - 1;
		this.numberOfFreeFields = this.count;
		optionLen = String.valueOf(count - 1).length();
	}

	/**
	 * initializes one position
	 * 
	 * @param count
	 *            - count of positions
	 * @param i
	 *            - row of position
	 * @param j
	 *            - column of position
	 * @return count
	 */
	private int initPosition(int count, int i, int j) {
		board[i][j] = String.valueOf(count);
		positions.add(new Position(i, j));
		count++;
		return count;
	}

	/**
	 * returns size of positions
	 * @return size of positions
	 */
	public int getBoardValueSize() {
		return positions.size();
	}

	/**
	 * prints the board onto the console
	 */
	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i % 2 == 0) {
					System.out.printf(" %" + optionLen + "s ", board[i][j]);
				} else {
					if (j > 0 && j < fieldLen * 2) {
						if (board[i][j].equals(" "))
							System.out.printf("%" + (board[i][j].length() + 1) + "s", " ");
						if (board[i][j].startsWith("p")) {
							System.out.printf("%" + (board[i][j].length()) + "s", " ");
						}
					}
					System.out.printf(" %" + optionLen + "s", board[i][j]);
				}
			}
			System.out.printf("\n");
		}
		System.out.println();
	}

	/**
	 * checks if the wall number pos is not set
	 * @param pos - position, which should be checked
	 * @return true if wall is free, otherwise false
	 */
	public boolean checkRules(int pos) {
		if (pos < 1 || pos > count)
			return false;
		return board[getRow(pos)][getCol(pos)].equals(String.valueOf(pos));
	}
	/**
	 * checks if position pos has a wall
	 * @param pos - position
	 * @return true if pos has a wall, false otherwise
	 */
	public boolean IsUsed(int pos) {
		int row = positions.get(pos - 1).getRow();
		int col = positions.get(pos - 1).getCol();
		return !board[row][col].matches("[1-9][0-9]*");
	}

	/**
	 * returns all fields with open walls
	 * @return all fields with open walls
	 */
	public List<String> getFields() {
		List<String> fields = new ArrayList<String>();
		for (Position p : positions) {
			if (Game.isNumber(board[p.getRow()][p.getCol()])) {
				fields.add(board[p.getRow()][p.getCol()]);
			}
		}
		return fields;
	}

	/**
	 * sets the wall on position pos and checks if a box is conquered
	 * @param pos - position where to set the wall
	 * @param name - internal player name
	 * @return if a box is conquered then Again else NextTurn
	 */
	public Action nextAction(int pos, String name) {
		int row = getRow(pos);
		int col = getCol(pos);
		if (board[row][col].equals(String.valueOf(pos))) {
			if (row % 2 == 0) {
				board[row][col] = "-";
				numberOfFreeFields--;
				return isConqueredByRow(name, row, col);

			} else {
				board[row][col] = "|";
				numberOfFreeFields--;
				return isConqueredByCol(name, row, col);
			}
		}

		return Action.NextTurn;
	}
	/**
	 * returns column of position pos
	 * @param pos - position for which we want to have column
	 * @return column of position pos
	 */
	public int getCol(int pos) {
		return positions.get(pos - 1).getCol();
	}
	/**
	 * returns row of position pos
	 * @param pos - position for which we want to have row
	 * @return row of position pos
	 */
	public int getRow(int pos) {
		return positions.get(pos - 1).getRow();
	}

	/**
	 * checks if above or below box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action isConqueredByRow(String name, int i, int j) {
		Action again = Action.NextTurn;
		again = checkBoxAbove(name, i, j, again);
		again = checkBoxBelow(name, i, j, again);
		return again;
	}

	/**
	 * checks if above or below box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action checkBoxBelow(String name, int i, int j, Action again) {
		if (i + 2 < board.length) {
			if (board[i + 2][j].equals("-") && board[i + 1][j - 1].equals("|") && board[i + 1][j + 1].equals("|")) {
				board[i + 1][j] = name;
				again = Action.Again;
			}
		}
		return again;
	}

	/**
	 * checks if above box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @param again - result of the previous check
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action checkBoxAbove(String name, int i, int j, Action again) {
		if (i - 2 >= 0) {
			if (board[i - 2][j].equals("-") && board[i - 1][j - 1].equals("|") && board[i - 1][j + 1].equals("|")) {
				board[i - 1][j] = name;
				again = Action.Again;
			}
		}
		return again;
	}

	/**
	 * checks if left or right box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action isConqueredByCol(String name, int i, int j) {
		Action again = Action.NextTurn;
		again = checkBoxLeft(name, i, j, again);
		again = checkBoxRight(name, i, j, again);
		return again;
	}
	/**
	 * checks if right box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @param again - result of the previous check
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action checkBoxRight(String name, int i, int j, Action again) {
		if (j + 2 < board[i].length) {
			if (board[i][j + 2].equals("|") && board[i - 1][j + 1].equals("-") && board[i + 1][j + 1].equals("-")) {
				board[i][j + 1] = name;
				again = Action.Again;
			}
		}
		return again;
	}
	/**
	 * checks if left box is conquered
	 * @param name - internal player name
	 * @param i - row of set position 
	 * @param j - column of set position
	 * @param again - result of the previous check
	 * @return Again if a box is conquered, NextTurn otherwise
	 */
	private Action checkBoxLeft(String name, int i, int j, Action again) {
		if (j - 2 >= 0) {
			if (board[i][j - 2].equals("|") && board[i - 1][j - 1].equals("-") && board[i + 1][j - 1].equals("-")) {
				board[i][j - 1] = name;
				again = Action.Again;
			}
		}
		return again;
	}
	/**
	 * checks if a position is not set and the game is over
	 * @return true if game is over, false otherwise
	 */
	public boolean isEnded() {
		return numberOfFreeFields == 0;
	}
	/**
	 * checks who has the most conquered boxes and prints the player statistics
	 * @param ps - map of internal player names to players
	 */
	public void showScore(Map<String, Player> ps) {
		PlayerStats pls = getPlayerStats();
		Map<String, Integer> map = pls.getMap();

		for (String p : ps.keySet()) {
			if (!map.containsKey(p)) {
				map.put(p, 0);
			}
		}
		List<String> players = determineWinners(ps, map, pls.getMaxCount());
		printWinners(players, ps.get(pls.getPlayer()).getName());
		printPlayerStats(ps, map);

	}
	/**
	 * returns player statistics
	 * @return player statistics
	 */
	public PlayerStats getPlayerStats() {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		int maxCount = 0;
		String player = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].startsWith("p")) {
					int count = 1;
					if (map.containsKey(board[i][j])) {
						count += map.get(board[i][j]);
					}
					if (count > maxCount) {
						maxCount = count;
						player = board[i][j];
					}
					map.put(board[i][j], count);
				}
			}
		}
		return new PlayerStats(map, player, maxCount);
	}
	/**
	 * prints winners
	 * @param players - winners
	 * @param player - one player name of the winners
	 */
	private void printWinners(List<String> players, String player) {
		if (players.size() > 1) {
			System.out.println("Draw between players: " + Arrays.toString(players.toArray()));

		} else {
			System.out.println("The player " + player + " has won!!!");
		}
	}

	/**
	 * prints statistics of each player
	 * @param ps - map of internal player names to players
	 * @param map - map of internal player names to conquered box counts
	 */
	private void printPlayerStats(Map<String, Player> ps, Map<String, Integer> map) {
		System.out.println("The players have:");
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(ps.get(entry.getKey()).getName() + "(" + entry.getKey() + "): " + entry.getValue());
		}
	}

	/**
	 * determines the winners of the game
	 * @param ps -  map of internal player names to players
	 * @param map - map of internal player names to conquered box counts
	 * @param maxCount - highest number of conquered boxes
	 * @return - list of winners
	 */
	private List<String> determineWinners(Map<String, Player> ps, Map<String, Integer> map, int maxCount) {

		List<String> players = new ArrayList<String>();
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == maxCount) {
				players.add(ps.get(entry.getKey()).getName());
			}
		}
		return players;
	}

	/**
	 * counts the boxes, which were conquered by a player
	 * 
	 * @param player - the player for whom the conquered boxes are accumulated
	 * @return count of boxes, which were conquered by the player
	 */
	public int getScore(String player) {
		int counter = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(player)) {
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * copies complete game board
	 * 
	 * @return board copy
	 */
	public Board copy() {
		Board newBoard = new Board(this.fieldLen);

		String[][] boardCopy = new String[board.length][];
		for (int i = 0; i < boardCopy.length; i++) {
			boardCopy[i] = new String[board[i].length];
			System.arraycopy(board[i], 0, boardCopy[i], 0, boardCopy[i].length);
		}

		List<Position> newPositions = new ArrayList<Position>();
		for (Position position : positions) {
			newPositions.add(position);
		}
		newBoard.board = boardCopy;
		newBoard.count = this.count;
		newBoard.positions = newPositions;
		newBoard.optionLen = this.optionLen;
		newBoard.numberOfFreeFields = this.numberOfFreeFields;

		return newBoard;
	}
}
