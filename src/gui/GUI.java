package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ludo.Board;
import ludo.HumanPlayer;
import ludo.LocalPlayer;
import ludo.Ludo;
import ludo.Player;
import ludo.GameRoom;

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
        waitingRoomFrame = new JFrame("Ludo - " + LocalPlayer.getColor().toString());
        waitingRoomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waitingRoomFrame.setMinimumSize(new Dimension(100,0));
        
        
        
        JButton buttonReady = new JButton("Ready");
        buttonReady.addActionListener(new ActionListener() {
        	Runnable changer = new Runnable() {
                public void run() {
                	HumanPlayer localPlayer =  LocalPlayer.getInstance();
    				localPlayer.toggleReady();
    				Ludo.updateAll();
    				showConnectedUsers();
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
        //showConnectedUsers();
        
        JPanel waitingRoomPanel = new JPanel();
        waitingRoomPanel.setLayout(new BoxLayout(waitingRoomPanel, BoxLayout.Y_AXIS));
        
        
        waitingRoomPanel.add(buttonBar);
        waitingRoomPanel.add(usersPanel);
		waitingRoomFrame.getContentPane().add(waitingRoomPanel);
        
        //Display the window.
        waitingRoomFrame.pack();
        waitingRoomFrame.setVisible(true);
	}

	private static void showConnectedUsers() {
		usersPanel.add(new JLabel("Prova"));
		for(Entry<Player, HumanPlayer> player :  GameRoom.getInstance().entrySet()) {
			String output;
			try {
				Player color = player.getKey();
				output = color.toString() + " " + player.getValue().getNickname() + ": ";
				output += player.getValue().getConnection().isReady() ? "" : "not ";
				output += "ready";
				
				System.out.println(output);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		waitingRoomFrame.revalidate();
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
