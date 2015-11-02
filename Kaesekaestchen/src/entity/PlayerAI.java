package entity;

import entity.AI.AI;

public class PlayerAI extends Player{
	
	private AI ai;

	public PlayerAI(String name, int id, AI ai) {
		super(name, id);
		this.ai = ai;
	}
	
	public int suggestTurn(){
		return ai.suggestTurn();
	}

}
