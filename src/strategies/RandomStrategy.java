package strategies;

import java.util.Random;

import view.BlockGraph;
import view.BlockGraph.NodeBlock;

public class RandomStrategy extends Strategy {

	public RandomStrategy(NodeBlock startNode, NodeBlock endNode) {
		super(startNode, endNode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String executeDir() {
		
		int rowCount=BlockGraph.nodeBlocks.length;
		int colCount=BlockGraph.nodeBlocks[0].length;
		
	
		NodePoint startpoint=new NodePoint(start.posI, start.posJ);

			NodePoint nodePoint=startpoint;
			
			
			String[] dirList = { "up", "right", "down", "left" };
			int[] deltaRow = { -1, 0, 1, 0 };
			int[] deltaCol = { 0, 1, 0, -1 };
			Random random = new Random();
			int dirNum=random.nextInt(4);
			int cRow = nodePoint.row + deltaRow[dirNum];
			int cCol = nodePoint.col + deltaCol[dirNum];
			
			  while(cRow < 0 || cRow >= rowCount || cCol < 0 || cCol >= colCount ||
			  !BlockGraph.nodeBlocks[cRow][cCol].isRoad) { 
				  dirNum=random.nextInt(4);		  
			 
				  cRow = nodePoint.row + deltaRow[dirNum];
				 cCol = nodePoint.col + deltaCol[dirNum];
			  }			
				
		
		String dir =dirList[dirNum];
	
		if (dir == null)
			return "none";
		else
			return dir;
	}




}
