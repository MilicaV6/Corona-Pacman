package view;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import game.Game;

public class FaceAnimation extends Thread {

 
int delta=Game.delta;

 @Override
	public  void run() {
	if(delta==0)
		delta=10;
	 while(Game.isRunning) {
		 
		  for (int i = 0; i < Game.walls.size(); i+=delta) {
				Game.walls.get(i).setIcon(new ImageIcon(new ImageIcon("resources/worried.png")
	   			.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
			}
		  for (int i = 0; i < Game.walls.size(); i+=delta) {
			Game.walls.get(i).setIcon(new ImageIcon(new ImageIcon("resources/worried.png")
   			.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		}
		 try {
			sleep(new Random().nextInt(4)*100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 Thread.currentThread().interrupt();
 }
}
