package main;

import javax.swing.SwingUtilities;

import game.Game;
import view.MainFrame;

public class MainClass {
   /** @param args */
   public static void main(String[] args) {    	
  
   	SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
		new MainFrame();
			
		}
	});
   	
   
   }

}