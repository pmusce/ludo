package gui;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ludo.GameEngine;

public class ActionsPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Label messageLabel;
	private JPanel content;
	private JButton buttonRoll;
	private JButton buttonToken;
	private JButton buttonMove;
	private JButton buttonPass;

	public ActionsPanel() {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		messageLabel = new Label("ActionsPanel");
		this.add(messageLabel);
		
		content = new JPanel();
		
		buttonRoll = new JButton("Roll");
		buttonRoll.setActionCommand("roll");
        buttonRoll.addActionListener(this);
        
        buttonToken = new JButton("Play token");
		buttonToken.setActionCommand("play");
        buttonToken.addActionListener(this);
        
        buttonMove = new JButton("Move");
		buttonMove.setActionCommand("move");
        buttonMove.addActionListener(this);
        
        buttonPass = new JButton("Pass");
		buttonPass.setActionCommand("pass");
        buttonPass.addActionListener(this);
        
        content.add(buttonRoll);
        content.add(buttonToken);
        content.add(buttonMove);
        content.add(buttonPass);
        
        this.add(content);
	}
	
	public void showText(String str) {
		messageLabel.setText(str);
	}

	

	public void showMoveAndPutToken(boolean enableMove, boolean enablePutToken) {
		hideAllButtons();
		buttonMove.setVisible(true);
		buttonToken.setVisible(true);
		
		buttonMove.setEnabled(enableMove);
		buttonToken.setEnabled(enablePutToken);
	}
	
	public void showRoll() {
		hideAllButtons();
		buttonRoll.setVisible(true);
	}
	
	public void showPass() {
		hideAllButtons();
		buttonPass.setVisible(true);
	}

	public void showWaiting() {
		hideAllButtons();
		messageLabel.setText("Not your turn");
	}

	private void hideAllButtons() {
		buttonMove.setVisible(false);
		buttonPass.setVisible(false);
		buttonToken.setVisible(false);
		buttonRoll.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "roll":
			GameEngine.rollDice();
			break;
		case "move":
			
			break;
		case "play":
			GameEngine.playToken();
			break;
		case "pass":
			GameEngine.passTurn();
			break;
		default:
			break;
		}
		
	}
}
