package testAufgabe3;

import static org.junit.Assert.*;
import implementierungAufgabe3.Node;
import implementierungAufgabe3.Parser;
import implementierungAufgabe3.Tree;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {
	
	String testString;
	Parser parser = new Parser();

	@Test(expected = IllegalArgumentException.class)
	public void emptyString() {
		testString = "";
		parser.parseBooleanEx(testString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noVariable() {
		testString = "&&|";
		parser.parseBooleanEx(testString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void noConjunction(){
		testString = "abcdefg";
		parser.parseBooleanEx(testString);
	}
	
	@Test 
	public void simpleTree(){
	testString = "A B C & |";
	Node A = new Node("A",null , null);
	Node B = new Node("B", null, null);
	Node C = new Node("C", null, null);
	Node and = new Node("&", B, C);
	Node or = new Node("|", A, and);
	Tree testTree = new Tree(or);
	
	System.out.println(testTree.traverse());
	System.out.println(parser.parseBooleanEx(testString).traverse());
	
	assertEquals(parser.parseBooleanEx(testString), testTree);
	}

}
