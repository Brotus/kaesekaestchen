package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Map;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.Player;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI.AI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI.AdvancedAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.AI.SimpleAI;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.ChineseWallStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EarthQuakeStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.EmptyStrategy;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FancyHandle;
import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex11.entity.fancy.FloodingStrategy;

/**
 * 
 * This class handles the UI (console) and runs the game loop.
 *
 */
public class Game {

	private int width, height;
	private Player[] players;
	private int playerAmount;
	private static Scanner s = new Scanner(System.in, "UTF-8");
	private Map gameMap;

	/** true iff a player is controlled by AI */
	private boolean useAI;
	/** true iff there is an auxiliary AI is available to the player */
	private boolean auxAIAvailable;

	private boolean beFancy;

	/**
	 * Start the game.
	 */
	public Game() {
		// width and height need to be entered here because Game.init() needs
		// gameMap
		System.out.println("This application will ignore whitespaces in your input.");

		// readme/help prompt
		if (parseInput("Do you want to read the ReadMe?", "[yn]").equals("y")) {
			/*
			 * try { String content = new
			 * String(Files.readAllBytes(Paths.get(Main
			 * .class.getResource("/src/main/resources/readme.txt").toURI())),
			 * "UTF-8"); System.out.println(content); } catch (IOException |
			 * URISyntaxException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			// System.out.println(System.getProperty("user.dir"));
			try {
				BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("readme.txt")));
				String line;
				while ((line = txtReader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			} catch (NullPointerException nEx) {
				System.out.println("Error");
			}

		}
		width = Integer.parseInt(parseInput("Enter the width of the board:", "[1-9]+\\d*"));
		height = Integer.parseInt(parseInput("Enter the height of the board:", "[1-9]+\\d*"));

		beFancy = parseInput("Do you enjoy fanciness?", "[yn]").equals("y");
		FancyHandle fancy;
		if (beFancy) {
			fancy = getFancyStrategy(new Random().nextInt(2));
		} else {
			fancy = getFancyStrategy(-1);
		}
		gameMap = new Map(height, width, fancy);

		init();

		MainLoop();
	}

	private FancyHandle getFancyStrategy(int strategyID) {
		// strategy has to be chosen when map is created
		switch (strategyID) {
		case 0:
			return new ChineseWallStrategy();
		case 1:
			return new EarthQuakeStrategy();
		case 2:
			return new FloodingStrategy();
		default:
			return new EmptyStrategy();
		}
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
		} else if (beFancy) {
			boolean fancyVisible = parseInput("Shall the wall causing fancy events be visible?", "[yn]").equals("y");
			if (fancyVisible) {
				gameMap.setFancyVisible();
				System.out.println("Boring. Watch out for the @.");
			}
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
				input = parseInput(players[pid].getName() + ", enter the edge you want to claim, 'help' or 'quit':", "(\\d+)|(help)|(quit)", p -> {
					if (p.equals("quit")) {
						System.exit(0);
						return false;
					} else if (p.equals("help"))
						return true;
					else
						try {
							int n = Integer.parseInt(p);
							return 0 <= n && n < gameMap.getEdgeCount();
						} catch (Exception e) {
							return false;
						}
				});
			} else {
				input = parseInput(players[pid].getName() + ", enter the edge you want to claim or 'quit':", "(\\d+)|(quit)", p -> {
					if (p.equals("quit")) {
						System.exit(0);
						return false;
					} else {
						int n = Integer.parseInt(p);
						return 0 <= n && n < gameMap.getEdgeCount();
					}
				});
			}

			if (auxAIAvailable && input.equals("help")) {
				System.out.println("AI suggests: " + players[pid].getTurn());
				continue;
			} else
				playerInput = Integer.parseInt(input);

			int fs = gameMap.markEdge(playerInput, players[pid]);

			if (fs == -1) {
				System.out.println("This edge is already selected.");
				continue;
			} else if (fs == 0) {

				humanTurn = gameMap.anotherTurn();
			} else if (fs > 0) {
				if (checkEnd())
					return true;
				else
					gameMap.plot();
				humanTurn = gameMap.anotherTurn();
			}
		}

		return false;

	}

	/**
	 * @return true if the game should be ended, false if it should keep running
	 */
	private boolean checkEnd() {
		return gameMap.isEnd();
	}

	private void endGame() {
		LinkedList<Player> winningPlayer = gameMap.getWinner();
		if (winningPlayer.size() > 1)
			System.out.println("It's a draw. Nobody wins. :( ");
		else {
			System.out.println("Congratulations, " + winningPlayer.get(0) + ", you won!");
		}
	}

	private boolean AILoopIteration() {
		int AIindex = 1;
		int turn;
		boolean AITurn = true;

		while (AITurn) {
			turn = players[AIindex].getTurn();
			int fs = gameMap.markEdge(turn, players[AIindex]);

			while (fs == -1) {
				turn = players[AIindex].getTurn();
				fs = gameMap.markEdge(turn, players[AIindex]);
			}

			AITurn = gameMap.anotherTurn();
			System.out.println("AI selects " + turn + ".");

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
