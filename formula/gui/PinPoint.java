/* $Id: PinPoint.java,v 1.9 2004/09/01 15:08:32 shadowice Exp $
 * Created on 02.07.2004
 */
package gui;

import java.awt.*;
import formula.*;

/**
 * Class to store the points where a formula can be connected.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.9 $
 */
/*
 * How to use this:
 * When a formula is added to the FormulaPanel the upper point of it's output and
 * all of the lower points of the inputs are added to a list in FormulaPanel.
 * If this point (or pin) is an input, the inputNumber is given.
 * If the pin is connected to another pin, the coordinates are saved in targetPoint.
 * mouseTargetPoint is a point below (input) or above (output) the real pinPoint.
 * It is used to specify the area where 2 formula elements are connected.
 */

public class PinPoint implements Cloneable {

// local attributes:
	private Formula formula;			// formula of this point
	private int inputNumber = -1;		// number of this input-pin (if it's an output, number is -1)
	private Point pinPoint;				// coordinates of the input/output pin
	private Point mouseTargetPoint;		// center coordinates used to connect other formulas
	private PinPoint targetPoint;		// if this pin is connected to another pin, that's the target
	private PinPoint bestCandidate;		// best possible connection that is found so far. If there's a better one it'll overwrite this
	private int bestDistance=0;			// distance to bestCandidate

// constructors:
	/**
	 * Constructor for output-pins.
	 * @param form Formula element of this pin
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public PinPoint(Formula form, int x, int y) {
		this.formula = form;
		this.pinPoint = new Point(x,y);
	}


	/**
	 * Constructor for output-pins.
	 * @param form Formula element of this pin
	 * @param p coordinates
	 */
	public PinPoint(Formula form, Point p) {
		this.formula = form;
		this.pinPoint = p;
	}


	/**
	 * Constructor for output-pins.
	 * @param form Formula element of this pin
	 */
	public PinPoint(Formula form) {
		this.formula = form;
		this.pinPoint = new Point();
	}


	/**
	 * Constructor for input-pins.
	 * @param form Formula element of this pin
	 * @param x x coordinates
	 * @param y y coordinates
	 * @param inNumber number of this input (0=1st, 1=2nd, 2=3rd,...)
	 */
	public PinPoint(Formula form, int x, int y, int inNumber) {
		this.formula = form;
		this.pinPoint = new Point(x,y);
		this.inputNumber = inNumber;
	}


	/**
	 * Constructor for input-pins.
	 * @param form Formula element of this pin
	 * @param p coordinates
	 * @param inNumber number of this input (0=1st, 1=2nd, 2=3rd,...)
	 */
	public PinPoint(Formula form, Point p, int inNumber) {
		this.formula = form;
		this.pinPoint = p;
		this.inputNumber = inNumber;
	}


	/**
	 * Constructor for input-pins.
	 * @param form Formula element of this pin
	 * @param inNumber number of this input (0=1st, 1=2nd, 2=3rd,...)
	 */
	public PinPoint(Formula form, int inNumber) {
		this.formula = form;
		this.pinPoint = new Point();
		this.inputNumber = inNumber;
	}


// methods:
	/**
	 * Sets the x and y coordinates of this PinPoint.
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public void setCoordinates(int x, int y) {
		this.pinPoint.setLocation(x,y);
	}


	/**
	 * @return Returns the x,y-coordinates of this PinPoint.
	 */
	public Point getCoordinates() {
		return this.pinPoint;
	}


	/**
	 * Sets the target to another PinPoint.
	 * @param pTarget target PinPoint
	 */
	public void setTarget(PinPoint pTarget) {
		this.targetPoint = pTarget;
	}


	/**
	 * @return returns the target PinPoint
	 */
	public PinPoint getTarget() {
		return this.targetPoint;
	}


	/**
	 * Sets the input-number of the formula for this PinPoint, only works for input-pins.
	 * @param inNumber the number of this input-PinPoint (0 = first)
	 */
	public void setInputNumber(int inNumber) {
		this.inputNumber = inNumber;
	}


	/**
	 * @return returns the number of this PinPoint
	 */
	public int getInputNumber() {
		return this.inputNumber;
	}


	/**
	 * The mouseTargetPoint is used to determine if another Formula is within reach of this PinPoint.
	 * @param mtp
	 */
	public void setMouseTargetPoint(Point mtp) {
		this.mouseTargetPoint = mtp;
	}


	/**
	 * The mouseTargetPoint is used to determine if another Formula is within reach of this PinPoint. 
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public void setMouseTargetPoint(int x, int y) {
		this.mouseTargetPoint = new Point(x,y);
	}


	/**
	 * @return
	 */
	public Point getMouseTargetPoint() {
		return this.mouseTargetPoint;
	}


	/**
	 * @param p another point
	 * @return returns the distance between this PinPoint's mouseTargetPoint and the point p.
	 */
	public int getDistance(Point p) {
		return (int)this.mouseTargetPoint.distance(p);
	}


	/**
	 * @param x x coordinates of another point
	 * @param y y coordinates of another point
	 * @return Returns the distance between this PinPoint and the point (x,y).
	 */
	public int getDistance(int x, int y) {
		return (int)this.mouseTargetPoint.distance(x,y);
	}


	/**
	 * @return returns the Formula this pin belongs to
	 */
	public Formula getFormula() {
		return this.formula;
	}


	/**
	 * Sets the formula this PinPoint belongs to.
	 * @param form Formula object
	 */
	public void setFormula(Formula form) {
		this.formula = form;
	}


	/**
	 * Moves the mouseTargetPoint and pinPoint to (x+x_off,y+y_off). 
	 * @param x_off x offset
	 * @param y_off y offset
	 */
	public void translate(int x_off, int y_off) {
		this.mouseTargetPoint.translate(x_off,y_off);
		this.pinPoint.translate(x_off,y_off);
	}


	/**
	 * @return nearest PinPoint
	 */
	public PinPoint getBestCandidate() {
		return bestCandidate;
	}


	/**
	 * @return best distance
	 */
	public int getBestDistance() {
		return bestDistance;
	}


	/**
	 * Sets the best (nearest) PinPoint found so far. Only temporary, used by Selection.refreshPinPointList().
	 * @param point
	 */
	public void setBestCandidate(PinPoint point) {
		bestCandidate = point;
	}


	/**
	 * Sets the best found distance. Only temporary, used by Selection.refreshPinPointList().
	 * @param dist distance
	 */
	public void setBestDistance(int dist) {
		bestDistance = dist;
	}


	/**
	 * Clones a PinPoint object. This will only clone it's point-datas.
	 *
	 * <b>IMPORTANT: Clone doesn't clone all references in this formula!
	 * Don't use this clone method without setting the formula and targets afterwards!</b> 
	 */
	public Object clone() {
		try {
			PinPoint cpp = (PinPoint)super.clone();
			cpp.bestCandidate = null;
			//cpp.bestDistance = 0;
			cpp.formula = null;
			cpp.mouseTargetPoint = (Point)mouseTargetPoint.clone();
			cpp.pinPoint = (Point)pinPoint.clone();
			cpp.targetPoint = null;
			//cpp.inputNumber = inputNumber;
			return cpp;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
	
	
	public void debug() {
		System.out.println("------------------------------");
		System.out.println("PinPoint Debug Output:");
		System.out.println("targetPoint: "+targetPoint);
		System.out.println("inputNumber: "+inputNumber);
		System.out.println("formula: "+formula);
		System.out.println("pinPoint: "+pinPoint);
		System.out.println("mouseTargetPoint: "+mouseTargetPoint);
		System.out.println("------------------------------");
	}

}
