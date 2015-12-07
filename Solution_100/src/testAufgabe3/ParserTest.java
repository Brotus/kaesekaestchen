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
		testString = "a b c d e f g";
		parser.parseBooleanEx(testString);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongCharacter(){
		testString = "a + &";
		parser.parseBooleanEx(testString);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void wrongNumberOfOperators(){
		testString = "a & &";
		parser.parseBooleanEx(testString);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyString2(){
		testString = null;
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
	
	
	assertEquals(parser.parseBooleanEx(testString), testTree);
	}

	@Test
	public void bigTree(){
		testString = "A B | C D & & E B A | | &"; 
		Node A1 = new Node("A");
		Node B1 = new Node("B");
		Node or1 = new Node ("|", A1, B1);
		Node C = new Node("C");
		Node D = new Node("D");
		Node and1 = new Node("&", C, D);
		Node and2 = new Node("&", or1, and1);
		Node E = new Node("E");
		Node B2 = new Node("B");
		Node A2 = new Node("A");
		Node or2 = new Node ("|", B2, A2);
		Node or3 = new Node ("|", E, or2);
		Node and3 = new Node("&", and2, or3);
		Tree testTree = new Tree(and3);
		
		assertEquals(parser.parseBooleanEx(testString), testTree);
	}
	
	
	
}
