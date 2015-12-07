package testAufgabe3;

import static org.junit.Assert.*;
import implementierungAufgabe3.Parser;

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

}
