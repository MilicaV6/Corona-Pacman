package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import helper.ResourceLoader;
import listeners.PacmanKeyListener;
import strategies.AStarStrategy;
import strategies.BFSStrategy;
import strategies.RandomStrategy;
import view.BlockGraph;
import view.BlockGraph.NodeBlock;
import view.FaceAnimation;
import view.MainFrame;

public class Game extends javax.swing.JPanel{
   private static final long serialVersionUID = 1L;
   public static boolean isRunning = false;
   public static boolean isOutro = false;
   
   public static ImageIcon seedIcon;
   public  Player playerPac;
   public  Ghost purpleGhost;
   public  Ghost blackGhost;
   public  Ghost greenGhost;
   private static Game instance=null;
   public static int delta=1;
   private PacmanKeyListener keyListener=new PacmanKeyListener();
   private MainFrame frame;
   FaceAnimation faceAnimation;
   
   
   /** @param g */
   @Override
   protected void paintComponent(Graphics g) {
   	// TODO Auto-generated method stub
   	super.paintComponent(g);
   }
   
   public static final int ROW = 15;
   public static final int COL = 21;
   public static final int BLOCKDIM = 32;

   public static ArrayList<BlockGraph.NodeBlock> walls =
		   new ArrayList<BlockGraph.NodeBlock>();
   public static boolean[][] isAvailable;

 
   public static int seedCount=0;
   
   public static Game getInstance() {
	   
	   if(instance==null)
		   instance=new Game();
	   return instance;
   }
   /**
    * Clears the icons of seeds and mans from the map
    */
   public void clearMap() {
	   
	   playerPac=null;
	   blackGhost=null;
	   greenGhost=null;
	   purpleGhost=null;
	  
	   
	   
			   
	   for(int i=0; i<BlockGraph.nodeBlocks.length;i++)
		   for(int j=0;j<BlockGraph.nodeBlocks[0].length;j++) {
			   BlockGraph.nodeBlocks[i][j].hasSeed=false;
			   BlockGraph.nodeBlocks[i][j].setIcon(null);
		   }
	   
	    this.repaint();
}
   
   private Game() {
   	
   	Dimension dimension=new Dimension(COL*BLOCKDIM,ROW*BLOCKDIM);
   	this.setPreferredSize(dimension);
   	this.setLayout(new GridLayout(ROW,COL));

   	seedIcon=new ImageIcon(new ImageIcon("resources/vaccine.png")
				.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH));
   	
	this.setFocusable(true);
   	this.drawMap();
   	frame=(MainFrame) SwingUtilities.getWindowAncestor(this);
     
  //  startGame();
	
   
   }
   
	
	  public void restart() {
	  
	  for(int i=0; i<BlockGraph.nodeBlocks.length;i++) 
		  for(int j=0;j<BlockGraph.nodeBlocks[0].length;j++) { 

	  BlockGraph.nodeBlocks[i][j].hasSeed=false;
	  BlockGraph.nodeBlocks[i][j].setIcon(null);
	  isAvailable[i][j]=false; 
	  } 
	   frame= (MainFrame) SwingUtilities.getWindowAncestor(instance);
	  frame.remove(instance);
	  instance.playerPac=null;
	  seedCount=0;
	  isOutro = false; 
	  instance.removeKeyListener(keyListener);
	  instance=null;
	  instance=getInstance();
	  do {
		  delta=new Random().nextInt(15);
	  }
	  while(delta<=5);
	  Game.isRunning=true;
				 
	  instance.blackGhost=null;
	  instance.greenGhost=null;
	  instance.purpleGhost=null;
	  instance.faceAnimation=null;
	
			  frame.add(instance);
			  instance.setFocusable(true);
			  instance.requestFocus();
			 
	//initiate player		
	
	 keyListener=new PacmanKeyListener();
   	
	 instance.playerPac=new Player(7, 10, new ImageIcon(new 
			ImageIcon("resources/icons8-face-with-medical-mask-48.png").
  		getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
	 instance.playerPac.speed=300;
   	instance.addKeyListener(keyListener);
	BlockGraph.nodeBlocks[instance.playerPac.getRow()][instance.playerPac.getCol()].
 	setIcon(instance.playerPac.getIcon());
 	BlockGraph.nodeBlocks[instance.playerPac.getRow()][instance.playerPac.getCol()].repaint();
 	
 	instance.playerPac.start();
 	
 	//initiate red ghost			  
 	instance.purpleGhost=new Ghost(13, 1, (new ImageIcon(new 
			ImageIcon("resources/coronavirusRed.png")
 			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH))));
 	instance.purpleGhost.setSpeed(200);
	RandomStrategy random=new RandomStrategy(
			(BlockGraph.nodeBlocks[instance.purpleGhost.getRow()]
					[instance.purpleGhost.getCol()]),
			(BlockGraph.nodeBlocks[instance.playerPac.getRow()]
					[instance.playerPac.getCol()]));
	instance.purpleGhost.setStrategy(random);
	BlockGraph.nodeBlocks[instance.purpleGhost.getRow()][instance.purpleGhost.getCol()].
 	setIcon(instance.purpleGhost.getIcon());
// 	BlockGraph.nodeBlocks[purpleGhost.getRow()][purpleGhost.getCol()].repaint();
	instance.purpleGhost.updateTarget(BlockGraph.nodeBlocks[instance.playerPac.getRow()]
			[instance.playerPac.getCol()]);
	instance.purpleGhost.start();
	
	//initiate black ghost
	instance.blackGhost=new Ghost(13, 19, new ImageIcon
 			(new ImageIcon("resources/blackCorona.png")
 			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
 	AStarStrategy aStrategy=new AStarStrategy(
 			(BlockGraph.nodeBlocks[instance.blackGhost.getRow()]
 					[instance.blackGhost.getCol()]),
 			(BlockGraph.nodeBlocks[instance.playerPac.getRow()]
 					[instance.playerPac.getCol()]));
 	instance.blackGhost.setStrategy(aStrategy);
 	BlockGraph.nodeBlocks[instance.blackGhost.getRow()]
 			[instance.blackGhost.getCol()].
 	setIcon(instance.blackGhost.getIcon());
 	instance.blackGhost.updateTarget(
 			BlockGraph.nodeBlocks[instance.playerPac.getRow()]
 					[instance.playerPac.getCol()]);
	BlockGraph.nodeBlocks[instance.blackGhost.getRow()]
			[instance.blackGhost.getCol()].repaint();
	instance.blackGhost.setSpeed(300);
	instance.blackGhost.start();
	
	//initiate green ghost
	instance.greenGhost=new Ghost(1, 8,new ImageIcon(
			new ImageIcon("resources/greenCorona.png")
 			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
 	BFSStrategy dfsStrategy=new BFSStrategy(
 			(BlockGraph.nodeBlocks[instance.greenGhost.getRow()]
 					[instance.greenGhost.getCol()]),
 			(BlockGraph.nodeBlocks[instance.playerPac.getRow()]
 					[instance.playerPac.getCol()]));
 	instance.greenGhost.setStrategy(dfsStrategy);
 	BlockGraph.nodeBlocks[instance.greenGhost.getRow()]
 			[instance.greenGhost.getCol()].
 	setIcon(instance.greenGhost.getIcon());
 	instance.greenGhost.updateTarget(
 			BlockGraph.nodeBlocks[instance.playerPac.getRow()]
 			[instance.playerPac.getCol()]);
	BlockGraph.nodeBlocks[instance.greenGhost.getRow()]
			[instance.greenGhost.getCol()].repaint();
	instance.greenGhost.setSpeed(300);
	instance.greenGhost.start();	
	
	instance.faceAnimation=new FaceAnimation();
	instance.faceAnimation.start();
	  }
	 
   public MainFrame getFrame() {
		return frame;
	}
/**
    * sets the necessary elements for executing the game..
    * creates player and ghosts
    */
   public void startGame() {
	   do {
			  delta=new Random().nextInt(15);
		  }
		  while(delta<=5);
		isRunning=true;
		faceAnimation=new FaceAnimation();
		faceAnimation.start();
		
	//initiate player
	playerPac=new Player(7, 10, new ImageIcon(new 
			ImageIcon("resources/icons8-face-with-medical-mask-48.png").
    		getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
	keyListener=new PacmanKeyListener();
	addKeyListener(keyListener);
	BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()].
   	setIcon(playerPac.getIcon());
   	BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()].repaint();
   	playerPac.start();
	
  //initiate red ghost
	purpleGhost=new Ghost(13, 1, (new ImageIcon(new 
			ImageIcon("resources/coronavirusRed.png")
   			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH))));
	purpleGhost.setSpeed(200);
	RandomStrategy random=new RandomStrategy(
			(BlockGraph.nodeBlocks[purpleGhost.getRow()][purpleGhost.getCol()]),
			(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]));
	purpleGhost.setStrategy(random);
	BlockGraph.nodeBlocks[purpleGhost.getRow()][purpleGhost.getCol()].
   	setIcon(purpleGhost.getIcon());
  // 	BlockGraph.nodeBlocks[purpleGhost.getRow()][purpleGhost.getCol()].repaint();
   	purpleGhost.updateTarget(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]);
   	purpleGhost.start();
   	
  //initiate bblack ghost
   	 blackGhost=new Ghost(13, 19, new ImageIcon
   			(new ImageIcon("resources/blackCorona.png")
   			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
   	AStarStrategy aStrategy=new AStarStrategy(
   			(BlockGraph.nodeBlocks[blackGhost.getRow()][blackGhost.getCol()]),
   			(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]));
   	blackGhost.setStrategy(aStrategy);
   	BlockGraph.nodeBlocks[blackGhost.getRow()][blackGhost.getCol()].
   	setIcon(blackGhost.getIcon());
   	blackGhost.updateTarget(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]);
	BlockGraph.nodeBlocks[blackGhost.getRow()][blackGhost.getCol()].repaint();
	blackGhost.setSpeed(300);
	blackGhost.start();
	
	
	//initiate green ghost
	greenGhost=new Ghost(1, 8,new ImageIcon(new ImageIcon("resources/greenCorona.png")
   			.getImage().getScaledInstance(BLOCKDIM, BLOCKDIM, Image.SCALE_SMOOTH)));
   	BFSStrategy dfsStrategy=new BFSStrategy(
   			(BlockGraph.nodeBlocks[greenGhost.getRow()][greenGhost.getCol()]),
   			(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]));
   	greenGhost.setStrategy(dfsStrategy);
   	BlockGraph.nodeBlocks[greenGhost.getRow()][greenGhost.getCol()].
   	setIcon(greenGhost.getIcon());
   	greenGhost.updateTarget(BlockGraph.nodeBlocks[playerPac.getRow()][playerPac.getCol()]);
	BlockGraph.nodeBlocks[greenGhost.getRow()][greenGhost.getCol()].repaint();
	greenGhost.setSpeed(300);
	greenGhost.start();	
		
		
}
   /**
    * draws the map and sets the icons for the blocks
    */
   public  void drawMap() {
   	
   	isAvailable=new boolean[ROW][COL];
   	
   	BlockGraph.nodeBlocks=new BlockGraph.NodeBlock[ROW][COL];
   	
   	for (int i = 0; i < ROW; i++) {
   		for (int j = 0; j < COL; j++) {
   			NodeBlock block=new BlockGraph.NodeBlock();
   			block.setVisible(true);
   			block.setBounds(j*BLOCKDIM,i*BLOCKDIM,BLOCKDIM,BLOCKDIM);
   			block.setPreferredSize(new Dimension(BLOCKDIM,BLOCKDIM));
   			block.setBorder(null);
   			block.posI=i;
   			block.posJ=j;
   			
   			if (block.isRoad) {
   				block.setBackground(Color.cyan);
   			}
   			else {
   				block.setBackground(Color.magenta);
   			}
   			
   			BlockGraph.nodeBlocks[i][j]=block;
   			add(block);
   			block.setFocusable(false);
   			isAvailable[i][j]=true;
   			
   		}
   		
   	}
   	
   	
   	//defining walls
   	for (int i = 0; i < BlockGraph.nodeBlocks.length; i++) {
   		walls.add(BlockGraph.nodeBlocks[i][0]);
   		walls.add(BlockGraph.nodeBlocks[i][BlockGraph.nodeBlocks[i].length-1]);
   		
   		BlockGraph.nodeBlocks[i][0].isRoad=false;
   		BlockGraph.nodeBlocks[i][0].setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[i][0].setFocusable(false);
   		isAvailable[i][0]=false;
   		
   		BlockGraph.nodeBlocks[i][BlockGraph.nodeBlocks[i].length-1].
   		setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[i][BlockGraph.nodeBlocks[i].length-1].isRoad=false;
   		BlockGraph.nodeBlocks[i][BlockGraph.nodeBlocks[i].length-1].setFocusable(false);
   		isAvailable[i][BlockGraph.nodeBlocks[i].length-1]=false;
   	}
   	for (int i = 0; i < BlockGraph.nodeBlocks[0].length; i++) {
   		walls.add(BlockGraph.nodeBlocks[0][i]);
   		walls.add(BlockGraph.nodeBlocks[BlockGraph.nodeBlocks.length-1][i]);
   		
   		BlockGraph.nodeBlocks[0][i].isRoad=false;
   		BlockGraph.nodeBlocks[0][i].setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[0][i].setFocusable(false);
   		isAvailable[0][i]=false;
   		
   		BlockGraph.nodeBlocks[BlockGraph.nodeBlocks.length-1][i].
   		setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[BlockGraph.nodeBlocks.length-1][i].isRoad=false;
   		BlockGraph.nodeBlocks[BlockGraph.nodeBlocks.length-1][i].setFocusable(false);
   		isAvailable[BlockGraph.nodeBlocks.length-1][i]=false;
   		
   	}
   	for (int i = 2; i < BlockGraph.nodeBlocks[3].length-2; i++) {
   		walls.add(BlockGraph.nodeBlocks[2][i]);
   		walls.add(BlockGraph.nodeBlocks[12][i]);
   		
   		BlockGraph.nodeBlocks[2][i].setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[2][i].isRoad=false;
   		BlockGraph.nodeBlocks[12][i].setBackground(Color.darkGray);
   		BlockGraph.nodeBlocks[12][i].isRoad=false;
   		isAvailable[2][i]=false;
   		isAvailable[12][i]=false;
   	}
   	
   	for (int i = 4; i < 7; i++) {
   		for (int j = 2; j < 7; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	
   	for (int i = 4; i < 7; i++) {
   		for (int j = 8; j < 13; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	for (int i = 4; i < 7; i++) {
   		for (int j = 14; j < 19; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	for (int i = 8; i < 11; i++) {
   		for (int j = 2; j < 7; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	
   	for (int i = 8; i < 11; i++) {
   		for (int j = 8; j < 13; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	for (int i = 8; i < 11; i++) {
   		for (int j = 14; j < 19; j++) {
   			walls.add(BlockGraph.nodeBlocks[i][j]);
   			BlockGraph.nodeBlocks[i][j].setBackground(Color.darkGray);
   			BlockGraph.nodeBlocks[i][j].isRoad=false;
   			isAvailable[i][j]=false;
   		}
   		
   	}
   	//defining road with seeds
   	
   	for (int i = 0; i < BlockGraph.nodeBlocks.length; i++) {
   		for (int j = 0; j < BlockGraph.nodeBlocks[i].length; j++) {
   			if (BlockGraph.nodeBlocks[i][j].isRoad) {
   				BlockGraph.nodeBlocks[i][j].setIcon(seedIcon);
   				BlockGraph.nodeBlocks[i][j].hasSeed=true;
   				isAvailable[i][j]=true;
   				seedCount++;
   			}
   		}
   		
   	}

   	
   }
 
   public void updateMap(int newY, int newX, int oldY, int oldX) {
	   BlockGraph.nodeBlocks[newY][newX].setIcon(playerPac.getIcon());
	   
	   BlockGraph.nodeBlocks[oldY][oldX].setIcon(null);
	   BlockGraph.nodeBlocks[oldY][oldX].hasSeed=false;
}


}