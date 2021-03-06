/* $Id: FormulaPanel.java,v 1.45 2004/10/20 16:04:53 shadowice Exp $
 * Created on 22.04.2004
 */
package gui;

import java.awt.*;
import java.util.*;

import formula.*;
import utils.*;

/**
 * The FormulaPanel is the workspace where users can create formula trees.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.45 $
 */
public class FormulaPanel extends Panel {

	private static final int MAX_PIN_DISTANCE	= 4900;	// (distance to a pin)^2 (mouseTargetPoint) to be of any interest (should be <100^2)
	private static final int OVERSIZE_WIDTH		= 5;	// pixels from left/right border 
	private static final int OVERSIZE_HEIGHT	= 5;	// pixels from bottom/top border

	// These lists store all input/output pins.
	private Vector inputPinList 	= new Vector();		// inputs of placed formulas
	private Vector outputPinList 	= new Vector();		// outputs of placed formulas
	private Vector tempInPPList		= new Vector();		// inputs of formulas currently selected (for displaying possible connections)
	private Vector tempOutPPList	= new Vector();		// ditto for outputs

	private AppletPanel aPanel;

	private Image bufferImage;							// used for double buffer (currently disabled due to slow speed)
	private int lastBufferWidth = 0;					// used to check if the buffer must be resized
	private int lastBufferHeight = 0;


	/**
	 * Creates a new FormulaPanel.
	 */
	public FormulaPanel(AppletPanel ap) {
		this.aPanel = ap;
		setLayout(new TreeLayout());
		setBackground(Color.white);
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
		for (int i=0;i<pPIn.size();i++) {
			inputPinList.addElement(pPIn.elementAt(i));
		}
	}


	/**
	 * Adds an input pin to the appropriate pin list.
	* @param pPIn input pin
	 */
	public void addInputPin(PinPoint pPIn) {
		if (pPIn.getTarget() != null) {
			pPIn.getTarget().setTarget(pPIn);
		}
		inputPinList.addElement(pPIn);
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
		for (int i=0;i<pPOut.size();i++) {
			outputPinList.addElement(pPOut.elementAt(i));
		}
	}


	/**
	 * Adds an output pin to the appropriate pin list.
	 * @param pPOut output pin
	 */
	public void addOutputPin(PinPoint pPOut) {
		if (pPOut.getTarget() != null) {
			pPOut.getTarget().setTarget(pPOut);
		}
		outputPinList.addElement(pPOut);
	}


	/**
	 * Used to remove a list of input pins from the pinList.
	 * @param pPIn list of input pins
	 */
	public void removeInputPins(PinPoint[] pPIn) {
		for (int i=0;i<pPIn.length;i++) {
			inputPinList.removeElement(pPIn[i]);
		}
	}


	/**
	 * Used to remove a list of output pins from the pinList.
	 * @param pPOut list of output pins
	 */
	public void removeOutputPins(PinPoint[] pPOut) {
		for (int i=0;i<pPOut.length;i++) {
			outputPinList.removeElement(pPOut[i]);
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


	/*public boolean isDoubleBuffered() {
		return false;
	}*/


	// NOTE faster double needed, this version is too slow!
	//public void update(Graphics g) {
		/*if ((bufferImage == null) || (getSize().width > lastBufferWidth) || (getSize().height > lastBufferHeight)) {
			lastBufferWidth = getSize().width;
			lastBufferHeight = getSize().height;
			bufferImage = createImage(lastBufferWidth,lastBufferHeight);
		}
		
		ScrollPane sp = (ScrollPane)getParent();
		int clip_x = sp.getScrollPosition().x;
		int clip_y = sp.getScrollPosition().y;
		int width = sp.getViewportSize().width;
		int height = sp.getViewportSize().height;
		
		Graphics bufferGraphics = bufferImage.getGraphics();
		paint(bufferGraphics);
		g.setClip(clip_x,clip_y,width,height);
		g.drawImage(bufferImage,0,0,this);
		bufferGraphics.dispose();*/
	//	paint(g);
	//}


	public void paint(Graphics g) {
		// clear graphics:
		//g.clearRect(0,0,getSize().width,getSize().height);
		// paint components in this container:
		super.paint(g);
		// paint connections between pins:
		PinPoint pp;
		g.setColor(Color.darkGray);
		for (int i=0;i<outputPinList.size();i++) {
			pp = (PinPoint)outputPinList.elementAt(i);
			//g.drawOval(pp.getMouseTargetPoint().x,pp.getMouseTargetPoint().y,5,5); // debug!
			if (pp.getTarget() != null) {
				g.drawLine(pp.getMouseTargetPoint().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}

		g.setColor(Color.red);
		for (int i=0;i<tempInPPList.size();i++) {
			pp = (PinPoint)tempInPPList.elementAt(i);
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
		for (int i=0;i<tempOutPPList.size();i++) {
			pp = (PinPoint)tempOutPPList.elementAt(i);
			if (pp.getTarget() != null) {
				g.drawLine(pp.getCoordinates().x,pp.getCoordinates().y,pp.getTarget().getCoordinates().x,pp.getTarget().getCoordinates().y);
			}
		}
	}


	/**
	 * Finds the nearest input pin to the outPin from the list and returns it if 
	 * it is compatible, has no target and is not from the same formula object.
	 * The PinPoint will also be returned if it was already found by
	 * another call of getNearestInputPin but this outPin is nearer to it.
	 * 
	 * The distance of the outPin to the returned PinPoint will be saved in the
	 * returned pin.bestDistance.
	 * 
	 * Note: PinPoint.bestCandidate must be set to != null, if another output pin wants
	 * to connect to this pin from the list. PinPoint.bestDistance stores the distance.
	 * If this distance is larger then to outPin, bestDistance is overwritten and the pin
	 * will be returned nethertheless.
	 * To check if getNearestInputPin returned a previously found input pin, you have to
	 * check if bestCandidate is != null. You also have to set bestCandidate outside of
	 * getNearestInputPin because it won't be set in it. 
	 * 
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
				tmpPP = (PinPoint)inputPinList.elementAt(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {  // if nearer then minDistance:
				// and if it has no target so far and the pins are not from the same formula-object:
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form)) {  
						// check for compatibility:
					 	if (tmpPP.getFormula().getInputCount() > 0) {
						 	inTypes = tmpPP.getFormula().getInputTypes(tmpPP.getInputNumber());
					 		if (FOMToolkit.hasPartialMatches(inTypes,outTypes)) {
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
	 * Finds the nearest output pin to the inPin from the list 
	 * and if it is compatible, has no target and is not from
	 * the same formula object it will be returned. 
	 * The PinPoint will also be returned if it was already found by another
	 * call of getNearestOutputPin but this inPin is nearer to it.
	 * 
	 * The distance of the inPin to the returned PinPoint will be saved in the
	 * returned pin.
	 * 
	 * Note: PinPoint.bestCandidate must be set to != null, if another input pin wants
	 * to connect to this pin from the list. PinPoint.bestDistance stores the distance.
	 * If this distance is larger then to inPin, bestDistance is overwritten and the pin
	 * will be returned nethertheless.
	 * To check if getNearestOutputPin returned a previously found output pin, you have to
	 * check if bestCandidate is != null. You also have to set bestCandidate outside of
	 * getNearestInputPin because it won't be set in it. 
	 * 
	 * @param inPin input pin
	 * @return returns the nearest output pin or null if nothing found
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
				tmpPP = (PinPoint)outputPinList.elementAt(i);
				distance = tmpPP.getDistance(x,y);
				if (distance < minDistance) {	// if nearer then minDistance:
				// and if it has no target so far and the pins are not from the same formula-object:
					if ((tmpPP.getTarget() == null) && (tmpPP.getFormula() != form)) {
					// check for compatibility:
						outTypes = tmpPP.getFormula().getOutputTypes();
						if (FOMToolkit.hasPartialMatches(inTypes,outTypes)) {
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
	 * Used only for graphical reasons. The two lists are possible connections and
	 * are used in the paint method to draw red lines for these connections.
	 * 
	 * @param ppInList List of input PinPoints.
	 * @param ppOutList List of output PinPoints.
	 */
	public void setTempPinPoints(Vector ppInList, Vector ppOutList) {
		tempInPPList = ppInList;
		tempOutPPList = ppOutList;
	}


	/**
	 * Detaches an input pin from it's target if it has one.
	 * 
	 * @param pin input pin to detach
	 */
	private void detachInput(PinPoint pin) {
		if (pin.getTarget() != null) {
			aPanel.getTreeList().addElement(pin.getTarget().getFormula());
			pin.getTarget().getFormula().setOutput(null);
			pin.getFormula().setInput(null,pin.getInputNumber());
			pin.getTarget().setTarget(null);
			pin.getTarget().setBestCandidate(null);
			pin.setTarget(null);
		}
		pin.setBestCandidate(null);
	}


	/**
	 * Detaches an output pin from it's target if it has one.
	 * 
	 * @param pin output pin to detach
	 */
	private void detachOutput(PinPoint pin) {
		if (pin.getTarget() != null) {
			pin.getTarget().getFormula().setInput(null,pin.getTarget().getInputNumber());
			pin.getFormula().setOutput(null);
			pin.getTarget().setTarget(null);
			pin.getTarget().setBestCandidate(null);
			pin.setTarget(null);
			aPanel.getTreeList().addElement(pin.getFormula());
		}
		pin.setBestCandidate(null);
	}


	/**
	 * Detaches an output of a formula (can also be root-formula of
	 * a whole subtree, doesn't matter) from the rest.
	 * 
	 * @param form Formula object to detach.
	 */
	public void detach(Formula form) {
		if (form.getOutput() != null) {
			PinPoint pin = form.getOutputPin();
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
	// NOTE Slow (if that matters) and not tested yet!!
	public void detach(Vector inPPList, Vector outPPList) {
		PinPoint pin;
		PinPoint targetPin;
		int j;
		boolean notfound;
		// detach all input-pins from inPPList, that are not connected to pins from outPPList
		for (int i=0;i<inPPList.size();i++) {
			pin = (PinPoint)inPPList.elementAt(i);
			targetPin = pin.getTarget();
			if (targetPin != null) {
				// is this pin in the outPPList?
				j = 0;
				notfound = true;
				while ((j<outPPList.size()) && notfound) {
					if ((PinPoint)outPPList.elementAt(i) == targetPin) {
						notfound = false;
					}
					j++;
				}
				if (notfound) {
					detachInput(pin);					// detach pins not connected to outPPList
				} else {
					outPPList.removeElement(targetPin); // prevents double-check
				}
			}
		}
		// detach all output that remain in outPPList:
		for (int i=0;i<outPPList.size();i++) {
			detachOutput((PinPoint)outPPList.elementAt(i));
		}
	}
	
	
	/**
	 * Attaches a list of PinPoints. The inputs/outputs will only be connected
	 * if they are still compatible. 
	 * 
	 * @param ppInList
	 * @param ppOutList
	 */
	public void attach(Vector ppInList, Vector ppOutList) {
		PinPoint pin;
		for (int i=0;i<ppInList.size();i++) {
			pin = (PinPoint)ppInList.elementAt(i);
			try {
				if (FOMToolkit.hasPartialMatches(pin.getFormula().getInputTypes(pin.getInputNumber()),pin.getTarget().getFormula().getOutputTypes())) {
					pin.getFormula().setInput(pin.getTarget().getFormula(),pin.getInputNumber());
					pin.getTarget().getFormula().setOutput(pin.getFormula());
					pin.getTarget().setTarget(pin);
					if (!inputPinList.contains(pin)) {
						inputPinList.addElement(pin);
					}
					aPanel.getTreeList().removeElement(pin.getTarget().getFormula());
				} else {
					pin.getTarget().getFormula().setOutput(null);
					pin.getTarget().setTarget(null);
					pin.getFormula().setInput(null,pin.getInputNumber());
					pin.setTarget(null);
				}
			} catch (FormulaException fe) {
				fe.printStackTrace(System.err);
			}
		}
		for (int i=0;i<ppOutList.size();i++) {
			pin = (PinPoint)ppOutList.elementAt(i);
			try {
				if (FOMToolkit.hasPartialMatches(pin.getFormula().getOutputTypes(),pin.getTarget().getFormula().getInputTypes(pin.getTarget().getInputNumber()))) {
					pin.getFormula().setOutput(pin.getTarget().getFormula());
					pin.getTarget().getFormula().setInput(pin.getFormula(),pin.getTarget().getInputNumber());
					pin.getTarget().setTarget(pin);
					if (!outputPinList.contains(pin)){
						outputPinList.addElement(pin);
					}
					aPanel.getTreeList().removeElement(pin.getFormula());
				} else {
					pin.getTarget().getFormula().setInput(null,pin.getTarget().getInputNumber());
					pin.getTarget().setTarget(null);
					pin.getFormula().setOutput(null);
					pin.setTarget(null);
				}
			} catch (FormulaException fe) {
				fe.printStackTrace(System.err);
			}
		}
		tempInPPList.removeAllElements();
		tempOutPPList.removeAllElements();
	}


	/**
	 * Detaches all inputs and outputs of a formula and removes it from the FormulaPanel
	 * and all lists it can be in.
	 * 
	 * @param form formula object to remove
	 */
	public void delete(Formula form) {
		PinPoint[] pinList = form.getInputPins();
		for (int i=0;i<pinList.length;i++) {
			detachInput(pinList[i]);
			inputPinList.removeElement(pinList[i]);
			tempInPPList.removeElement(pinList[i]);
		}
		detachOutput(form.getOutputPin());
		outputPinList.removeElement(form.getOutputPin());
		tempOutPPList.removeElement(form.getOutputPin());
		aPanel.getTreeList().removeElement(form);
		remove(form);
	}


	/**
	 * Deletes everything in the FormulaPanel.
	 */
	public void deleteAll() {
		ConstVarFormula cvForm;
		for (int i=0;i<outputPinList.size();i++) {
			if (((PinPoint)outputPinList.elementAt(i)).getFormula() instanceof ConstVarFormula) {
				cvForm = (ConstVarFormula)((PinPoint)outputPinList.elementAt(i)).getFormula();
				aPanel.getVariableList().deleteVarList(cvForm,cvForm.getInputVarName());
			}
		}
		inputPinList.removeAllElements();
		outputPinList.removeAllElements();
		tempInPPList.removeAllElements();
		tempOutPPList.removeAllElements();
		Formula[] treeList = aPanel.getTreeList().getTreeArray();
		for (int i=0;i<treeList.length;i++) {
			aPanel.getTreeList().removeElement(treeList[i]);
		}
		removeAll();
	}


	/**
	 * Recursivly adds a whole tree to the FormulaPanel.
	 * 
	 * @param form currrent Formula node to add
	 */
	public void addFormulaTree(Formula form) {
		form.init(aPanel);
		add(form);
		if (form.getOutput() == null) {
			aPanel.getTreeList().addElement(form);
		}
		outputPinList.addElement(form.getOutputPin());
		PinPoint[] ppList = form.getInputPins();
		for (int i=0;i<form.getInputCount();i++) {
			inputPinList.addElement(ppList[i]);
			if (form.getInput(i) != null) {
				addFormulaTree(form.getInput(i));	
			}
		}
	}

}
