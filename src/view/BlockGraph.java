package view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;

public class BlockGraph {
	public static NodeBlock[][] nodeBlocks;
	public BlockGraph() {
		
	}
	
	
	

	public static class NodeBlock extends JButton {
		   	   
				   
		   public int posI;
		   public int posJ;
		   
		   public boolean hasSeed = false;
		   public boolean isRoad = true;
		   
		   public int hValue;
		   public int cValue=1;
		   public int fValue;
		   
		   /** @param g */
		   @Override
		   protected void paintComponent(Graphics g) {
		   	// TODO Auto-generated method stub
		   	super.paintComponent(g);
		   }
		   
		 
		   
		 public NodeBlock() {
			// TODO Auto-generated constructor stub
		 	}	  
		 
		   
		   public int retrieveHValue(NodeBlock endBlock) {
			   hValue=(Math.abs(endBlock.posI-this.posI)+Math.abs(endBlock.posJ-this.posJ));
			return hValue;
			
		}    
		 
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "(["+posI+"]"+"["+posJ+"])";
		}
		   
	}
}
