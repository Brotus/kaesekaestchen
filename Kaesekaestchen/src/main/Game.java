package main;

import java.util.Scanner;

import entity.Edge;
import entity.Entity;
import entity.FieldStates;
import entity.Map;
import entity.Player;

public class Game {
	
	private int width, height;
	private Player p1, p2;
	private boolean errorMessage = false;
	private static Scanner s = new Scanner(System.in);
	Map gameMap;
	
	Game(int width, int height, Player p1, Player p2){
		this.width = width;
		this.height = height;
		this.p1 = p1;
		this.p2 = p2;
		gameMap = new Map (height, width);
		GameLoop(p1);
	}
	
		
	
	private void GameLoop (Player activePlayer) {
		
		gameMap.plot();
		if(errorMessage){
			System.out.println("edge already selected");
			errorMessage = false;
		}
		System.out.println(activePlayer.getName() + " enter the edge you want to claim");
		int playerInput = s.nextInt();
		FieldStates fs =  gameMap.markEdge(playerInput, activePlayer);
		int numberOfFields = height*width;
		switch (fs) {
			case INVALID :
				errorMessage = true;
				GameLoop (activePlayer);
				break;
		
			case MARKED :
				activePlayer = switchActivePlayer(activePlayer);
				break;
			
			case ONE :
				System.out.println("one ");
				activePlayer.increaseOwnedEdges(1);
				break;
				
			
			case TWO :
				activePlayer.increaseOwnedEdges(2);
				System.out.println("two");
				break;
			}
		System.out.println(p1.getOwnedEdges());
		System.out.println(p2.getOwnedEdges());
		if((p1.getOwnedEdges() + p2.getOwnedEdges()) == numberOfFields){
			if(p1.getOwnedEdges() < p2.getOwnedEdges()){
				System.out.println("congratulations " + p2.getName() + " you won");
			}
			if(p1.getOwnedEdges() > p2.getOwnedEdges()){
				System.out.println("congratulations " + p1.getName() + " you won");
			}
			else 
				System.out.println("its a draw. Nobody wins :( ");
		}
		else GameLoop(activePlayer);
		
	}
	
	private Player switchActivePlayer (Player activePlayer){
		if (activePlayer.equals(p1)){
			return p2;
		}
		else 
			return p1;
		}
	}
	
	
