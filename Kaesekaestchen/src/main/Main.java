package main;

import entity.Edge;
import entity.Entity;
import entity.Field;

public class Main {
	
	public static void main(String[] args){
		Entity[] e = new Entity[10];
		e[0] = new Edge();
		e[1] = new Field();
		
		System.out.println(e[0]);
		System.out.println(e[1]);
	}

}
