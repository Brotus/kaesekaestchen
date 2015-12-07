package implementierungAufgabe3;

public class Node {
	
	public String s;
	public Node l, r;
	
	public Node(String s){
		this.s = s;
		this.l = null;
		this.r = null;
	}
	
	public Node(String s, Node l, Node r){
		this.s = s;
		this.l = l;
		this.r = r;
	}
	
	@Override
	public String toString(){
		return s;
	}

}
