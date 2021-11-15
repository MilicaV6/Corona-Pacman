package game;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import game.outro.ThreadPoolOutro;

import view.BlockGraph;

public class Player extends Man {
	
	private ArrayList<Ghost>ghostsObservers=new ArrayList<Ghost>();
	
   public Player(int x, int y,ImageIcon icon) {
	   super(x, y, icon);
		this.setRow(x);
		this.setCol(y);
		this.setIcon(icon);

		
		// TODO Auto-generated constructor stub
	}

 

public ArrayList<Ghost> getGhostsObservers() {
	return ghostsObservers;
}


public void setGhostsObservers(ArrayList<Ghost> ghostsObservers) {
	this.ghostsObservers = ghostsObservers;
}


@Override
synchronized public void run() {
		dir='N';
		// TODO Auto-generated method stub
		while (Game.isRunning ) {


			if(this.col!=-1)
				Game.isAvailable[this.row][this.col]=true;
			if(this.dir=='R') {
				if (this.col+1<Game.isAvailable[0].length && 
						Game.isAvailable[this.row][this.col+1]) {
					if(BlockGraph.nodeBlocks[this.row][this.col+1].hasSeed) {
						BlockGraph.nodeBlocks[row][col+1].hasSeed=false;
					Game.seedCount--;
					}
					
					this.move(this.row, this.col+1);
					
				}
			}
			else if (this.dir=='L') {
				if (this.col-1>-1 && Game.isAvailable[this.row][this.col-1]) {
					if(BlockGraph.nodeBlocks[this.row][this.col-1].hasSeed) {
						BlockGraph.nodeBlocks[row][col-1].hasSeed=false;
						Game.seedCount--;
						}
					
					this.move(row, col-1);
				
				}
			}
			else if (this.dir=='U') {
				if (this.row-1>-1 && Game.isAvailable[this.row-1][this.col]) {
					if(BlockGraph.nodeBlocks[this.row-1][this.col].hasSeed) {
						BlockGraph.nodeBlocks[row-1][col].hasSeed=false;
						Game.seedCount--;
						}
					
					this.move(this.row-1, this.col);
				}
			}
			else if(this.dir=='D') {
				
			
				if (this.row+1<Game.isAvailable.length &&
						Game.isAvailable[this.row+1][this.col]) {
					if(BlockGraph.nodeBlocks[this.row+1][this.col].hasSeed) {
						BlockGraph.nodeBlocks[row+1][col].hasSeed=false;
						Game.seedCount--;
						}
					
					this.move(row+1, col);
					
				}
			}
			
			
			 
			if(Game.seedCount==0)
			{
		
				Game.isRunning=false;
				
			
				JOptionPane.showMessageDialog(null,"You are safe", 
						"Message",JOptionPane.PLAIN_MESSAGE,(new ImageIcon(new 
						ImageIcon("resources/smiley.png").
			    		getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))));
				
				  int result=JOptionPane.showConfirmDialog 
						  (null, "New Game?", "question",
				  JOptionPane.YES_NO_CANCEL_OPTION);
				  if(result==JOptionPane.YES_OPTION) {
					  this.row=0;
					  this.col=0;
				Game.isOutro=false;
				Game.isRunning=false;
				  Game.getInstance().restart(); 
				  }
				  else if(result==JOptionPane.NO_OPTION)
				  System.exit(0); 
				  else {
					  Game.isOutro=true; 
					  Game.getInstance().clearMap();
				  Game.getInstance().repaint();
				  
				  new ThreadPoolOutro(true).start(); 
				  }
				
			}
			  try { 
				  sleep(speed);
				  } 
			  catch (InterruptedException e) { 
				  e.printStackTrace();
				  }
		}
		
	Thread.currentThread().interrupt();
	
	}



@Override
public void changePosition() {
	// TODO Auto-generated method stub
	
}
 

}