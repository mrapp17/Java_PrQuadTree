package Minor.P3.DS;

import Minor.P3.DS.Compare2D;
import Minor.P3.DS.Direction;

public class Point implements Compare2D<Point> {

	private long xcoord;
	private long ycoord;
	
	public Point() {
		xcoord = 0;
		ycoord = 0;
	}
	public Point(long x, long y) {
		xcoord = x;
		ycoord = y;
	}
	public long getX() {
		return xcoord;
	}
	public long getY() {
		return ycoord;
	}
	
	public Direction directionFrom(long X, long Y) {
		if(xcoord == X && ycoord == Y)
			return Direction.NOQUADRANT;
		if(xcoord > X && ycoord >= Y)
			return Direction.NE;
		else if(xcoord <= X && ycoord > Y)
			return Direction.NW;
		else if(xcoord < X && ycoord <= Y)
			return  Direction.SW;
		else if(xcoord >= X && ycoord < Y)
			return Direction.SE;
		else 
			return null;
	}
	
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {
		if(!inBox(xLo,xHi,yLo,yHi))
			return Direction.NOQUADRANT;
		
		long xMid = (long) (xHi + xLo)/2;
		long yMid = (long) (yHi + yLo)/2;
		if(xcoord == xMid && ycoord == yMid)
			return Direction.NE;
		return directionFrom(xMid,yMid);
	}
	
	public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
		if(xcoord >= xLo && xcoord <= xHi && ycoord >= yLo && ycoord <= yHi)
			return true;
		else
			return false;
	}
	
	public String toString() {
		
		return new String("(" + xcoord + ", " + ycoord + ")");
	}
	
	public boolean equals(Object o) {
	    if (o == this)
	    	return true;
	    else if (o == null)
	    	return false;
	    else if(getClass() != o.getClass())
	    	return false;
		Point otherPoint = (Point) o;
		return otherPoint.getX() == getX() && otherPoint.getY() == getY();
	}
}
