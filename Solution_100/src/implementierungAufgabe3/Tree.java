package implementierungAufgabe3;

public class Tree {
	public Node root;
	public Tree(Node root){
		this.root = root;
	}
	
	public String traverse(){
		return traverse(root);
	}
	
	private String traverse(Node root){
		String str = root.s;
		if(!(root.r == null || root.r == null)){
			str = str + "(" + traverse(root.l) + ", "+ traverse(root.r) + ")";
		}
		return str;
	}
	
	@Override
	public boolean equals(Object t){
		return t != null && t instanceof Tree && this.traverse().equals(((Tree)t).traverse());
	}
}
