package strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;


import view.BlockGraph;
import view.BlockGraph.NodeBlock;

public class AStarStrategy extends Strategy {

	

	public AStarStrategy(NodeBlock startBlock, NodeBlock endBlock) {
		super(startBlock,endBlock);
		
	}
	
	@Override
	public String executeDir() {
	
		int rowCount=BlockGraph.nodeBlocks.length;
		int colCount=BlockGraph.nodeBlocks[0].length;
		String[][] directions = new String[rowCount][colCount];
		NodePoint[][] parents = new NodePoint[rowCount][colCount];
		boolean[][] visited = new boolean[rowCount][colCount];
		boolean found = false;
		
		PriorityQueue<NodePoint> openQueue = new PriorityQueue<NodePoint>();
		PriorityQueue<NodePoint> closedQueue = new PriorityQueue<NodePoint>();
		Strategy.NodePoint startPoint=new NodePoint(start.posI, start.posJ);
		startPoint.cValue=0;
		startPoint.hValue=BlockGraph.nodeBlocks[start.posI][start.posJ].retrieveHValue(endBlock);
		startPoint.hValue=startPoint.cValue+startPoint.hValue;
		openQueue.add(new NodePoint(start.posI, start.posJ));
	
		while (!openQueue.isEmpty() && !found) {
			
			NodePoint nodePoint = openQueue.peek();
			if(nodePoint.row==endBlock.posI && nodePoint.col==endBlock.posJ)
				found=true;
			
			String[] dirList = { "up", "right", "down", "left" };
			int[] deltaRow = { -1, 0, 1, 0 };
			int[] deltaCol = { 0, 1, 0, -1 };
			
			for (int i = 0; i < 4; i++) {
				int totalC=nodePoint.cValue+1;
				int cRow = nodePoint.row + deltaRow[i];
				int cCol = nodePoint.col + deltaCol[i];
				
				if (cRow < 0 || cRow >= rowCount || cCol < 0
						|| cCol >= colCount)
					continue;
				if (visited[cRow][cCol] || !BlockGraph.nodeBlocks[cRow][cCol].isRoad)
					continue;
				
				
				NodePoint pointToAdd=new NodePoint(cRow, cCol);
				if (!openQueue.contains(pointToAdd) && !closedQueue.contains(pointToAdd)) {
				pointToAdd.cValue=totalC;
				pointToAdd.hValue=BlockGraph.nodeBlocks[cRow][cCol].retrieveHValue(endBlock);
				pointToAdd.fValue=pointToAdd.cValue+pointToAdd.hValue;
			
				openQueue.add(pointToAdd);
				
				directions[cRow][cCol] = dirList[i];
				parents[cRow][cCol] = nodePoint;
				visited[cRow][cCol] = true;
				if (cRow == endBlock.posI && cCol == endBlock.posJ)
					found = true;
			}
				else {
					if (totalC<pointToAdd.cValue) {
						pointToAdd.cValue=totalC;
						pointToAdd.hValue=BlockGraph.nodeBlocks[cRow][cCol].retrieveHValue(endBlock);
						pointToAdd.fValue=pointToAdd.cValue+pointToAdd.hValue;
						parents[cRow][cCol] = nodePoint;
						directions[cRow][cCol] = dirList[i];
						
						if (openQueue.contains(pointToAdd)) {
							openQueue.remove(pointToAdd);
							openQueue.add(pointToAdd);
						}
					}
			}
				
		}
			openQueue.remove(nodePoint);
			closedQueue.add(nodePoint);
		}
		if (!found)
			return "none";

		NodePoint nodePoint = new NodePoint(endBlock.posI, endBlock.posJ);
		NodePoint parent = parents[endBlock.posI][endBlock.posJ];
		while (parent != null && !(parent.row == start.posI && parent.col == start.posJ)) {
			nodePoint = parent;
			parent = parents[nodePoint.row][nodePoint.col];
		}

		String dir = directions[nodePoint.row][nodePoint.col];

		if (dir == null)
			return "none";
		else
			return dir;
		
	
	}
	

	


}
