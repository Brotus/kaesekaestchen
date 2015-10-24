package entity;

public class Player {
	
	private String name;
	public int ownedEdges = 0;
	
	public int getOwnedEdges() {
		return ownedEdges;
	}

	public void setOwnedEdges(int ownedEdges) {
		this.ownedEdges = ownedEdges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player(String name){
		this.name = name;
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
