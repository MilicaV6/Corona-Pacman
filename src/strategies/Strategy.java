package strategies;



import java.util.ArrayList;

import view.BlockGraph;
import view.BlockGraph.NodeBlock;

/**
 * 
 * @author Milica
 * Abstracts strategies for search algorithms
 */

public abstract class Strategy {
	
	NodeBlock start;
	NodeBlock endBlock;
	
	public Strategy(NodeBlock startNode, NodeBlock endNode) {
		this.start=startNode;
		this.endBlock=endNode;
		
	}


	public abstract String executeDir();


	public NodeBlock getStart() {
		return start;
	}

	public void setStart(NodeBlock start) {
		this.start = start;
	}

	public NodeBlock getEndBlock() {
		return endBlock;
	}

	public void setEndBlock(NodeBlock endBlock) {
		this.endBlock = endBlock;
	}
	/**
	 * 
	 * @author Milica
	 * Class for defining nodes of the graph in search algorithms
	 */
	class NodePoint implements Comparable<NodePoint> {
	 public int row;
	 public int col;
	public int fValue;
	public int cValue;
	public int hValue;
	 
	 public NodePoint(int row,int col) {
		this.row=row;
		this.col=col;
	}

	@Override
	public int compareTo(NodePoint o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.fValue, o.fValue);
	}
	 
	
	 
	}
}

