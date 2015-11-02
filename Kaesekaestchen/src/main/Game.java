package main;

import java.util.Scanner;
import java.util.function.Predicate;

import entity.FieldStates;
import entity.Map;
import entity.Player;
import entity.AI.MinMaxAI;

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
	// AI to help the player when he enters 'help', 'aux' stands for auxiluary
	private MinMaxAI auxAI;

	/**
	 * Start the game.
	 */
	Game() {
		init();

		gameMap = new Map(height, width);
		
		auxAI = new MinMaxAI(gameMap);
		
		GameLoop(0);
	}

	/**
	 * Make the users enter their names and the size of the map.
	 */
	private void init() {
		System.out.println("Application will ignore whitespaces.");
		String str;
		playerAmount = Integer.parseInt(parseInput("Enter the amount of players (1 == human against AI):", "[1-9]+"));
		useAI = playerAmount == 1;
		players = new Player[playerAmount + (useAI?1:0)];
		for (int i = 1; i <= playerAmount; i++) {
			str = parseInput("Enter the name of player P" + i, "[a-zA-Z]+\\w*");
			players[i-1] = new Player(str, i);
		}
		// if(useAI) players[1] = new AI()
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
	 * @param pid the index of the active player in players
	 */
	private void GameLoop(int pid) {
		gameMap.plot();
		int numberOfFields = height * width;

		if (errorMessage) {
			System.out.println("This edge is already selected.");
			errorMessage = false;
		}

		// prompts the user to enter a valid edge
		int playerInput = Integer.parseInt(parseInput(players[pid].getName() + ", enter the edge you want to claim or 'help':", "\\d+", p -> {
			int n = Integer.parseInt(p);
			return 0 <= n && n < gameMap.getEdgeCount();
		}, "Input too high or too low."));

		// players have to enter again if edge was already claimed, if they get
		// a point (or two) they get an additional turn
		FieldStates fs = gameMap.markEdge(playerInput, players[pid]);
		switch (fs) {
		case INVALID:
			errorMessage = true;
			GameLoop(pid);
			break;

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
		for(int i = 0; i < playerAmount; i++)
			sum += players[i].getOwnedFields();
		
		if(sum == numberOfFields){
			int maxID = 0;
			boolean draw = false;
			for(int i = 1; i < playerAmount; i++){
				if(players[i].getOwnedFields() > players[maxID].getOwnedFields()){
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
		}else
			GameLoop(pid);
	}

	/**
	 * switches the active player
	 * 
	 * @param activePlayer
	 * @return
	 */
	private int switchActivePlayer(int pid) {
		if(pid < playerAmount -1)
			pid++;
		else 
			pid = 0;
		
		return pid;
	}
}
