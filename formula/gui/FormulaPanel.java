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
	private LinkedList tempInPPList		= new LinkedList();
	private LinkedList tempOutPPList	= new LinkedList();
	
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
		for (int i=0;i<tempInPPList.size();i++) {
			pp = (PinPoint)tempInPPList.get(i);
			g.drawOval(pp.getCoordinates().x,pp.getCoordinates().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
		for (int i=0;i<tempOutPPList.size();i++) {
			pp = (PinPoint)tempOutPPList.get(i);
			g.drawOval(pp.getCoordinates().x,pp.getCoordinates().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}

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
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form) && (!tmpPP.getMark())) {  
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
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form) && (!tmpPP.getMark())) {
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

	
	
	
	/**
	 * Used only for graphical reasons. The two lists are possible connections.
	 * @param ppInList List of input PinPoints.
	 * @param ppOutList List of output PinPoints.
	 */
	public void setTempPinPoints(LinkedList ppInList, LinkedList ppOutList) {
		tempInPPList = ppInList;
		tempOutPPList = ppOutList;
	}


	/**
	 * Searches for the input PinPoint representing the inNumber-th input of form.
	 * @param form formula to search for
	 * @param inNumber number of the input (from(0) left to right(length-1))
	 * @return returns the PinPoint if found, otherwise null.
	 */
	public PinPoint getInputPinForFormula(Formula form, int inNumber) {
		PinPoint pin;
		for (int i=0;i<inputPinList.size();i++) {
			pin = (PinPoint)inputPinList.get(i);
			if (pin.getFormula() == form) {
				if (inNumber == pin.getInputNumber()) {
					return pin;
				}
			}
		}
		return null;  // shouldn't happen if everything's all right!
	}

	/**
	 * Searches for the output PinPoint representing the output of form.
	 * @param form formula to search for
	 * @return the PinPoint if found, otherwise null.
	 */
	public PinPoint getOutputPinForFormula(Formula form) {
		PinPoint pin;
		for (int i=0;i<outputPinList.size();i++) {
			pin = (PinPoint)outputPinList.get(i);
			if (pin.getFormula() == form) {
				return pin;
			}
		}
		return null;  // shouldn't happen if everything's all right!
	}



	private void detachInput(PinPoint pin) {
		if (pin.getTarget() != null) {
			pin.getTarget().getFormula().setOutput(null);
			pin.getFormula().setInput(null,pin.getInputNumber());
			pin.getTarget().setTarget(null);
			pin.setTarget(null);
		}
	}
	
	private void detachOutput(PinPoint pin) {
		if (pin.getTarget() != null) {
			pin.getTarget().getFormula().setInput(null,pin.getTarget().getInputNumber());
			pin.getFormula().setOutput(null);
			pin.getTarget().setTarget(null);
			pin.setTarget(null);
		}
	}

	/**
	 * Detaches an output of a formula (can also be root-formula of
	 * a whole subtree, doesn't matter) from the rest.
	 * @param form Formula object to detach.
	 */
	public void detach(Formula form) {
		if (form.getOutput() != null) {
			PinPoint pin = getOutputPinForFormula(form);	// O(n)
			if (pin != null) {
				detachOutput(pin);
				outputPinList.remove(pin);
			}
		}
	}
	
	
	/**
	 * Detaches a list of formula elements. Elements that have a connection to
	 * another element in the list don't loose their connection.
	 * 
	 * @param inPPList input pins
	 * @param outPPList output pins
	 */
	public void detach(LinkedList inPPList, LinkedList outPPList) {
		PinPoint pin;
		PinPoint targetPin;
		int j;
		boolean notfound;
		// detach all input-pins from inPPList, that are not connected to pins from outPPList
		for (int i=0;i<inPPList.size();i++) {
			pin = (PinPoint)inPPList.get(i);
			targetPin = pin.getTarget();
			if (targetPin != null) {
				// is this pin in the outPPList?
				j = 0;
				notfound = true;
				while ((j<outPPList.size()) && notfound) {
					if ((PinPoint)outPPList.get(i) == targetPin) {
						notfound = false;
					}
					j++;
				}
				if (notfound) {
					detachInput(pin);				// detach pins not connected to outPPList
				} else {
					outPPList.remove(targetPin); 	// prevents double-check
				}
			}
		}
		// detach all output that remain in outPPList:
		for (int i=0;i<outPPList.size();i++) {
			detachOutput((PinPoint)outPPList.get(i));
		}
	}
	
	/**
	 * Attaches a list of PinPoints. 
	 * @param ppInList
	 * @param ppOutList
	 */
	public void attach(LinkedList ppInList, LinkedList ppOutList) {
		PinPoint pin;
		for (int i=0;i<ppInList.size();i++) {
			pin = (PinPoint)ppInList.get(i);
			pin.getFormula().setInput(pin.getTarget().getFormula(),pin.getInputNumber());
			pin.getTarget().getFormula().setOutput(pin.getFormula());
			pin.getTarget().setTarget(pin);
			inputPinList.add(pin);
		}
		for (int i=0;i<ppOutList.size();i++) {
			pin = (PinPoint)ppOutList.get(i);
			pin.getFormula().setOutput(pin.getTarget().getFormula());
			pin.getTarget().getFormula().setInput(pin.getFormula(),pin.getTarget().getInputNumber());
			pin.getTarget().setTarget(pin);
			outputPinList.add(pin);
		}
	}

}
