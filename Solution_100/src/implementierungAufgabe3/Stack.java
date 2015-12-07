package implementierungAufgabe3;

import java.util.ArrayList;
import java.util.List;

public class Stack {
	
	public List<Node> stack;
	public int elems;
	
	public Stack(){
		stack = new ArrayList<Node>();
		elems = -1; // -1: empty, 0: one element
	}

	
	public void push(Node n){
		stack.add(n);
		elems++;
	}
	
	public Node pop(){
		return (elems == -1) ? null : stack.remove(elems--);
	}
	
	public Tree toTree(){
		if(elems == 0)
			return new Tree(stack.remove(0));
		else return null;
	}
}
