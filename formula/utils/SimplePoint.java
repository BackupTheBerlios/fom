/* $Id: SimplePoint.java,v 1.1 2004/10/20 15:18:24 shadowice Exp $
 * Created on 20.10.2004
 *
 */
package utils;

/**
 * Simple point class, because java.awt.Point needs Point2D for some obscure reasons.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.1 $
 */
public class SimplePoint implements Cloneable {

	public int x = 0;
	public int y = 0;


	/**
	 * Creates a new SimplePoint object with coordinates (0,0).
	 * 
	 */
	public SimplePoint() {
	}


	/**
	 * Creates a new SimplePoint object with coordinates (x,y).
	 * 
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public SimplePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * Creates a new SimplePoint object with coordinates (point.x,point.y).
	 * 
	 * @param point position
	 */
	public SimplePoint(SimplePoint point) {
		this.x = point.x;
		this.y = point.y;
	}


	/**
	 * Sets a new location for this point.
	 * 
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public void setLocation(int x,int y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * Moves the point to (x+x_off,y+y_off).
	 * 
	 * @param x_off 
	 * @param y_off
	 */
	public void translate(int x_off,int y_off) {
		this.x += x_off;
		this.y += y_off;
	}


	/**
	 * Creates a copy of an object.
	 */
	public Object clone() {
		try {
			SimplePoint sp = (SimplePoint)super.clone();
			return sp;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
}
