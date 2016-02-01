package de.tud.cs.se.ws15.kaesekaestchen_team100.entity;

/**
 * A simple Enumeration to differ States within the game loop.
 */

public enum FieldStates {
	/**
	 * Edge has already been marked.
	 */
	INVALID, 
	
	ONE,
	TWO,
	MARKED(-1){
		
	};
	
	FieldStates(){
		
	}
		
	FieldStates(int amount){
		this.amount = amount;
	}
	
	private int amount;
	
	public int getAmount(){
		return amount;
	}

	public static FieldStates MARKED(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
