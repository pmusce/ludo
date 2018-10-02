package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Map.Entry;

import javax.swing.JPanel;

import ludo.Board;
import ludo.GameEngine;
import ludo.GameRoom;
import ludo.HumanPlayer;
import ludo.Player;
import ludo.Square;

@SuppressWarnings("serial")
public class GUIBoard extends JPanel {
	private Board board;
	private int squareSize = 30;
	private int offset = 15;
	private Point[] regularCoords;
	private Shape[] regularSquares;
	private Point[] redHomeCoords;
	private Shape[] redHomeSquares;
	private Point[] greenHomeCoords;
	private Shape[] greenHomeSquares;
	private Point[] yellowHomeCoords;
	private Shape[] yellowHomeSquares;
	private Point[] blueHomeCoords;
	private Shape[] blueHomeSquares;
		
	
	public GUIBoard(Board b){
		super();
		this.setPreferredSize(new Dimension(500, 500));
		board = b;
		init();
		addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                for (int i=0; i<regularSquares.length; i++) {
                	Shape s = regularSquares[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                    	//we can either just print out the object class name
                        System.out.println("Clicked " + i);
                        GameEngine.moveToken(i);
                    }
                }
                for (int i=0; i<redHomeSquares.length; i++) {
                	Shape s = redHomeSquares[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                        System.out.println("Clicked red home " + i);
                        GameEngine.moveInsideHomeColumn(i);
                    }
                }
                for (int i=0; i<greenHomeSquares.length; i++) {
                	Shape s = greenHomeSquares[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                        System.out.println("Clicked green home " + i);
                        GameEngine.moveInsideHomeColumn(i);
                    }
                }
                for (int i=0; i<yellowHomeSquares.length; i++) {
                	Shape s = yellowHomeSquares[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                        System.out.println("Clicked yellow home " + i);
                        GameEngine.moveInsideHomeColumn(i);
                    }
                }
                for (int i=0; i<blueHomeSquares.length; i++) {
                	Shape s = blueHomeSquares[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                        System.out.println("Clicked blue home " + i);
                        GameEngine.moveInsideHomeColumn(i);
                    }
                }
            }
		});
	}

	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		paintBoard(g2);
		paintNicknames(g2);
	}
	
	public void paintNicknames(Graphics2D g2) {
		FontMetrics fm = g2.getFontMetrics();
		for(Entry<Player, HumanPlayer> player : GameRoom.getInstance().entrySet()) {
			String nickname = player.getValue().getNickname();
			Player color = player.getKey();
			int x = 0, y = 0;
			
			g2.setColor(color.getColor());
			
			switch (color) {
			case RED:
				y = 9;
				break;
			case GREEN:
				x = 0;
				y = 0;
				break;
			case YELLOW:
				x = 10;
				y = 0;
				break;
			case BLUE:
				x = 10;
				y = 9;
				break;
			}
			g2.drawString(nickname, x * squareSize + offset, y * squareSize + fm.getHeight() + offset);
			if(color.equals(GameEngine.getActivePlayer())) {
				g2.drawString("(playing)", x * squareSize + offset, (y + 1) * squareSize + fm.getHeight() + offset);
			}
		}
	}
	
	private void init() {
		regularCoords = new Point[52];
		
		for(int i = 0; i<6; i++) {			
			regularCoords[i] = new Point(i, 6);
			regularCoords[6+i] = new Point(6, 5-i);
			regularCoords[13+i] = new Point(8, i);
			regularCoords[19+i] = new Point(9+i, 6);
			regularCoords[26+i] = new Point(14-i, 8);
			regularCoords[32+i] = new Point(8, 9+i);
			regularCoords[39+i] = new Point(6, 14-i);
			regularCoords[45+i] = new Point(5-i, 8);
		}
		
		regularCoords[12] = new Point(7, 0);
		regularCoords[25] = new Point(14, 7);
		regularCoords[38] = new Point(7, 14);
		regularCoords[51] = new Point(0, 7);
		
		redHomeCoords = new Point[5];
		greenHomeCoords = new Point[5];
		yellowHomeCoords = new Point[5];
		blueHomeCoords = new Point[5];
		for(int i=0; i<5; i++) {
			redHomeCoords[i] = new Point(1+i, 7);
			greenHomeCoords[i] = new Point(7, 1+i);
			yellowHomeCoords[i] = new Point(13-i, 7);
			blueHomeCoords[i] = new Point(7, 13-i);
		}
		
		createShapes();
	}
	
	

	private void createShapes() {
		regularSquares = new Shape[52];
		for(int i=0; i<regularCoords.length; i++) {
			Point p = regularCoords[i];
			regularSquares[i] = createSquare(p);
		}
		
		redHomeSquares = new Shape[5];
		greenHomeSquares = new Shape[5];
		yellowHomeSquares = new Shape[5];
		blueHomeSquares = new Shape[5];
		for(int i=0; i<redHomeCoords.length; i++) {
			Point p = redHomeCoords[i];
			redHomeSquares[i] = createSquare(p);
			
			p = greenHomeCoords[i];
			greenHomeSquares[i] = createSquare(p);
			
			p = yellowHomeCoords[i];
			yellowHomeSquares[i] = createSquare(p);
			
			p = blueHomeCoords[i];
			blueHomeSquares[i] = createSquare(p);
		}
	}
	
	
	private Shape createSquare(Point p) {
		return new Rectangle(
				squareSize * p.x + offset, 
				squareSize * p.y + offset, 
				squareSize, 
				squareSize
			);
	}

	public void update() {
		repaint();
	}
	
	public void paintBoard(Graphics2D g2) {
		Square[] regulars = board.getRegular();
		for(int i=0; i<regulars.length; i++) {
			paintSquare(g2, regularSquares[i], regulars[i], regularCoords[i], Color.WHITE);
		}
		
		paintHomeColumn(g2, Player.RED, redHomeSquares, redHomeCoords);
		paintHomeColumn(g2, Player.GREEN, greenHomeSquares, greenHomeCoords);
		paintHomeColumn(g2, Player.YELLOW, yellowHomeSquares, yellowHomeCoords);
		paintHomeColumn(g2, Player.BLUE, blueHomeSquares, blueHomeCoords);
	}

	private void paintHomeColumn(Graphics2D g2, Player player, Shape[] squares, Point[] coords) {
		Square[] home = board.getHomes().get(player);
		for(int i=0; i<5; i++) {
			paintSquare(g2, squares[i], home[i], coords[i], player.getColor());
		}
	}
	
	public void paintSquare(Graphics2D g2, Shape shape, Square square, Point coords, Color color) {
		g2.setColor(color);
		g2.fill(shape); 
		g2.setColor(Color.BLACK);
		g2.draw(shape); 
		if(!square.isEmpty()) {
			paintToken(g2, coords, square); 
		}
	}
	
	public void paintToken(Graphics2D g2, Point p, Square s) {
		g2.setColor(s.getColor());
		Shape circle = new Ellipse2D.Double(squareSize * p.x + offset, squareSize * p.y + offset, squareSize, squareSize);
		g2.fill(circle); 
		g2.setColor(Color.BLACK);
		g2.draw(circle);
	}
}
