/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.*;
import java.util.*;
import formula.*;

/**
 * The FormulaPanel displays the formula-trees, created by the user.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class FormulaPanel extends Panel {


	private static int maxPinDistance = 40;	// distance to a pin (mouseTargetPoint) to be of any interest

	// These lists store all input/output pins.
	private LinkedList inputPinList = new LinkedList();
	private LinkedList outputPinList = new LinkedList();



	/**
	 * Creates a new FormulaPanel.
	 */
	public FormulaPanel() {
		setLayout(new TreeLayout());
		setBackground(SystemColor.text);
	}
	
	
	/**
	 * @param pPIn
	 */
	public void addInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			inputPinList.add(pPIn[i]);
		}
	}

	/**
	 * @param pPOut
	 */
	public void addOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			outputPinList.add(pPOut[i]);
		}
	}
	
	/**
	 * @param pPIn
	 */
	public void removeInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			inputPinList.remove(pPIn[i]);
		}
	}
	
	/**
	 * @param pPOut
	 */
	public void removeOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			outputPinList.remove(pPOut[i]);
		}
	}

	/**
	 * @return
	 */
	public PinPoint[] getInputPins() {
		return (PinPoint[])inputPinList.toArray();
	}

	/**
	 * @return
	 */
	public PinPoint[] getOutputPins() {
		return (PinPoint[])outputPinList.toArray();
	}


	/**
	 * Finds out if an input-pin is near the outPin and if they are compatible. 
	 * @param outPin source output-pin
	 * @return returns the nearest input-pin or null
	 */
	public PinPoint getNearestInputPin(PinPoint outPin) {
		int size = inputPinList.size();
		int x = outPin.getCoordinates().x;
		int y = outPin.getCoordinates().y;
		PinPoint nearestPP = null;
		PinPoint tmpPP = null;
		Formula tmpForm = null;
		int minDistance = maxPinDistance;		// pins farther away are not interesting
		int distance = 0;
		try {
			Class[] inTypes;
			Class[] outTypes = outPin.getFormula().getOutputTypes();
			for (int i=0;i<size;i++) {
				tmpPP = (PinPoint)inputPinList.get(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {
				 	inTypes = tmpPP.getFormula().getInputTypes(tmpPP.getInputNumber());
					int a=0,b=0;
					while (((a<inTypes.length) || (b<outTypes.length)) && (inTypes[a] != outTypes[b])) {
						b++;
						if (a >= inTypes.length) {
							a++;
							b = 0;
						}
			 		}
			 		if (inTypes[a] == outTypes[b]) {
				 		nearestPP = tmpPP;
				 	}
				}
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			return nearestPP;			
		}		
	}

	/**
	 * Finds out if an output-pin is near the inPin and if they are compatible.
	 * @param inPin input-pin to test
	 * @return returns the nearest output-pin or null
	 */
	public PinPoint getNearestOutputPin(PinPoint inPin) {
		int size = outputPinList.size();
		int x = inPin.getCoordinates().x;
		int y = inPin.getCoordinates().y;
		PinPoint nearestPP = null;
		PinPoint tmpPP = null;
		Formula tmpForm = null;
		int minDistance = maxPinDistance;		// pins farther away are not interesting
		int distance = 0;
		try {
			Class[] inTypes = inPin.getFormula().getInputTypes(inPin.getInputNumber());
			Class[] outTypes;
			for (int i=0;i<size;i++) {
				tmpPP = (PinPoint)outputPinList.get(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {
					outTypes = tmpPP.getFormula().getOutputTypes();
					int a=0,b=0;
					while (((a<inTypes.length) || (b<outTypes.length)) && (inTypes[a] != outTypes[b])) {
						b++;
						if (a >= inTypes.length) {
							a++;
							b = 0;
						}
					}
					if (inTypes[a] == outTypes[b]) {
						nearestPP = tmpPP;
					}
				}
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			return nearestPP;			
		}		
	}


}
