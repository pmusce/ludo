package ludo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;

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
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        frame.getContentPane().add(buttonReady);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
		
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

    
}
