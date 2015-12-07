package implementierungAufgabe3;

public class Parser {
	
	public Tree parseBooleanEx(String s) throws IllegalArgumentException{
		Stack stack = new Stack();
		
		String[] sArr = s.split(" ");
		for(String t: sArr){
			if(t.matches("[A-Za-z]+")){
				stack.push(new Node(t));
			} else if(t.matches("[&|]")){
				if(stack.elems < 1){ // less than 2 elements
					throw new IllegalArgumentException("Found boolean operator with less than two arguments");
				}
				Node n2 = stack.pop();
				Node n1 = stack.pop();
				stack.push(new Node(t, n1, n2));
			} else {
				throw new IllegalArgumentException("Found invalid character");
			}
		}
		
		return stack.toTree();
	}

}
