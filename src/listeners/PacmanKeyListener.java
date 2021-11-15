package listeners;

import game.*;
import java.lang.annotation.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import game.Player;

public class PacmanKeyListener implements java.awt.event.KeyListener {
   Player player;
   
   /** @param player */
   public PacmanKeyListener(Player player) {
   	this.player=player;
   }
   
   public PacmanKeyListener() {
	// TODO Auto-generated constructor stub
}

/** @param e */
   @Override
   public void keyTyped(KeyEvent e) {
	   if(e.getKeyCode()==KeyEvent.VK_LEFT)
	   		Game.getInstance().playerPac.dir='L';
	   	if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	   		Game.getInstance().playerPac.dir='R';
	   	if(e.getKeyCode()==KeyEvent.VK_UP) {
	   		Game.getInstance().playerPac.dir='U';
	   		
	   	}
	   	if(e.getKeyCode()==KeyEvent.VK_DOWN) {
	   		Game.getInstance().playerPac.dir='D';
	  
	   	}	
   }
   
   /** @param e */
   @Override
   public void keyPressed(KeyEvent e) {
   
   	if(e.getKeyCode()==KeyEvent.VK_LEFT)
   		Game.getInstance().playerPac.dir='L';
   	if(e.getKeyCode()==KeyEvent.VK_RIGHT)
   		Game.getInstance().playerPac.dir='R';
   	if(e.getKeyCode()==KeyEvent.VK_UP) {
   		Game.getInstance().playerPac.dir='U';
  
   	}
   	if(e.getKeyCode()==KeyEvent.VK_DOWN) {
   		Game.getInstance().playerPac.dir='D';
  
   	}	
   	
   }
   
   /** @param e */
   @Override
   public void keyReleased(KeyEvent e) {
   
   	
   }

}