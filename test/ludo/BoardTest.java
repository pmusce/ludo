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
	
	@Test
	public void testMoveAToken() {
		board.putIn(Player.RED);
		board.move(Player.RED, 0, 3);
		
		String expected = "3-4-4-4" + "\n";
		expected += "...r........." + "............." + "............." + "............." + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMoveATokenAroundTheBoard() {
		board.putIn(Player.BLUE);
		
		int position = Player.BLUE.getStartingSquare();
		position = board.move(Player.BLUE, position, 6);
		position = board.move(Player.BLUE, position, 5);
		board.move(Player.BLUE, position, 4);
		
		
		String expected = "4-4-4-3" + "\n";
		expected += "..b.........." + "............." + "............." + "............." + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGoesIntoHomeColumn() {
		board.putIn(Player.GREEN);
		
		int position = Player.GREEN.getStartingSquare();
		position = board.move(Player.GREEN, position, 50);
		board.move(Player.GREEN, position, 4);
		
		
		String expected = "4-3-4-4" + "\n";
		expected += "............." + "............." + "............." + "............." + "\n";
		expected += "....." + "..g.." + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRedGoesInHomeColumn() {
		Player p = Player.RED;
		board.putIn(p);
		
		int position = p.getStartingSquare();
		position = board.move(p, position, 50);		
		
		assertTrue(board.isGoingToHomeColumn(p, position, 4));
	}
	
	@Test
	public void testBlueGoesInHomeColumn() {
		Player p = Player.BLUE;
		board.putIn(p);
		
		int position = p.getStartingSquare();
		position = board.move(p, position, 50);		
		
		assertTrue(board.isGoingToHomeColumn(p, position, 4));
	}
	
	@Test
	public void testMoveFromHomeColumn() {
		Player p = Player.GREEN;
		board.putIn(p);
		int position = p.getStartingSquare();
		position = board.move(p, position, 50);	
		position = board.move(p, position, 4);
		
		board.moveInsideHomeColumn(p, position, 2);
		
		String expected = "4-3-4-4" + "\n";
		expected += "............." + "............." + "............." + "............." + "\n";
		expected += "....." + "....g" + "....." + "....." + "\n";
		expected += "0-0-0-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}

	@Test(expected = MoveNotAllowedException.class)
	public void testCannotMoveInHomeColumnWithTooManySteps() {
		Player p = Player.YELLOW;
		board.putIn(p);
		int position = p.getStartingSquare();
		position = board.move(p, position, 50);	
		position = board.move(p, position, 4);
		
		board.moveInsideHomeColumn(p, position, 5);
	}
	
	@Test
	public void testGoesToFinishingLine() {
		Player p = Player.YELLOW;
		board.putIn(p);
		int position = p.getStartingSquare();
		position = board.move(p, position, 50);	
		position = board.move(p, position, 4);
		
		board.moveInsideHomeColumn(p, position, 3);
		
		String expected = "4-4-3-4" + "\n";
		expected += "............." + "............." + "............." + "............." + "\n";
		expected += "....." + "....." + "....." + "....." + "\n";
		expected += "0-0-1-0";
		
		String actual = board.toString();
		assertEquals(expected, actual);
	}
}
