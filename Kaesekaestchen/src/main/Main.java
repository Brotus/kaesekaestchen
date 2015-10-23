package main;

import java.util.Scanner;

import entity.Player;

public class Main {
	
	private static Scanner s = new Scanner(System.in);
	private static Game game;
	
	public static void main(String[] args){
		init();
	}
	
	private static void init(){
		System.out.println("Name of Player 1:");
		Player p1 = new Player(s.next());
		System.out.println("Name of Player 2:");
		Player p2 = new Player(s.next());
		System.out.println("Width of the board:");
		int width = s.nextInt();
		System.out.println("Height of the board:");
		int height = s.nextInt();
		
		game = new Game(width, height, p1, p2);
	}

}
