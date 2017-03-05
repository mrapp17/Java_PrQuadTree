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
		return Direction.NOQUADRANT;
	}
	
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {

      return Direction.NOQUADRANT;
	}
	
	public boolean   inBox(double xLo, double xHi, double yLo, double yHi) {
		
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
