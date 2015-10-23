package main;

import entity.Edge;
import entity.Entity;
import entity.Player;

public class Game {
	private Entity[][] entities;
	private Edge[] edges;
	
	private int width, height;
	private Player p1, p2;
	
	Game(int width, int height, Player p1, Player p2){
		this.width = width;
		this.height = height;
		this.p1 = p1;
		this.p2 = p2;
	}
}
