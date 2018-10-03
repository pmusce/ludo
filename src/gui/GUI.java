package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ludo.Board;
import ludo.GameEngine;
import ludo.HumanPlayer;
import ludo.LocalPlayer;
import ludo.Ludo;
import ludo.Player;
import ludo.GameRoom;

public class GUI {
	private static JFrame waitingRoomFrame;
	private static GUIBoard gBoard;
	private static ActionsPanel actionsPanel;
	
	public static void start() {
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	protected static void createAndShowGUI() {
		//Create and set up the window.
        waitingRoomFrame = new JFrame("Ludo - " + LocalPlayer.getColor().toString());
        waitingRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        showConnectedUsers();
        
        JButton buttonReady = new JButton("Ready");
        buttonReady.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HumanPlayer localPlayer =  LocalPlayer.getInstance();
				localPlayer.toggleReady();
				Ludo.updateAll();
				showConnectedUsers();
			}
		});
        
        JButton buttonRoll = new JButton("Roll");
        buttonRoll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GameEngine.rollDice();
			}
		});
        
        JTextField moveInput = new JTextField(4);
        JButton buttonMove = new JButton("Move");
        buttonMove.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int tokenPosition = Integer.parseInt(moveInput.getText());
						GameEngine.moveToken(tokenPosition);
					}
				});
        
        
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout());
        buttonBar.add(buttonReady);
        buttonBar.add(buttonRoll);
        buttonBar.add(moveInput);
        buttonBar.add(buttonMove);
		waitingRoomFrame.getContentPane().add(buttonBar);
        
        //Display the window.
        waitingRoomFrame.pack();
        waitingRoomFrame.setVisible(true);
	}

	public static void showConnectedUsers() {		
		for(Entry<Player, HumanPlayer> player :  GameRoom.getInstance().entrySet()) {
			String output;
			try {
				Player color = player.getKey();
				output = player.getValue().getConnection().isReady() ? "" : "not ";
				System.out.println(color.toString() + " " + player.getValue().getNickname() + ": " + output + "ready");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("----");
	}

	public static GUIBoard createBoardFrame(Board board) {
		gBoard = new GUIBoard(board);
		actionsPanel = new ActionsPanel();
 		JFrame boardFrame = new JFrame("Ludo Board - " + LocalPlayer.getColor().toString());
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.add(gBoard, BorderLayout.CENTER);
        boardFrame.add(actionsPanel, BorderLayout.SOUTH);
        boardFrame.pack();
        boardFrame.setVisible(true);
        return gBoard;
	}
	
	public static void showText(String str) {
		actionsPanel.showText(str);
	}
	
	public static void showMoveAndPutToken(boolean enableMove, boolean enablePutToken) {
		actionsPanel.showMoveAndPutToken(enableMove, enablePutToken);
	}

	public static void showWaiting() {
		actionsPanel.showWaiting();
	}

	public static void showRoll() {
		actionsPanel.showRoll();
	}

	public static void showPass() {
		actionsPanel.showPass();
	}

	public static void enableMovingToken() {
		gBoard.setMoving(true);
		
	}
    
}
