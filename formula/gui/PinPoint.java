/*
 * Created on 02.07.2004
 *
 */
package gui;

import java.awt.*;
import formula.*;

/**
 * Class to store the points where a formula can be connected.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
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

public class PinPoint {

	private Formula formula;			// formula of this point
	private int inputNumber = -1;		// number of this input-pin (if it's an output, number is -1)
	private Point pinPoint;				// coordinates of the input/output pin
	private Point mouseTargetPoint;		// center coordinates used to connect other formulas
	private Point targetPoint;			// if this pin is connected to another pin, that's the target

	/**
	 * @param form Formula element of this pin
	 * @param x x coordinates
	 * @param y y coordinates
	 */
	public PinPoint(Formula form, int x, int y) {
		this.formula = form;
		this.pinPoint = new Point(x,y);
	}

	/**
	 * @param form Formula element of this pin
	 * @param p coordinates
	 */
	public PinPoint(Formula form, Point p) {
		this.formula = form;
		this.pinPoint = p;
	}
	
	public int getDistance(Point p) {
		return (int)pinPoint.distance(p);
	}

	public int getDistance(int x, int y) {
		return (int)pinPoint.distance(x,y);
	}

}
