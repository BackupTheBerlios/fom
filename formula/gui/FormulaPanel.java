/*
 * Created on 22.04.2004
 *
 */
package gui;

import formula.*;

import java.awt.*;
import java.util.*;

/**
 * The FormulaPanel displays the formula-trees, created by the user.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class FormulaPanel extends Panel {


	private static final int MAX_PIN_DISTANCE	= 70;	// distance to a pin (mouseTargetPoint) to be of any interest (should be <100)
	private static final int OVERSIZE_WIDTH		= 5;
	private static final int OVERSIZE_HEIGHT	= 5;


	// These lists store all input/output pins.
	private Vector inputPinList 		= new Vector();
	private Vector outputPinList 		= new Vector();
	private Vector tempInPPList		= new Vector();
	private Vector tempOutPPList	= new Vector();
	
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
	 * Adds an array of input pins to the appropriate pin list.
	 * @param pPIn array of input pins
	 */
	public void addInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			addInputPin(pPIn[i]);
		}
	}
	
	/**
	 * Adds a list (Vector) of input pins to the appropriate pin list.
	 * @param pPIn list of input pins
	 */
	public void addInputPins(Vector pPIn) {
		inputPinList.addAll(pPIn);
	}

	/**
	 * Adds an input pin to the appropriate pin list.
	* @param pPIn input pin
	 */
	public void addInputPin(PinPoint pPIn) {
		if (pPIn.getTarget() != null) {
			pPIn.getTarget().setTarget(pPIn);
		}
		inputPinList.add(pPIn);
	}

	/**
	 * Adds an array of output pins to the appropriate pin list.
	 * @param pPOut list of output pins
	 */
	public void addOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			addOutputPin(pPOut[i]);
		}
	}
	
	/**
	 * Adds a list (Vector) of output pins to the appropriate pin list.
	 * @param pPOut list of output pins
	 */
	public void addOutputPins(Vector pPOut) {
		outputPinList.addAll(pPOut);
	}

	/**
	 * Adds an output pin to the appropriate pin list.
	 * @param pPOut output pin
	 */
	public void addOutputPin(PinPoint pPOut) {
		if (pPOut.getTarget() != null) {
			pPOut.getTarget().setTarget(pPOut);
		}
		outputPinList.add(pPOut);
	}

	/**
	 * Used to remove a list of input pins from the pinList.
	 * @param pPIn list of input pins
	 */
	public void removeInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			inputPinList.remove(pPIn[i]);
		}
	}
	
	/**
	 * Used to remove a list of output pins from the pinList.
	 * @param pPOut list of output pins
	 */
	public void removeOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			outputPinList.remove(pPOut[i]);
		}
	}

	/**
	 * @return returns a vector of input pins from all formula elements in the panel
	 */
	public Vector getInputPins() {
		return inputPinList;
	}

	/**
	 * @return returns a vector of output pins from all formula elements in the panel
	 */
	public Vector getOutputPins() {
		return outputPinList;
	}


	public void paint(Graphics g) {
		super.paint(g);
		// paint connections between pins:
		PinPoint pp;
		g.setColor(Color.DARK_GRAY);
		for (int i=0;i<outputPinList.size();i++) {
			pp = (PinPoint)outputPinList.get(i);
			//g.drawOval(pp.getMouseTargetPoint().x,pp.getMouseTargetPoint().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getMouseTargetPoint().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
		//debug!:
		/*for (int i=0;i<inputPinList.size();i++) {
			pp = (PinPoint)inputPinList.get(i);
			g.drawOval(pp.getMouseTargetPoint().x,pp.getMouseTargetPoint().y,5,5);
		}*/
		
		g.setColor(Color.RED);
		for (int i=0;i<tempInPPList.size();i++) {
			pp = (PinPoint)tempInPPList.get(i);
			//g.drawOval(pp.getMouseTargetPoint().x,pp.getMouseTargetPoint().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
		for (int i=0;i<tempOutPPList.size();i++) {
			pp = (PinPoint)tempOutPPList.get(i);
			//g.drawOval(pp.getMouseTargetPoint().x,pp.getMouseTargetPoint().y,5,5); // debug!
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
		int minDistance = MAX_PIN_DISTANCE;		// pins farther away are not interesting
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
				 			boolean match = false;
				 			for (int i_in=0;i_in<inTypes.length;i_in++) {
				 				for (int i_out=0;i_out<outTypes.length;i_out++) {
				 					if (inTypes[i_in] == outTypes[i_out]) {
				 						match = true;				 			
				 					}
				 				}
				 			}
					 		if (match) {
								if (tmpPP.getBestCandidate() != null) {
									if (tmpPP.getBestDistance() > distance) {
										nearestPP = tmpPP;									
										minDistance = distance;
									}
								} else {
									minDistance = distance;
									nearestPP = tmpPP;
								}
						 	}
					 	}
					}					 	
				}
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			if (nearestPP != null) {
				nearestPP.setBestDistance(minDistance);
			}
			return nearestPP;			
		}		
	}

	/**
	 * Finds out if an output-pin is near the inPin and if they are compatible, have no target and are not from the same formula-object.
	 * @param inPin input-pin to test
	 * @return returns the nearest output-pin or null
	 */
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
		int minDistance = MAX_PIN_DISTANCE;		// pins farther away are not interesting
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
						boolean match = false;	
						for (int i_in=0;i_in<inTypes.length;i_in++) {
							for (int i_out=0;i_out<outTypes.length;i_out++) {
								if (inTypes[i_in] == outTypes[i_out]) {
									match = true;				 			
								}
							}
						}
						if (match) {
							// check if already temp-connected, but distance is lower:
							if (tmpPP.getBestCandidate() != null) {
								if (tmpPP.getBestDistance() > distance) {
									nearestPP = tmpPP;									
									minDistance = distance;
								}
							} else {
								minDistance = distance;
								nearestPP = tmpPP;
							}
						}
					}
				}
			}
		}catch (FormulaException fe) {
			fe.printStackTrace();
		} finally {
			if (nearestPP != null) {
				nearestPP.setBestDistance(minDistance);
			}
			return nearestPP;			
		}		
	}
	
	
	/**
	 * Used only for graphical reasons. The two lists are possible connections.
	 * @param ppInList List of input PinPoints.
	 * @param ppOutList List of output PinPoints.
	 */
	public void setTempPinPoints(Vector ppInList, Vector ppOutList) {
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
			aPanel.getTreeList().add(pin.getTarget().getFormula());
			pin.getTarget().getFormula().setOutput(null);
			pin.getFormula().setInput(null,pin.getInputNumber());
			pin.getTarget().setTarget(null);
			pin.getTarget().setBestCandidate(null);
			pin.setTarget(null);
		}
		pin.setBestCandidate(null);
	}
	
	private void detachOutput(PinPoint pin) {
		if (pin.getTarget() != null) {
			pin.getTarget().getFormula().setInput(null,pin.getTarget().getInputNumber());
			pin.getFormula().setOutput(null);
			pin.getTarget().setTarget(null);
			pin.getTarget().setBestCandidate(null);
			pin.setTarget(null);
			aPanel.getTreeList().add(pin.getFormula());
		}
		pin.setBestCandidate(null);
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
	// NOTE: Slow (if that matters) and not tested yet!
	public void detach(Vector inPPList, Vector outPPList) {
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
	public void attach(Vector ppInList, Vector ppOutList) {
		PinPoint pin;
		for (int i=0;i<ppInList.size();i++) {
			pin = (PinPoint)ppInList.get(i);
			pin.getFormula().setInput(pin.getTarget().getFormula(),pin.getInputNumber());
			pin.getTarget().getFormula().setOutput(pin.getFormula());
			pin.getTarget().setTarget(pin);
			if (!inputPinList.contains(pin)) {
				inputPinList.add(pin);
			}
			aPanel.getTreeList().remove(pin.getTarget().getFormula());
		}
		for (int i=0;i<ppOutList.size();i++) {
			pin = (PinPoint)ppOutList.get(i);
			pin.getFormula().setOutput(pin.getTarget().getFormula());
			pin.getTarget().getFormula().setInput(pin.getFormula(),pin.getTarget().getInputNumber());
			pin.getTarget().setTarget(pin);
			if (!outputPinList.contains(pin)){
				outputPinList.add(pin);
			}
			aPanel.getTreeList().remove(pin.getFormula());
		}
	}
	
	public void checkBounds() {
		Component comp;
		int width = getWidth();
		int height = getHeight();
		Dimension oldSize = new Dimension(getWidth(),getHeight());
		Rectangle newBounds = new Rectangle(0,0,0,0);
		for (int i=0;i<getComponentCount();i++) {
			comp = getComponent(i);
			if ((comp.getX()+comp.getWidth()) > newBounds.width) {
				newBounds.width = comp.getX() + comp.getWidth() + OVERSIZE_WIDTH;
			}
			if (comp.getX() < getX()) {
				newBounds.x = comp.getX();
			}
			if ((comp.getY()+comp.getHeight()) > newBounds.height) {
				newBounds.height = comp.getY() + comp.getHeight() + OVERSIZE_HEIGHT;
			}
			if (comp.getY() < getY()) {
				newBounds.y = comp.getY();
			}
		}

		Formula form;
		if ((newBounds.x < 0) || (newBounds.y < 0)) {
			if (newBounds.x < 0) {
				newBounds.width = newBounds.width - newBounds.x;
			}
			if (newBounds.y < 0) {
				newBounds.height = newBounds.height - newBounds.y;
			}
			
			for (int i=0;i<getComponentCount();i++) {
				form = (Formula)getComponent(i);
				form.moveTo(form.getX()-newBounds.x+OVERSIZE_WIDTH,form.getY()-newBounds.y+OVERSIZE_HEIGHT);
			}
			//ScrollPane sPane = (ScrollPane)getParent();
			//sPane.setScrollPosition(sPane.getScrollPosition().x,0);
		}
		
		if ((newBounds.width > 0) && (newBounds.height > 0) && !oldSize.equals(newBounds.getSize())) {
			setSize(newBounds.width,newBounds.height);
			getParent().validate();
		}
	}

	public void delete(Formula form) {
		PinPoint[] pinList = form.getInputPins();
		for (int i=0;i<pinList.length;i++) {
			detachInput(pinList[i]);
			inputPinList.remove(pinList[i]);
			tempInPPList.remove(pinList[i]);
		}
		detachOutput(form.getOutputPin());
		outputPinList.remove(form.getOutputPin());
		tempOutPPList.remove(form.getOutputPin());
		aPanel.getTreeList().remove(form);
		remove(form);
	}

	
	/**
	 * Deletes everything in the FormulaPanel.
	 */
	public void deleteAll() {
		ConstVarFormula cvForm;
		for (int i=0;i<outputPinList.size();i++) {
			if (((PinPoint)outputPinList.get(i)).getFormula() instanceof ConstVarFormula) {
				cvForm = (ConstVarFormula)((PinPoint)outputPinList.get(i)).getFormula();
				ConstVarFormula.deleteVarList(cvForm,cvForm.getInputVarName());
			}
		}
		inputPinList.clear();
		outputPinList.clear();
		tempInPPList.clear();
		tempOutPPList.clear();
		Formula[] treeList = aPanel.getTreeList().getTreeArray();
		for (int i=0;i<treeList.length;i++) {
			aPanel.getTreeList().remove(treeList[i]);
		}
		removeAll();
	}

}
