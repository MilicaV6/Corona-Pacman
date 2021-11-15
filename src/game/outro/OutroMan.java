package game.outro;

import javax.swing.ImageIcon;

import game.Game;
import game.Ghost;
import strategies.Strategy;
import view.BlockGraph;
import view.BlockGraph.NodeBlock;

public class OutroMan implements Runnable {

	public boolean done=false;
	private Strategy strategy;
	public int row;
	public int col;
	public ImageIcon icon;
	private char dir;
	private int speed;
	private NodeBlock targetPosition;
	
	public int getSpeed() {
		return speed;
	}



	public void setSpeed(int speed) {
		this.speed = speed;
	}



	public NodeBlock getTargetPosition() {
		return targetPosition;
	}



	public void setTargetPosition(NodeBlock targetPosition) {
		this.targetPosition = targetPosition;
	}



	public ImageIcon getIcon() {
		return icon;
	}



	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}



	public int getRow() {
		return row;
	}



	public void setRow(int row) {
		this.row = row;
	}



	public int getCol() {
		return col;
	}



	public void setCol(int col) {
		this.col = col;
	}

	public OutroMan(int x, int y, ImageIcon icon) {
		this.setRow(x);
		this.setCol(y);
		this.setIcon(icon);
		// TODO Auto-generated constructor stub
	}

	
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
public void findDirection() {
		
		dir=this.getStrategy().executeDir().charAt(0);
	
		
		  if (dir == 'r') dir = 'R'; else if (dir == 'l') dir = 'L'; else if (dir ==
		  'd') dir = 'D'; else if (dir == 'u') dir = 'U';
		 
	}
	@Override
	public  void run() {
		while (Game.isOutro && !done) {
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
			if(this.row==targetPosition.posI && this.col==targetPosition.posJ) {
				done=true;
				BlockGraph.nodeBlocks[this.row][this.col].setIcon(null);
				Game.isAvailable[this.row][this.col]=true;
			//	System.out.println("Task named "+ this.getName()+" completed");
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		Thread.currentThread().interrupt();
	}
		private void move(int x, int y) {
			if (0 <= col && col < Game.isAvailable[0].length && 0 <= row
					&& row < Game.isAvailable.length
					&& Game.isAvailable[row][col]  ) {
				if (BlockGraph.nodeBlocks[row][col].hasSeed)
					BlockGraph.nodeBlocks[row][col].setIcon(Game.seedIcon);
				else
					BlockGraph.nodeBlocks[row][col].setIcon(null);
			
				//GraphicalView.showPower(PacMan.blocks[col][row]);
			}

			row = x;
			col = y;
			BlockGraph.nodeBlocks[row][col].setIcon(icon);
		
	}



		public void updatePosition() {
		
			strategy.setStart(BlockGraph.nodeBlocks[this.row][this.col]);
	
		}

}
