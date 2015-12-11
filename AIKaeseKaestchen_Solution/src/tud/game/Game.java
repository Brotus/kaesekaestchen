package tud.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import tug.game.ai.AdvancedAI;
import tug.game.ai.BasicAI;
import tug.game.ai.PlayerAI;

public class Game {
	/**
	 * play a game against computer
	 */
	private static boolean isAiEnabled = false;
	/**
	 * player count
	 */
	private static int numberOfPlayers = 2;

	/**
	 * begin of game
	 * @param args - not needed parameter array
	 */
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Map<String, Player> players;

		isAiEnabled = playVersusComputer(scan);

		if (isAiEnabled) {
			players = createPlayers(scan, 1);
			players.put("p2", selectAI(scan));

		} else {
			numberOfPlayers = getNumberOfPlayers(scan);
			players = createPlayers(scan, numberOfPlayers);
		}

		Board board = new Board(getFieldLen(scan));
		Player currPlayer = players.get("p1");
		while (true) {
			board.printBoard();
			System.out.println("It is your turn " + currPlayer.getName() + "(" + currPlayer.getPlayerName() + "):");
			int pos = checkTurn(scan, board, currPlayer);
			if (board.nextAction(pos, currPlayer.getPlayerName()).equals(Action.NextTurn)) {
				currPlayer = nextPlayer(players, currPlayer);
			}
			if (board.isEnded()) {
				board.printBoard();
				board.showScore(players);
				break;
			}
			System.out.println("---------------------------------------------------------------");
		}
		scan.close();
	}

	/**
	 * returns the next player in the game
	 * @param players - map of internal player names to players
	 * @param currPlayer - current player
	 * @return next player in the row, if current player is the last it returns player 1
	 */
	private static Player nextPlayer(Map<String, Player> players, Player currPlayer) {
		return players.get("p" + ((currPlayer.getPlayerId() % players.size()) + 1));
	}

	/**
	 * checks if the input is a valid turn
	 * @param scan - input scanner
	 * @param board - game board
	 * @param currPlayer - current player
	 * @return valid turn
	 */
	private static int checkTurn(Scanner scan, Board board, Player currPlayer) {
		String turn;
		if (currPlayer instanceof PlayerAI) {
			turn = ((PlayerAI) currPlayer).getNextTurn(board);
		} else {
			turn = scan.next();
			while (!isNumber(turn) || turn.matches("[?]") || !board.checkRules(Integer.valueOf(turn))) {
				if (turn.matches("[?]") && !isAiEnabled && numberOfPlayers == 2) {
					String nextPossibleTurn = AdvancedAI.supportPlayer(board, currPlayer.getPlayerName());
					System.out.println("Next possible turn: " + nextPossibleTurn);
				} else {
					System.out.println("Your turn is not valid! Please make another.");
				}
				turn = scan.next();
			}
		}
		return Integer.valueOf(turn);
	}

	/**
	 * creates players and asks for their names
	 * @param scan - input scanner
	 * @param playerCount - count of how many players should be created
	 * @return - map of internal player names to players
	 */
	private static Map<String, Player> createPlayers(Scanner scan, int playerCount) {

		Map<String, Player> players = new HashMap<String, Player>();
		for (int i = 0; i < playerCount; i++) {
			System.out.println("Player " + (i + 1) + " what is your name?");
			System.out.print("Name:");
			String pstr = scan.next();
			Player p = new Player(pstr);
			players.put(p.getPlayerName(), p);
			System.out.println("Welcome " + pstr);
		}

		System.out.println("---------------------------------------------------------------");

		return players;
	}

	/**
	 * asks for player count and returns the count
	 * 
	 * @return player count
	 */
	private static int getNumberOfPlayers(Scanner scan) {
		System.out.println("Enter number of players:");
		String numberOfPlayers = scan.next();
		while (!isNumber(numberOfPlayers)) {
			System.out.println("Your option is not valid! Please make another.");
			numberOfPlayers = scan.next();
		}

		System.out.println("---------------------------------------------------------------");

		return Integer.valueOf(numberOfPlayers);
	}

	/**
	 * asks if a game should be played against a computer or a human
	 * 
	 * @param scan - input scanner
	 * @return true if human versus computer have been chosen, false otherwise
	 */
	private static boolean playVersusComputer(Scanner scan) {
		System.out.println("To play Human vs. Human enter   : h");
		System.out.println("To play Human vs. Computer enter: c");
		String gameMode = scan.next();
		while (!gameMode.matches("[h,H,c,C]")) {
			System.out.println("Your option is not valid! Please make another.");
			gameMode = scan.next();
		}

		System.out.println("---------------------------------------------------------------");

		return gameMode.equalsIgnoreCase("c");
	}

	/**
	 * asks for AI level
	 * 
	 * @param scan - input scanner
	 * @return true if human versus computer have been chosen, false otherwise
	 */
	private static Player selectAI(Scanner scan) {
		System.out.println("Select AI?");
		System.out.println("basicAI(b) or advancedAI(a):");
		String selectedAI = scan.next();
		while (!selectedAI.matches("[a,A,b,B]")) {
			System.out.println("Your option is not valid! Please make another.");
			selectedAI = scan.next();
		}

		System.out.println("---------------------------------------------------------------");

		if (selectedAI.equalsIgnoreCase("a"))
			return new AdvancedAI();
		return new BasicAI();
	}

	/**
	 * asks for board length
	 * 
	 * @param scan - input scanner
	 * @return board length
	 */
	private static int getFieldLen(Scanner scan) {
		System.out.println("Enter length of gameboard:");
		String fieldLen = scan.next();
		while (!isNumber(fieldLen)) {
			System.out.println("Your option is not valid! Please make another.");
			fieldLen = scan.next();
		}

		System.out.println("---------------------------------------------------------------");

		return Integer.valueOf(fieldLen);
	}

	/**
	 * checks if the string is a positive number
	 * @param value - string, which should be checked
	 * @return true if string is a positive number, false otherwise
	 */
	public static boolean isNumber(String value) {
		return value.matches("[1-9][0-9]*");
	}

	

}
