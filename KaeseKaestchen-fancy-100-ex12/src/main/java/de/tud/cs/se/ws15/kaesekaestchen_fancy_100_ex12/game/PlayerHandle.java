package de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.game;

import de.tud.cs.se.ws15.kaesekaestchen_fancy_100_ex12.entity.Player;

public class PlayerHandle {
	private Player[] players;
	private int size;
	/* the position of the currently active player */
	private int pid;
	private boolean useAI;
	
	
	public PlayerHandle(int theSize){
		size = theSize;
		if(size == 1){
			size++;
			useAI = true;
		} else {
			useAI = false;
		}
		players = new Player[size];
	}
	
	public void set(Player p, int pos){
		players[pos] = p;
	}
	
	public Player get(int pos){
		return players[pos];
	}
	
	public Player getActive(){
		return get(pid);
	}
	
	public boolean AITurn(){
		return useAI && pid == 1;
	}
	
	public boolean useAI(){
		return useAI;
	}
	
	public void switchActivePlayer(){
		if (pid < size - 1)
			pid++;
		else
			pid = 0;
	}
	
	public int getSize(){
		return size;
	}
	
}
