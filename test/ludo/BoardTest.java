package ludo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testInit() {
		String expected = "4-4-4-4" + "\n";
		expected += "............." + "............." + "............." + "............." + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRedPlaysAToken() {
		String expected = "3-4-4-4" + "\n";
		expected += "r............" + "............." + "............." + "............." + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		board.putIn(Player.RED);
		String actual = board.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRedAndBluePlayAToken() {
		String expected = "3-4-4-3" + "\n";
		expected += "r............" + "............." + "............." + "b............" + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		board.putIn(Player.RED);
		board.putIn(Player.BLUE);
		String actual = board.toString();
		assertEquals(expected, actual);
	}

}
