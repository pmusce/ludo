package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ludo.Board;
import ludo.GameEngine;
import ludo.HumanPlayer;
import ludo.LocalPlayer;
import ludo.Ludo;
import ludo.LudoInterface;
import ludo.PlayersMap;

public class GUI {
	
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
        JFrame actionsFrame = new JFrame("Ludo");
        actionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
		actionsFrame.getContentPane().add(buttonBar);
        
        //Display the window.
        actionsFrame.pack();
        actionsFrame.setVisible(true);
	}

	public static void showConnectedUsers() {
		
		PlayersMap players = PlayersMap.getInstance();
		
		for(String nickname : players.keySet()) {
			try {
				LudoInterface playerState = (LudoInterface) players.get(nickname);
				String output = playerState.isReady() ? "" : "not ";
				System.out.println(nickname + ": " + output + "ready");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("----");
	}

	public static GUIBoard createBoardFrame(Board board) {
		GUIBoard gBoard = new GUIBoard(board);
		JFrame boardFrame = new JFrame("Ludo Board");
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setSize(500,500);
        boardFrame.add(gBoard);
        boardFrame.setVisible(true);
        return gBoard;
	}

    
}
