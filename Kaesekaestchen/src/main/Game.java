package main;

import entity.Edge;
import entity.Entity;

public class Game {
	private Entity[][] entities;
	private Edge[] edges;
	
	private int width, height;
	
	Game(int width, int height){
		this.width = width;
		this.height = height;
	}
}
