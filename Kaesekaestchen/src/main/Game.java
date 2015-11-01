package main;

import java.util.Scanner;
import java.util.function.Predicate;

import entity.FieldStates;
import entity.Map;
import entity.Player;

/**
 * 
 * This class handles the UI (console) and runs the game loop.
 *
 */
public class Game {

	private int width, height;
	private Player p1, p2;
	private boolean errorMessage = false;
	private static Scanner s = new Scanner(System.in);
	private Map gameMap;

	/**
	 * Start the game.
	 */
	Game() {
		init();

		gameMap = new Map(height, width);
		GameLoop(p1);
	}

	/**
	 * Make the users enter their names and the size of the map.
	 */
	private void init() {
		System.out.println("Application will ignore whitespaces.");
		String str;
		str = parseInput("Enter the amount of players:", "[1-9]+");
		int players = Integer.parseInt(str);
		// remove this later
		players = 2;
		for (int i = 1; i <= players; i++) {
			str = parseInput("Enter the name of player P" + i, "[a-zA-Z]+\\w+");
			if (i == 1)
				p1 = new Player(str, 1);
			else if (i == 2)
				p2 = new Player(str, 2);
		}
		width = Integer.parseInt(parseInput("Enter the width of the board:", "[1-9]+"));
		height = Integer.parseInt(parseInput("Enter the height of the board:", "[1-9]+"));
	}

	/**
	 * Prompts the user the prompt as long as the input does not yet match the
	 * matcher (regular expression). Whitespaces will be deleted.
	 * 
	 * @param matcher
	 *            a regular expression the input as to match to
	 * @return a valid input the user entered
	 */
	private String parseInput(String prompt, String matcher) {
		String str;
		while (true) {
			System.out.println(prompt);
			str = s.nextLine();
			if (str.matches(matcher))
				return str;
			else
				System.out.println("Input has to match " + matcher + ".");
		}
	}

	/**
	 * Advanced version of parseInput(String, String). Prompts every time a
	 * wrong input is used. An input is correct if and only if it matches the
	 * matcher and the Predicate p.
	 * 
	 * @param matcher
	 *            the matcher the input should match to
	 * @param p
	 *            a Java 8 functional predicate
	 * @param predicateFailMessage
	 *            the message to display if the input does not satisfy the
	 *            predicate
	 * @return a valid string
	 */
	private String parseInput(String prompt, String matcher, Predicate<String> p, String predicateFailMessage) {
		String str;
		while (true) {
			System.out.println(prompt);
			str = s.nextLine();
			if (str.matches(matcher))
				if (p.test(str))
					return str;
				else
					System.out.println(predicateFailMessage);
			else
				System.out.println("Input has to match " + matcher + ".");
		}
	}

	/**
	 * manages the turns of the game
	 * 
	 * @param activePlayer
	 */
	private void GameLoop(Player activePlayer) {
		gameMap.plot();
		int numberOfFields = height * width;

		if (errorMessage) {
			System.out.println("This edge is already selected.");
			errorMessage = false;
		}

		int playerInput = Integer.parseInt(parseInput(activePlayer.getName() + ", enter the edge you want to claim:", "\\d+", p -> {
			int n = Integer.parseInt(p);
			return 0 <= n && n < gameMap.getEdgeCount();
		}, "Input too high or too low."));

		// players have to enter again if edge was already claimed, if they get
		// a point (or two) they get an additional turn
		FieldStates fs = gameMap.markEdge(playerInput, activePlayer);
		switch (fs) {
		case INVALID:
			errorMessage = true;
			GameLoop(activePlayer);
			break;

		case MARKED:
			activePlayer = switchActivePlayer(activePlayer);
			break;

		case ONE:
			activePlayer.increaseOwnedFields(1);
			break;

		case TWO:
			activePlayer.increaseOwnedFields(2);
			break;
		}

		// decides if game is over (and who won) based on the sum of the player
		// scores
		if ((p1.getOwnedFields() + p2.getOwnedFields()) == numberOfFields) {
			if (p1.getOwnedFields() < p2.getOwnedFields()) {
				System.out.println("Congratulations " + p2.getName() + ", you won!");
			}
			if (p1.getOwnedFields() > p2.getOwnedFields()) {
				System.out.println("Congratulations " + p1.getName() + ", you won!");
			} else
				System.out.println("It's a draw. Nobody wins. :( ");
		} else
			GameLoop(activePlayer);

	}

	/**
	 * switches the active player
	 * 
	 * @param activePlayer
	 * @return
	 */
	private Player switchActivePlayer(Player activePlayer) {
		if (activePlayer.equals(p1)) {
			return p2;
		} else
			return p1;
	}
}
