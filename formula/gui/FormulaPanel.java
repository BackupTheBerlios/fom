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
	private LinkedList inputPinList 	= new LinkedList();
	private LinkedList outputPinList 	= new LinkedList();
	private LinkedList tempPPList		= new LinkedList();
	//private LinkedList tempInPPList		= new LinkedList();
	//private LinkedList tempOutPPList		= new LinkedList();
	private AppletPanel aPanel;

	/**
	 * Creates a new FormulaPanel.
	 */
	public FormulaPanel(AppletPanel ap) {
		this.aPanel = ap;
		setLayout(new TreeLayout());
		setBackground(SystemColor.text);
	}
	
	
	/**
	 * @param pPIn
	 */
	public void addInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			addInputPin(pPIn[i]);
		}
	}

	/**
	* @param pPIn
	 */
	public void addInputPin(PinPoint pPIn) {
		if (pPIn.getTarget() != null) {
			pPIn.getTarget().setTarget(pPIn);
		}
		inputPinList.add(pPIn);
	}

	/**
	 * @param pPOut
	 */
	public void addOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			addOutputPin(pPOut[i]);
		}
	}

	/**
	 * @param pPOut
	 */
	public void addOutputPin(PinPoint pPOut) {
		if (pPOut.getTarget() != null) {
			pPOut.getTarget().setTarget(pPOut);
		}
		outputPinList.add(pPOut);
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
	public LinkedList getInputPins() {
		return inputPinList;
	}

	/**
	 * @return
	 */
	public LinkedList getOutputPins() {
		return outputPinList;
	}


	/**
	 * Finds out if an input-pin is near the outPin and if they are compatible, have no target and are not from the same formula-object. 
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
		Formula form = outPin.getFormula();
		int minDistance = maxPinDistance;		// pins farther away are not interesting
		int distance = 0;
		try {
			Class[] inTypes;
			Class[] outTypes = outPin.getFormula().getOutputTypes();
			for (int i=0;i<size;i++) {
				tmpPP = (PinPoint)inputPinList.get(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {  // if nearer then minDistance:
				// and if it has no target so far and the pins are not from the same formula-object:
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form)) {  
						// check for compatibility:
					 	if (tmpPP.getFormula().getInputCount() > 0) {
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
				}
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			return nearestPP;			
		}		
	}

	/**
	 * Finds out if an output-pin is near the inPin and if they are compatible, have no target and are not from the same formula-object.
	 * @param inPin input-pin to test
	 * @return returns the nearest output-pin or null
	 */
	// NOTE: combine with getNearestInputPin?
	public PinPoint getNearestOutputPin(PinPoint inPin) {
		if (inPin.getFormula().getInputCount() == 0) {
			return null;
		}
		int size = outputPinList.size();
		int x = inPin.getCoordinates().x;
		int y = inPin.getCoordinates().y;
		PinPoint nearestPP = null;
		PinPoint tmpPP = null;
		Formula tmpForm = null;
		Formula form = inPin.getFormula();
		int minDistance = maxPinDistance;		// pins farther away are not interesting
		int distance = 0;
		try {
			Class[] inTypes = inPin.getFormula().getInputTypes(inPin.getInputNumber());
			Class[] outTypes;
			for (int i=0;i<size;i++) {
				tmpPP = (PinPoint)outputPinList.get(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {	// if nearer then minDistance:
				// and if it has no target so far and the pins are not from the same formula-object:
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form)) {
					// check for compatibility:
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
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			return nearestPP;			
		}		
	}

	
	public void paint(Graphics g) {
		super.paint(g);
		// paint connections between pins:
		PinPoint pp;
		g.setColor(Color.DARK_GRAY);
		for (int i=0;i<outputPinList.size();i++) {
			pp = (PinPoint)outputPinList.get(i);
			g.drawOval(pp.getCoordinates().x,pp.getCoordinates().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
		g.setColor(Color.RED);
		for (int i=0;i<tempPPList.size();i++) {
			pp = (PinPoint)tempPPList.get(i);
			g.drawOval(pp.getCoordinates().x,pp.getCoordinates().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
	}
	
	public void setTempPinPoints(LinkedList ppList) {
		tempPPList = ppList;
	}


	public void detach(LinkedList ppList) {
		
	}
	
	public void attach(LinkedList ppList) {
		
	}


	/*public void setTempInputPinPoints(LinkedList ppList) {
		tempInPPList = ppList;
	}
	
	public void setTempOutputPinPoints(LinkedList ppList) {
		tempOutPPList = ppList;
	}
	
	public void clearTempPinPoints() {
		tempInPPList.clear();
		tempOutPPList.clear();
	}
	
	public void connectTempPinPoints() {
	
	}*/

}
