package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import game.Game;

public class MainFrame extends javax.swing.JFrame implements ActionListener {
   public static final String TITLE = "PACMAN";
   JMenuBar bar=null;
   JMenuItem startItem=null;
   JMenuItem fasterSlower=null;
   public MainFrame() {

   Game game=Game.getInstance();
   
   this.setTitle(TITLE);
   this.add(game);
   this.setJMenuBar(initMenuBar());
   
   this.setResizable(false);
   this.pack();
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setLocationRelativeTo(null);
   
   //game.start();
   
   
   setVisible(true);
   }
   public JMenuBar initMenuBar() {
	   bar=new JMenuBar();
		JMenu gameJMenu=new JMenu("Game");
		
		 startItem=new JMenuItem("Start Game");
		startItem.setActionCommand("startGame");
		startItem.addActionListener(this);
		
		 fasterSlower=new JMenuItem("Change Dificulty");
		fasterSlower.setActionCommand("fasterSlower");
		fasterSlower.addActionListener(this);
		
		if(!Game.isRunning && !Game.isOutro) {
			startItem.setEnabled(true);
			fasterSlower.setEnabled(false);
		}	
		else if (Game.isRunning) {
			fasterSlower.setEnabled(true);
			startItem.setEnabled(false);
		}
		else {
			startItem.setEnabled(false);
			fasterSlower.setEnabled(false);
			
		}
		JMenuItem endItem=new JMenuItem("End Game");
		gameJMenu.add(startItem);
		gameJMenu.add(endItem);
		endItem.setActionCommand("endGame");
		endItem.addActionListener(this);
		
		bar.add(gameJMenu);
		
		JMenu settingsJMenu=new JMenu("Settings");

		
		settingsJMenu.add(fasterSlower);
		
		bar.add(settingsJMenu);
		
		return bar;
   }
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand()=="startGame") {
		
		if(!Game.isRunning && !Game.isOutro) {
			Game.getInstance().startGame();	
			fasterSlower.setEnabled(true);
		
		}	
		else if (Game.isRunning) {
		
			fasterSlower.setEnabled(true);
		
		}
		
		else {
			Game.getInstance().restart();	
			fasterSlower.setEnabled(true);
			
		}
		
	}
	else if (e.getActionCommand()=="endGame") {
		System.exit(0);
	}
	else if (e.getActionCommand()=="fasterSlower") {
		if(Game.isRunning && !Game.isOutro) {
			
			((JMenuItem)e.getSource()).setEnabled(true);
			if(Game.getInstance().blackGhost.speed==400) {
				Game.getInstance().blackGhost.speed=300;
				Game.getInstance().greenGhost.speed=300;
			}
			else {
				 Game.getInstance().blackGhost.speed=400;
					Game.getInstance().greenGhost.speed=400;
			}
			}
			else {
				((JMenuItem)e.getSource()).setEnabled(true);
			}
	
	
	}
		
}

}