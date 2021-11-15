package strategies;

import java.util.ArrayList;
import java.util.LinkedList;

import view.BlockGraph;
import view.BlockGraph.NodeBlock;

public class BFSStrategy extends Strategy {

	public BFSStrategy(NodeBlock startNode, NodeBlock endNode) {
		super(startNode, endNode);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public String executeDir() {
	
		
		int rowCount=BlockGraph.nodeBlocks.length;
		int colCount=BlockGraph.nodeBlocks[0].length;
		String[][] directions = new String[rowCount][colCount];
		NodePoint[][] parents = new NodePoint[rowCount][colCount];
		boolean[][] visited = new boolean[rowCount][colCount];
		boolean found = false;
		
		LinkedList<NodePoint> queue = new LinkedList<NodePoint>();
		queue.add(new NodePoint(start.posI, start.posJ));
		
		while (!found && queue.size()>0) {
			NodePoint nodePoint=queue.poll();
			if(nodePoint.row==endBlock.posI && nodePoint.col==endBlock.posJ)
				found=true;
			
			String[] dirList = { "up", "right", "down", "left" };
			int[] deltaRow = { -1, 0, 1, 0 };
			int[] deltaCol = { 0, 1, 0, -1 };
			
			for (int i = 0; i < 4; i++) {
				int cRow = nodePoint.row + deltaRow[i];
				int cCol = nodePoint.col + deltaCol[i];
				if (cRow < 0 || cRow >= rowCount || cCol < 0
						|| cCol >= colCount)
					continue;
				if (visited[cRow][cCol] || !BlockGraph.nodeBlocks[cRow][cCol].isRoad)
					continue;
				queue.add(new NodePoint(cRow, cCol));
				directions[cRow][cCol] = dirList[i];
				parents[cRow][cCol] = nodePoint;
				visited[cRow][cCol] = true;
				if (cRow == endBlock.posI && cCol == endBlock.posJ)
					found = true;
			}
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
