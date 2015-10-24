package entity;

public class Player extends Entity {
	
	private String name;
	private int ownedEdges = 0;
	

	public Player(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public int getOwnedEdges() {
		return ownedEdges;
	}

	public String getName() {
		return name;
	}
	
	String getStrId(){
		return "P" + id;
	}
	
	public void increaseOwnedEdges (int n) throws IllegalArgumentException  {
		if (0 < n && n < 3 ){
			ownedEdges = ownedEdges + n;
			System.out.println("n is " +n);
		}
		else 
			throw new IllegalArgumentException();
	}

}
