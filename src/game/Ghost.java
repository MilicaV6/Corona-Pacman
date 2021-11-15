package game;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.javadoc.Parameter;

import game.outro.ThreadPoolOutro;
import strategies.Strategy;
import view.BlockGraph;
import view.BlockGraph.NodeBlock;

public class Ghost extends Man {
	
	private Strategy strategy;
	public int speed=300;
	protected NodeBlock targetPosition;
	protected NodeBlock startPosition;
	
	@Override
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return this.icon;
	}
	 public Ghost(int x, int y,ImageIcon icon) {
			this.setRow(x);
			this.setCol(y);
			this.setIcon(icon);

			
			// TODO Auto-generated constructor stub
		}
	
	public Ghost(int row, int col, ImageIcon img, int speed) {
		super();
	setCol(col);
	setRow(row);
	setIcon(img);
		 
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public NodeBlock getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(NodeBlock startPosition) {
		this.startPosition = startPosition;
	}

	public NodeBlock getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(NodeBlock targetPosition) {
		this.targetPosition = targetPosition;
	}

	

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	
	/**
	 * Finds one of the defined direction letters by executing strategy
	 */

	
	public void findDirection() {
		
		dir=this.getStrategy().executeDir().charAt(0);
		
		  if (dir == 'r') dir = 'R'; 
		  else if (dir == 'l') dir = 'L';
		  else if (dir == 'd') dir = 'D'; 
		  else if (dir == 'u') dir = 'U';
		 
	}
	@Override
	synchronized public void run() {
		
		
		while (Game.isRunning) {
			findDirection();
			Game.isAvailable[row][col] = true;
			if(dir=='R')
				if(col+1<Game.isAvailable[0].length && Game.isAvailable[row][col + 1]) {
					move(row, col + 1);
					this.updatePosition();
					
				}
				else {
					dir = 'L';
					if (0 <= col - 1 && Game.isAvailable[row][col - 1]) {
						move(row, col - 1);
						this.updatePosition();
					
					}
						
				}
			else if (dir == 'L')
				if (0 <= col - 1 && Game.isAvailable[row][col - 1]) {
					move(row, col - 1);
					this.updatePosition();
			
				}
					
				else {
					dir = 'R';
					if (col + 1 < Game.isAvailable[0].length
							&& Game.isAvailable[row][col + 1]) {
						move(row, col + 1);
						this.updatePosition();
				
					}
				}
			else if (dir == 'U')
				if (0 <= row - 1 && Game.isAvailable[row - 1][col] 
						&& BlockGraph.nodeBlocks[row - 1][col].isRoad) {
					move(row - 1, col);
					this.updatePosition();
			
				}
					
				else {
					dir = 'D';
					if (row + 1 < Game.isAvailable[0].length
							&& Game.isAvailable[row + 1][col]
									&& BlockGraph.nodeBlocks[row + 1][col].isRoad){
						move(row + 1, col);
						this.updatePosition();
				
					}
						
				}
			else if (dir == 'D')
				if (row + 1 < Game.isAvailable[0].length
						&& Game.isAvailable[row + 1][col]){
					move(row + 1, col);
					this.updatePosition();
			
				}
					
				else {
					dir = 'U';
					if (0 <= row - 1 && Game.isAvailable[row - 1][col]){
						move(row - 1, col);
						this.updatePosition();
			
					}
						
				}
			Game.isAvailable[row][col] = false;
	
			if (this.row == targetPosition.posI && this.col == targetPosition.posJ) {		
				Game.isRunning=false;
			
				if(Game.seedCount>0) {
					JOptionPane.showMessageDialog(null,"You lost ", "Message",
							JOptionPane.PLAIN_MESSAGE,(new ImageIcon(new 
							ImageIcon("resources/dead.png").
				    		getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))));
				
					}
				
					
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
					  
					  new ThreadPoolOutro(false).start(); 
					  }
					 
				
				
								
				
			}
			try {
				sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		Thread.currentThread().interrupt();
	}
		/**
		 * Sets the target node to the value of {@link Parameter} 
		 * @param target
		 */
	
	public void updateTarget(NodeBlock target) {
	

		this.strategy.setEndBlock(target);
		this.strategy.setStart(BlockGraph.nodeBlocks[this.row][this.col]);
		this.targetPosition=target;
		this.startPosition=BlockGraph.nodeBlocks[this.row][this.col];

	
		
	}
	/**
	 * Updates start and target node of the instance
	 */
	public void updatePosition() {
		setStartPosition(BlockGraph.nodeBlocks[this.row][this.col]);
		if(Game.getInstance().playerPac!=null)
		setTargetPosition(BlockGraph.nodeBlocks
				[Game.getInstance().playerPac.row][Game.getInstance().playerPac.col]);
		strategy.setStart(this.startPosition);
		strategy.setEndBlock(this.targetPosition);

	}
	@Override
	public void changePosition() {
		// TODO Auto-generated method stub
		
	}

}
