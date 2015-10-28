package main;

import java.util.Scanner;

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
	Game(){
		init();

		gameMap = new Map(height, width);
		GameLoop(p1);
	}
	
	/**
	 * Make the users enter their names and the size of the map.
	 */
	private void init(){
		System.out.println("Enter the name of player P1:");
		p1 = new Player(s.next(), 1);
		System.out.println("Enter the name of player P2:");
		p2 = new Player(s.next(), 2);
		System.out.println("Enter the width of the board:");
		width = s.nextInt();
		System.out.println("Enter the height of the board:");
		height = s.nextInt();
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

		System.out.println(activePlayer.getName() + ", enter the edge you want to claim.");
		int playerInput = s.nextInt();

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
