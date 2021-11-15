/***********************************************************************
 * Module:  Man.java
 * Author:  User
 * Purpose: Defines the Class Man
 ***********************************************************************/

package game;

import java.util.*;

import javax.swing.ImageIcon;


import view.BlockGraph;

public abstract class Man extends Thread {
   protected ImageIcon icon;

   public int speed=300;
   public int row=-1;
   public int col=0;
   public char dir='R';
   
   protected Game observerGame;
   
   public Game getObserverGame() {
	return observerGame;
}
public void setObserverGame(Game observerGame) {
	this.observerGame = observerGame;
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
/** @param posX 
    * @param posY */
   public abstract void changePosition();
   /** @param x 
    * @param y */
   public Man(int x, int y, ImageIcon icon) {
	 this.setRow(x);
	 this.setCol(y);
     this.setIcon(icon);
   }
public Man() {
	// TODO Auto-generated constructor stub
}
public Man(int row, int col, ImageIcon img, int speed) {
	
	this.setCol(col);
	this.setRow(row);
	this.setIcon(img);
	this.speed=speed;
}


public void notifyObserver(int newY, int newX, int oldY, int oldX) {
	observerGame.updateMap(newY,newX,oldY,oldX);
}
/**
 * Changing the position of the instance
 * @param x - specifies the new row to move on
 * @param y - specifies the new column to move on
 */
 protected void move(int x, int y) {
	 if (0 <= col && col < Game.isAvailable[0].length && 0 <= row
				&& row < Game.isAvailable.length
				&& Game.isAvailable[row][col]  ) {
			if (BlockGraph.nodeBlocks[row][col].hasSeed)
				BlockGraph.nodeBlocks[row][col].setIcon(Game.seedIcon);
			else
				BlockGraph.nodeBlocks[row][col].setIcon(null);
		
		}

		row = x;
		col = y;
		BlockGraph.nodeBlocks[row][col].setIcon(icon);
	
}

}