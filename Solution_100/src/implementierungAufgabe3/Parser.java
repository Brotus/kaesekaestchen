package implementierungAufgabe3;

public class Parser {
	
	public Tree parseBooleanEx(String s) throws IllegalArgumentException{
		String[] sArr = s.split(" ");
		for(String t: sArr){
			if(t.matches("[A-Za-z]+")){
				
			} else if(t.matches("[&|]")){
				
			} else {
				throw new IllegalArgumentException();
			}
		}
		Stack stack = new Stack();
		return stack.toTree();
	}

}
