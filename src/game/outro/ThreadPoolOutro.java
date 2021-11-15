package game.outro;

import java.awt.Image;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.ImageIcon;

import game.Game;
import strategies.BFSStrategy;
import strategies.RandomStrategy;
import view.BlockGraph;
/**
 * 
 * @author Milica
 * Moves the objects on the cleared map and writes to console number of threads using
 */
public class ThreadPoolOutro extends Thread {
	Random random=new Random();

	int x=1;
	int y=1;
	int x2=1;
	int y2=1;
	ExecutorService executor;
	ThreadPoolExecutor pool;
	int speed;
	OutroMan outroMan;
	ImageIcon outroIcon;
	
	/**
	 * 
	 * @param won - if the player has won, sets another icons to moving objects
	 */
	
	public ThreadPoolOutro(boolean won) {
		// TODO Auto-generated constructor stub
	
	if(won) {
		executor=Executors.newCachedThreadPool();
		pool=(ThreadPoolExecutor) executor;
		outroIcon=new ImageIcon(new 
				ImageIcon("resources/smiley.png").
	    		getImage().getScaledInstance(Game.BLOCKDIM, Game.BLOCKDIM, 
	    				Image.SCALE_SMOOTH));
	}	
	else {
		executor=Executors.newFixedThreadPool(5);
		pool=(ThreadPoolExecutor) executor;
		outroIcon=new ImageIcon(new 
				ImageIcon("resources/purpleCorona.png").
	    		getImage().getScaledInstance(Game.BLOCKDIM, Game.BLOCKDIM, 
	    				Image.SCALE_SMOOTH));
	}
	
	for (int i = 1; i <= Game.delta; ++i) {
		do {
		x=random.nextInt(Game.ROW);
		 y=random.nextInt(Game.COL);
		 x2=random.nextInt(Game.ROW);
		 y2=random.nextInt(Game.COL);
	
		}
		while(!Game.isAvailable[x][y] || !Game.isAvailable[x2][y2]);		
			
			outroMan=new OutroMan(x, y, outroIcon);
		
		do {
			speed=random.nextInt(4);
		}while(speed==0);
		outroMan.setSpeed(speed * 100);
		outroMan.setTargetPosition(BlockGraph.nodeBlocks[x2][y2]);
		RandomStrategy randomStrategy=new RandomStrategy(
				(BlockGraph.nodeBlocks[x][y]),
				(BlockGraph.nodeBlocks[x2][y2]));
	
		outroMan.setStrategy(randomStrategy);
		BlockGraph.nodeBlocks[x][y].setIcon(outroMan.getIcon());
		
        executor.submit(outroMan);       
     
        
    }	 
	
	 executor.shutdown();
	
	}
	   @Override
   	public void run() {
				 while (!executor.isTerminated()) {
					 System.out.println("Core threads: " + pool.getCorePoolSize());
				     System.out.println("Largest executions: "
				        + pool.getLargestPoolSize());
				     System.out.println("Maximum allowed threads: "
				        + pool.getMaximumPoolSize());
				     System.out.println("Current threads in pool: "
				        + pool.getPoolSize());
				     System.out.println("Currently executing threads: "
				        + pool.getActiveCount());
				     System.out.println("Total number of threads(ever scheduled): "
				        + pool.getTaskCount());
		        		     try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				}
				Thread.currentThread().interrupt();
			}
		
	 
		// TODO Auto-generated method stub
		
	
}

