package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import commands.CommandDeliverer;
import ludo.Board;
import ludo.LocalPlayer;

public class GUI {
	private static JFrame waitingRoomFrame;
	private static GUIBoard gBoard;
	private static ActionsPanel actionsPanel;
	private static JPanel usersPanel;
	
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
        waitingRoomFrame = new JFrame("Ludo - " + LocalPlayer.getInstance().getNickname());
        waitingRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waitingRoomFrame.setMinimumSize(new Dimension(100,0));
        
        
        
        JButton buttonReady = new JButton("Ready");
        buttonReady.addActionListener(new ActionListener() {
        	Runnable changer = new Runnable() {
                public void run() {
                	CommandDeliverer.toggleReady();
                }
              };
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(changer).start();
			}
		});
        
        
        
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout());
        buttonBar.add(buttonReady);
        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        
        JPanel waitingRoomPanel = new JPanel();
        waitingRoomPanel.setLayout(new BoxLayout(waitingRoomPanel, BoxLayout.Y_AXIS));
        
        
        waitingRoomPanel.add(buttonBar);
        waitingRoomPanel.add(usersPanel);
		waitingRoomFrame.getContentPane().add(waitingRoomPanel);
        
        //Display the window.
        waitingRoomFrame.pack();
        waitingRoomFrame.setVisible(true);
	}

	public static void updateBoard() {
		gBoard.update();
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
