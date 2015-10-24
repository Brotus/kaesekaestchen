package main;

import java.util.Scanner;

import entity.Player;

public class Main {

	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		init();
	}

	private static void init() {
		System.out.println("Enter the name of player P1:");
		Player p1 = new Player(s.next(), 1);
		System.out.println("Enter the name of player P2:");
		Player p2 = new Player(s.next(), 2);
		System.out.println("Enter the width of the board:");
		int width = s.nextInt();
		System.out.println("Enter the height of the board:");
		int height = s.nextInt();

		new Game(width, height, p1, p2);
	}

}
