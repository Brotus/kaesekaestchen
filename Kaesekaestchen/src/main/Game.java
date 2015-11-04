package main;

import java.util.Scanner;
import java.util.function.Predicate;

import entity.FieldStates;
import entity.Map;
import entity.Player;
import entity.AI.AI;
import entity.AI.MinMaxAI;
import entity.AI.SimpleAI;

/**
 * 
 * This class handles the UI (console) and runs the game loop.
 *
 */
public class Game {

	private int width, height;
	private Player[] players;
	private int playerAmount;
	private boolean errorMessage = false;
	private static Scanner s = new Scanner(System.in);
	private Map gameMap;
	private boolean useAI;

	/**
	 * Start the game.
	 */
	Game() {
		// width and height need to be entered here because Game.init() needs
		// gameMap
		System.out.println("Application will ignore whitespaces.");
		width = Integer.parseInt(parseInput("Enter the width of the board:", "[1-9]+\\d*"));
		height = Integer.parseInt(parseInput("Enter the height of the board:", "[1-9]+\\d*"));

		gameMap = new Map(height, width);

		init();

		GameLoop();
	}

	/**
	 * Input of - amount of players - names of players - AI types
	 */
	private void init() {
		String str;
		playerAmount = Integer.parseInt(parseInput("Enter the amount of players (1 == human against AI):", "[1-9]+\\d*"));
		useAI = playerAmount == 1;
		boolean auxAIAvailable = playerAmount <= 2;
		players = new Player[playerAmount + (useAI ? 1 : 0)];
		for (int i = 1; i <= playerAmount; i++) {
			str = parseInput("Enter the name of player P" + i, "[a-zA-Z]+\\w*");
			players[i - 1] = new Player(str, i, auxAIAvailable ? new MinMaxAI(gameMap) : null, true);
		}
		if (useAI) {
			playerAmount = 2;
			int aiType = Integer.parseInt(parseInput("Enter AI type you want to play against. (0 or 1):", "[01]"));
			AI ai;
			switch (aiType) {
			case 0:
				ai = new SimpleAI(gameMap);
				break;
			case 1:
				ai = new MinMaxAI(gameMap);
				break;
			default:
				ai = new SimpleAI(gameMap);
			}
			players[1] = new Player("AI", 2, ai, false);
		}

	}

	/**
	 * Prompts the user the prompt as long as the input does not yet match the
	 * matcher (regular expression). Whitespace will be deleted.
	 * 
	 * @param matcher
	 *            a regular expression the input as to match to
	 * @return a valid input the user entered
	 */
	private String parseInput(String prompt, String matcher) {
		return parseInput(prompt, matcher, x -> true, "Input has to match " + matcher + ".");
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
	 * @param pid
	 *            the index of the active player in players
	 */
	private void GameLoop2(int pid) {
		gameMap.plot();

		if (errorMessage) {
			System.out.println("This edge is already selected.");
			errorMessage = false;
		}

		// prompts the user to enter a valid edge

		// playerInput will contain the targeted edge
		int playerInput;
		// 1 player against AI, AI's turn
		if (useAI && pid == 1) {
			playerInput = players[pid].getTurn();
		} else {
			// this is complicated because it parses 'help' and edgeids
			String input = parseInput(players[pid].getName() + ", enter the edge you want to claim or 'help':", "(\\d+)|(help)", p -> {
				if (p.equals("help"))
					return true;
				else
					// parse for valid edge
					try {
						int n = Integer.parseInt(p);
						return 0 <= n && n < gameMap.getEdgeCount();
					} catch (Exception e) {
						return false;
					}
			}, "Input has to match (\\d+)|(help).");
			if (input.equals("help")) {

				System.out.println("AI suggests: " + players[pid].getTurn());
				GameLoop2(pid);
				return;
			} else
				playerInput = Integer.parseInt(input);
		}

		int numberOfFields = height * width;
		// players have to enter again if edge was already claimed, if they get
		// a point (or two) they get an additional turn
		FieldStates fs = gameMap.markEdge(playerInput, players[pid]);
		switch (fs) {
		case INVALID:
			errorMessage = true;
			GameLoop2(pid);
			return;
		case MARKED:
			pid = switchActivePlayer(pid);
			break;

		case ONE:
			players[pid].increaseOwnedFields(1);
			break;

		case TWO:
			players[pid].increaseOwnedFields(2);
			break;
		}
		// decides if game is over (and who won) based on the sum of the player
		// scores
		int sum = 0;
		for (int i = 0; i < playerAmount; i++)
			sum += players[i].getOwnedFields();

		if (sum == numberOfFields) {
			gameMap.plot();
			int maxID = 0;
			boolean draw = false;
			for (int i = 1; i < playerAmount; i++) {
				if (players[i].getOwnedFields() > players[maxID].getOwnedFields()) {
					maxID = i;
					draw = false;
				} else if (players[i].getOwnedFields() == players[maxID].getOwnedFields()) {
					draw = true;
				}
			}
			if (draw)
				System.out.println("It's a draw. Nobody wins. :( ");
			else {
				System.out.println("Congratulations, " + players[maxID].getName() + ", you won!");
				return;
			}
		} else
			GameLoop2(pid);

	}

	private void GameLoop() {
		// the id of the currently active player
		int pid = 0;
		
		// playerInput will contain the targeted edge
		int playerInput;
		
		int amountOfFields = height * width;
		
		gameMap.plot();

		while (true) {

			// 1 player against AI, AI's turn
			if (useAI && pid == 1) {
				playerInput = players[pid].getTurn();
			} else {
				// this is complicated because it parses 'help' and edgeids
				String input = parseInput(players[pid].getName() + ", enter the edge you want to claim or 'help':", "(\\d+)|(help)", p -> {
					if (p.equals("help"))
						return true;
					else
						// parse for valid edge
						try {
							int n = Integer.parseInt(p);
							return 0 <= n && n < gameMap.getEdgeCount();
						} catch (Exception e) {
							return false;
						}
				}, "Input has to match (\\d+)|(help).");
				if (input.equals("help")) {
					System.out.println("AI suggests: " + players[pid].getTurn());
					continue;
				} else
					playerInput = Integer.parseInt(input);
			}
			
			// players have to enter again if edge was already claimed, if they get
			// a point (or two) they get an additional turn
			FieldStates fs = gameMap.markEdge(playerInput, players[pid]);
			switch (fs) {
			case INVALID:
				System.out.println("This edge is already selected.");
				continue;
			case MARKED:
				pid = switchActivePlayer(pid);
				if (useAI && pid == 1)
					System.out.println("AI selects " + playerInput + ".");
				break;
			case ONE:
				players[pid].increaseOwnedFields(1);
				if (useAI && pid == 1)
					System.out.println("AI selects " + playerInput);
				break;

			case TWO:
				players[pid].increaseOwnedFields(2);
				if (useAI && pid == 1)
					System.out.println("AI selects " + playerInput);
				break;
			}
			// decides if game is over (and who won) based on the sum of the player
			// scores
			int sum = 0;
			for (int i = 0; i < playerAmount; i++)
				sum += players[i].getOwnedFields();

			if (sum == amountOfFields) {
				gameMap.plot();
				int maxID = 0;
				boolean draw = false;
				for (int i = 1; i < playerAmount; i++) {
					if (players[i].getOwnedFields() > players[maxID].getOwnedFields()) {
						maxID = i;
						draw = false;
					} else if (players[i].getOwnedFields() == players[maxID].getOwnedFields()) {
						draw = true;
					}
				}
				if (draw)
					System.out.println("It's a draw. Nobody wins. :( ");
				else {
					System.out.println("Congratulations, " + players[maxID].getName() + ", you won!");
					return;
				}
			}
			
			if (!(useAI && pid == 1)) {
				gameMap.plot();
			}

		} // end of while

	}

	/**
	 * switches the active player
	 * 
	 * @param activePlayer
	 * @return
	 */
	private int switchActivePlayer(int pid) {
		if (pid < playerAmount - 1)
			pid++;
		else
			pid = 0;

		return pid;
	}
}
