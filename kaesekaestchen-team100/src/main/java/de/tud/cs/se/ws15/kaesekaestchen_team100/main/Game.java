package de.tud.cs.se.ws15.kaesekaestchen_team100.main;

import java.util.Scanner;
import java.util.function.Predicate;

import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.FieldStates;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.AI.AI;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.AI.AdvancedAI;
import de.tud.cs.se.ws15.kaesekaestchen_team100.entity.AI.SimpleAI;

/**
 * 
 * This class handles the UI (console) and runs the game loop.
 *
 */
public class Game {

	private int width, height;
	private Player[] players;
	private int playerAmount;
	private static Scanner s = new Scanner(System.in);
	private Map gameMap;
	private boolean useAI;
	private boolean auxAIAvailable;

	/**
	 * Start the game.
	 */
	public Game() {
		// width and height need to be entered here because Game.init() needs
		// gameMap
		System.out.println("Application will ignore whitespaces.");
		width = Integer.parseInt(parseInput("Enter the width of the board:", "[1-9]+\\d*"));
		height = Integer.parseInt(parseInput("Enter the height of the board:", "[1-9]+\\d*"));

		gameMap = new Map(height, width);

		init();

		MainLoop();
	}

	/**
	 * Input of - amount of players - names of players - AI types
	 */
	private void init() {
		String str;
		playerAmount = Integer.parseInt(parseInput("Enter the amount of players (1 == human against AI):", "[1-9]+\\d*"));
		useAI = playerAmount == 1;
		auxAIAvailable = playerAmount <= 2;
		players = new Player[playerAmount + (useAI ? 1 : 0)];
		for (int i = 1; i <= playerAmount; i++) {
			str = parseInput("Enter the name of player P" + i, "[a-zA-Z]+\\w*");
			players[i - 1] = new Player(str, i, auxAIAvailable ? new AdvancedAI(gameMap) : null, true);
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
				ai = new AdvancedAI(gameMap);
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
		return parseInput(prompt, matcher, x -> true);
	}

	private String parseInput(String prompt, String matcher, Predicate<String> p) {
		return parseInput(prompt, matcher, p, "Input has to match " + matcher + ".");
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

	private void MainLoop() {
		int pid = 0;
		boolean running = true;

		gameMap.plot();

		while (running) {
			if (useAI && pid == 1) {
				running = !AILoopIteration();
				pid = 0;
			} else {
				running = !HumanLoopIteration(pid);
				pid = switchActivePlayer(pid);
			}
			gameMap.plot();
		}

		endGame();

	}

	private boolean HumanLoopIteration(int pid) {
		String input;
		int playerInput;
		boolean humanTurn = true;

		while (humanTurn) {
			if (auxAIAvailable) {
				input = parseInput(players[pid].getName() + ", enter the edge you want to claim or 'help':", "(\\d+)|(help)", p -> {
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
				});
			} else {
				input = parseInput(players[pid].getName() + ", enter the edge you want to claim:", "\\d+", p -> {
					int n = Integer.parseInt(p);
					return 0 <= n && n < gameMap.getEdgeCount();
				});
			}

			if (auxAIAvailable && input.equals("help")) {
				System.out.println("AI suggests: " + players[pid].getTurn());
				continue;
			} else
				playerInput = Integer.parseInt(input);

			FieldStates fs = gameMap.markEdge(playerInput, players[pid]);
			switch (fs) {
			case INVALID:
				System.out.println("This edge is already selected.");
				continue;
			case MARKED:
				humanTurn = false;
				break;
			case ONE:
				players[pid].increaseOwnedFields(1);
				gameMap.plot();
				if (checkEnd())
					return true;
				break;
			case TWO:
				players[pid].increaseOwnedFields(2);
				gameMap.plot();
				if (checkEnd())
					return true;
				break;
			}
		}

		return false;

	}

	/**
	 * 
	 * @return true if the game should be ended, false if it should keep running
	 */
	private boolean checkEnd() {
		int sum = 0;
		for (int i = 0; i < playerAmount; i++)
			sum += players[i].getOwnedFields();

		return sum == height * width;
	}

	private void endGame() {
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
		}
	}

	private boolean AILoopIteration() {
		int AIindex = 1;
		int turn;
		boolean AITurn = true;

		while (AITurn) {
			turn = players[AIindex].getTurn();
			FieldStates fs = gameMap.markEdge(turn, players[AIindex]);

			while (fs == FieldStates.INVALID) {
				turn = players[AIindex].getTurn();
				fs = gameMap.markEdge(turn, players[AIindex]);
			}

			switch (fs) {
			case MARKED:
				AITurn = false;
				System.out.println("AI selects " + turn + ".");
				break;
			case ONE:
				players[AIindex].increaseOwnedFields(1);
				System.out.println("AI selects " + turn + ".");
				if (checkEnd())
					return true;
				break;
			case TWO:
				players[AIindex].increaseOwnedFields(2);
				System.out.println("AI selects " + turn + ".");
				if (checkEnd())
					return true;
				break;
			default:
				if (!(fs == FieldStates.MARKED))
					System.out.println("Unexpected switch case (INVALID)");
			}
		}

		return false;
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
