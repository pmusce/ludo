package gui;

import java.awt.BasicStroke;
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
import java.util.EnumMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import ludo.Board;
import ludo.GameEngine;
import ludo.GameRoom;
import ludo.HumanPlayer;
import ludo.LocalPlayer;
import ludo.Player;
import ludo.Square;

@SuppressWarnings("serial")
public class GUIBoard extends JPanel {
	private Board board;
	private int squareSize = 30;
	private int offset = 15;
	private boolean isMoving;
	private Point[] regularCoords;
	private Shape[] regularSquares;
	private EnumMap<Player, Point[]> homeCoords;
	private EnumMap<Player, Shape[]> homeSquares;
		
	
	public GUIBoard(Board b){
		super();
		this.setPreferredSize(new Dimension(500, 500));
		isMoving = false;
		board = b;
		init();
		addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                
                if(!isMoving) return;
                
                for (int i=0; i<regularSquares.length; i++) {
                	Shape s = regularSquares[i];
                    if (s.contains(me.getPoint()) && board.getRegular()[i].isTokenOfPlayer(LocalPlayer.getColor())) {//check if mouse is clicked within shape
                    	//we can either just print out the object class name
                        System.out.println("Clicked " + i);
                        GameEngine.moveToken(i);
                        isMoving = false;
                    }
                }
            	for (int i=0; i<5; i++) {
                	Shape s = homeSquares.get(LocalPlayer.getColor())[i];
                    if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                        System.out.println("Clicked "+ LocalPlayer.getColor().toString() + " home " + i);
                        GameEngine.moveInsideHomeColumn(i);
                        isMoving = false;
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
		paintFinish(g2);
	}
	
	private void paintFinish(Graphics2D g2) {
		FontMetrics fm = g2.getFontMetrics();
		for(Entry<Player, HumanPlayer> player : GameRoom.getInstance().entrySet()) {
			Player color = player.getKey();
			float x = 7, y = 7;
			
			g2.setColor(color.getColor());
			
			switch (color) {
			case RED:
				x = 6;
				break;
			case GREEN:
				y = 6;
				break;
			case YELLOW:
				x = 8;
				break;
			case BLUE:
				y = 8;
				break;
			}
			
			int finishTokens = board.getFinishing().get(color);
			g2.drawString(Integer.toString(finishTokens), x * squareSize + 15 + offset, y * squareSize + fm.getHeight() + offset);
			
		}
		
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
			
			if(color.equals(GameEngine.getActivePlayer())) {
				nickname = nickname + " (playing)";
			}
			g2.drawString(nickname, x * squareSize + offset, y * squareSize + fm.getHeight() + offset);
			int availableTokens = board.getStarting().get(color);
			g2.drawString("Available tokens: " + availableTokens, x * squareSize + offset, (y + 1) * squareSize + fm.getHeight() + offset);
			
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
		
		homeCoords = new EnumMap<Player,Point[]>(Player.class);
		homeCoords.put(Player.RED, new Point[5]);
		homeCoords.put(Player.GREEN, new Point[5]);
		homeCoords.put(Player.YELLOW, new Point[5]);
		homeCoords.put(Player.BLUE, new Point[5]);
		for(int i=0; i<5; i++) {
			homeCoords.get(Player.RED)[i] = new Point(1+i, 7);
			homeCoords.get(Player.GREEN)[i] = new Point(7, 1+i);
			homeCoords.get(Player.YELLOW)[i] = new Point(13-i, 7);
			homeCoords.get(Player.BLUE)[i] = new Point(7, 13-i);
		}
		
		createShapes();
	}
	
	

	private void createShapes() {
		regularSquares = new Shape[52];
		for(int i=0; i<regularCoords.length; i++) {
			Point p = regularCoords[i];
			regularSquares[i] = createSquare(p);
		}
		
		homeSquares = new EnumMap<Player,Shape[]>(Player.class);
		for(Player player : Player.values()) {
			homeSquares.put(player, new Shape[5]);
			for(int i=0; i<5; i++) {
				Point p = homeCoords.get(player)[i];
				homeSquares.get(player)[i] = createSquare(p);
			}
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
		
		for(Player player : Player.values()) {
			paintHomeColumn(g2, player, homeSquares.get(player), homeCoords.get(player));
		}
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
		if(isMoving && s.isTokenOfPlayer(LocalPlayer.getColor())) {
			g2.setStroke(new BasicStroke(3));
			g2.draw(circle);
			g2.setStroke(new BasicStroke());
			g2.setColor(Color.WHITE);	
		}
		g2.draw(circle);
	}
	
	public void setMoving(boolean b) {
		isMoving = b;
		this.repaint();
	}
	
}
