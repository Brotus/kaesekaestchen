package implementierungAufgabe3;

public class Run {
	public static void main(String[] args){
		Parser p = new Parser();
		String str = "a b & c | e f | &";
		System.out.println(p.parseBooleanEx(str).traverse());
	}
}
