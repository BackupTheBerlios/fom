/*
 * Created on 12.08.2004
 *
 */
package gui;

import java.util.*;
import formula.*;
import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Selection {


	public static final int MOUSE_POINT_DISTANCE = 15;

	private AppletPanel aPanel				= null;				// root panel of everything
	//private FormulaPanel fPanel				= null;				// formula panel
	
	private boolean dragInProgress			= false;			// true if something is dragged
	private boolean insertInProgress		= false;			// true if element is selected from the ElementPanel
	private Point selectedStartPoint		= null;				// the starting point of DnD actions
	private Point selectedRelativePoint		= null;				// the relative point within the dragged element
	private Formula selectedComponentRoot	= null;				// component that is dragged or selected (or root of a component)
	private LinkedList selectedComponents	= new LinkedList(); // list of all selected formula-objects
	private Formula newComponentInstance 	= null;				// new instance of a selected component
	private LinkedList pPInputs				= new LinkedList();	// list of input PinPoints of the selected element(s)
	private LinkedList pPOutputs			= new LinkedList();	// list of output PinPoints of the selected element(s)
	private LinkedList tempPPInputs			= new LinkedList(); // list of temporary connections between input and output
	private LinkedList tempPPOutputs		= new LinkedList(); // list of temporary connections between output and input
	private LinkedList inactivePPInputs		= new LinkedList(); // list of input pins, already connected to the selection (only move around)
	private LinkedList inactivePPOutputs	= new LinkedList(); // list of output pins, already connected to the selection (only move around)


	/**
	 * 
	 */
	public Selection(AppletPanel ap) {
		super();
		aPanel = ap;
		//fPanel = aPanel.getFormulaPanel();
	}
	
	
	/**
	 * Moves a formula, all its sub-formulas and all pPInputs and pPOutputs (relative) to a given point.
	 * This point will be the top left corner of the formula. 
	 * @param formula the formula to move
	 * @param point target point to move to
	 */
	public void moveTo(Formula formula, Point point) {
		Point location = formula.getLocation();
		int xOffset = point.x - location.x;
		int yOffset = point.y - location.y;
		Formula form;
		for (int i=0;i<selectedComponents.size();i++) {
			form = (Formula)selectedComponents.get(i);
			location = form.getLocation();
			location.translate(xOffset,yOffset);
			form.moveTo(location.x,location.y);
		}
		if (newComponentInstance != null) {
			location = newComponentInstance.getLocation();
			location.translate(xOffset,yOffset);
			newComponentInstance.moveTo(location.x,location.y);
		}
	}
	
	/**
	 * Refreshs the list of connections to other formula elements.
	 */
	// NOTE: would probably be better in a thread
	public void refreshPinPointList() {
		PinPoint targetPP;
		PinPoint pin;
		int index;
		// clear marks:
		for (int i=0;i<tempPPInputs.size();i++) {
			((PinPoint)tempPPInputs.get(i)).getTarget().setBestCandidate(null);
		}
		tempPPInputs.clear();
		for (int i=0;i<tempPPOutputs.size();i++) {
			((PinPoint)tempPPOutputs.get(i)).getTarget().setBestCandidate(null);
		}
		tempPPOutputs.clear();
		// Outputs:
		Stack ppStack = new Stack();
		ppStack.addAll(pPOutputs);
		while (!ppStack.isEmpty()) {
			pin = (PinPoint)ppStack.pop();
			targetPP = aPanel.getFormulaPanel().getNearestInputPin(pin);
			if (targetPP != null) {
				if (targetPP.getBestCandidate() != null) {
					// special case if pin is nearer than another pin
					tempPPOutputs.remove(targetPP.getBestCandidate());
					targetPP.getBestCandidate().setTarget(null);
					ppStack.push(targetPP.getBestCandidate());
				}
				pin.setTarget(targetPP);
				targetPP.setBestCandidate(pin);
				tempPPOutputs.add(pin);
			} else {
				pin.setTarget(null);
			}
		}
		// Inputs:
		ppStack.addAll(pPInputs);
		while (!ppStack.isEmpty()) {
			pin = (PinPoint)ppStack.pop();			
			targetPP = aPanel.getFormulaPanel().getNearestOutputPin(pin);
			if (targetPP != null) {
				if (targetPP.getBestCandidate() != null) {
					// special case if pin is nearer than another pin
					tempPPInputs.remove(targetPP.getBestCandidate());
					targetPP.getBestCandidate().setTarget(null);
					ppStack.push(targetPP.getBestCandidate());
				}
				pin.setTarget(targetPP);
				targetPP.setBestCandidate(pin);
				tempPPInputs.add(pin);
			} else {
				pin.setTarget(null);
			}
		}
	}
	
	
	/**
	 * Selects a whole subtree with form as the root.
	 * @param form root formula
	 */
	public void recursiveSelect(Formula form,Point point) {
		dragInProgress = true;
		selectedComponentRoot = form;
		form.requestFocus();
		selectedStartPoint = selectedComponentRoot.getLocation();
		selectedRelativePoint = new Point((int)(selectedStartPoint.getX()-point.getX()),(int)(selectedStartPoint.getY()-point.getY()));
		aPanel.getFormulaPanel().detach(selectedComponentRoot);
		recursiveSelectRec(form);
		aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
		
	
	/**
	 * Recursive method for recursiveSelect.
	 * @param form formula to select
	 */
	private void recursiveSelectRec(Formula form) {
				
		selectedComponents.add(form);
		// get input PinPoints:
		LinkedList ppList = aPanel.getFormulaPanel().getInputPins();
		PinPoint pin;
		for (int i=0; i<ppList.size();i++) {
			pin = (PinPoint)ppList.get(i);
			if (pin.getFormula() == form) {
				if (pin.getTarget() != null) {
					inactivePPInputs.add(pin);
				} else {
					pPInputs.add(pin);
				}
			}
		}

		// get output PinPoints:
		ppList = aPanel.getFormulaPanel().getOutputPins();
		for (int i=0; i<ppList.size();i++) {
			pin = (PinPoint)ppList.get(i);
			if (pin.getFormula() == form) {
				if (pin.getTarget() != null) {
					inactivePPOutputs.add(pin);
				} else {
					pPOutputs.add(pin);
				}
				
			}
		}

		// select next formulas from this formula's inputs
		for (int i=0; i<form.getInputCount();i++) {
			if (form.getInput(i) != null) {
				recursiveSelectRec(form.getInput(i));
			}
		}
	}
	
	
	/**
	 * Updates all selected elements to a new paintStatus.
	 * @param status paintStatus from Formula
	 */
	public void updatePaintStatus(int status) {
		Formula form;
		for (int i=0;i<selectedComponents.size();i++) {
			form = (Formula)selectedComponents.get(i);
			form.setPaintStatus(status);
		}
	}
	
	/**
	 * Resets everything to it's init-state. 
	 */
	public void deselect() {
		dragInProgress = false;
		if (insertInProgress && (newComponentInstance instanceof ConstVarFormula)) {
			ConstVarFormula cvForm = (ConstVarFormula)newComponentInstance;
			ConstVarFormula.deleteVarList(cvForm,cvForm.getInputVarName());
		}
		insertInProgress = false;
		pPInputs.clear();
		pPOutputs.clear();
		tempPPInputs.clear();
		tempPPOutputs.clear();
		inactivePPInputs.clear();
		inactivePPOutputs.clear();
		if (newComponentInstance != null) {
			aPanel.getFormulaPanel().remove(newComponentInstance);
			newComponentInstance = null;
		}
		if (selectedComponentRoot != null) {
			selectedComponentRoot.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
			selectedComponentRoot = null;	
		}
		if (!selectedComponents.isEmpty()) {
			updatePaintStatus(Formula.PAINTSTATUS_STANDARD);
			selectedComponents.clear();
		}
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
		aPanel.requestFocus();
	}
	
	/**
	 * Selects a formula element in the ElementPanel, creates a new instance of it
	 * and adds it to the FormulaPanel (invisible until mouse enters FormulaPanel).
	 * Also creates the PinPoints for this formula element.
	 * 
	 * @param targetComponent the target formula element
	 */
	public void selectNewElement(Formula targetFormula) {
		// remove previously selected element if there is one:
		deselect();
		insertInProgress = true;
		selectedComponentRoot = targetFormula;
		selectedComponentRoot.setPaintStatus(Formula.PAINTSTATUS_SELECTED);
		selectedComponentRoot.repaint();
		//create new instance:
		try {
			newComponentInstance = (Formula)selectedComponentRoot.getClass().newInstance();
			newComponentInstance.setVisible(false);	//not visible as long as mouse outside of formula-panel
			aPanel.getFormulaPanel().add(newComponentInstance);
			newComponentInstance.setPaintStatus(Formula.PAINTSTATUS_INSERTING);
			aPanel.getFormulaPanel().repaint();
			newComponentInstance.addKeyListener(aPanel.getHotkeyListener());
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (InstantiationException ie) {
			ie.printStackTrace();
		}
		//create PinPoint lists for inputs & output
		//newComponentInstance may be null if newInstance was unsuccessful
		int xPos = newComponentInstance.getX();
		int yPos = newComponentInstance.getY();
		int width = newComponentInstance.getWidth();
		int height = newComponentInstance.getHeight();
		int inCount = newComponentInstance.getInputCount();
		pPInputs.clear();
		pPOutputs.clear();
		PinPoint pp;
		PinPoint[] ppArray = new PinPoint[inCount];
		for (int i=0;i<inCount;i++) {
			pp = new PinPoint(newComponentInstance,xPos+(i+1)*width/(inCount+1),yPos+height,i);
			pp.setMouseTargetPoint(xPos-width/2+(i+1)*width*2/(inCount+1),yPos+height+MOUSE_POINT_DISTANCE);
			pPInputs.add(pp);
			ppArray[i] = pp;
		}
		newComponentInstance.setInputPins(ppArray);
		pp = new PinPoint(newComponentInstance,xPos+width/2,yPos);
		pp.setMouseTargetPoint(xPos+width/2,yPos-MOUSE_POINT_DISTANCE);
		pPOutputs.add(pp);
		newComponentInstance.setOutputPin(pp);
		newComponentInstance.setLocation(xPos,yPos); // bugfix, newComponentInstance may be at another position
		aPanel.getFormulaPanel().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		aPanel.getFormulaPanel().validate();
	}


	public void placeElement(Point point) {
		insertInProgress = false;
		newComponentInstance.moveTo((int)point.getX()-newComponentInstance.getWidth()/2,(int)point.getY()-newComponentInstance.getHeight()/2);
		selectedComponentRoot.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
		newComponentInstance.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
		newComponentInstance.setVisible(true);
		Formula.addTree(newComponentInstance);
		newComponentInstance = null;
		aPanel.getFormulaPanel().setCursor(Cursor.getDefaultCursor());
		// add PinPoints to FormulaPanel:
		for (int i=0;i<pPInputs.size();i++) {
			aPanel.getFormulaPanel().addInputPin((PinPoint)pPInputs.get(i));
		}
		for (int i=0;i<pPOutputs.size();i++) {
			aPanel.getFormulaPanel().addOutputPin((PinPoint)pPOutputs.get(i));
		}
		aPanel.getFormulaPanel().attach(tempPPInputs,tempPPOutputs);
		deselect();
		aPanel.getFormulaPanel().doLayout();
		aPanel.getFormulaPanel().checkBounds();
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();		
	}

	/**
	 * Check if the user is inserting an element.
	 * @return true if inserting
	 */
	public boolean isInsertInProgress() {
		return insertInProgress;
	}
	
	/**
	 * Check if the user is dragging something.
	 * @return true if dragging
	 */
	public boolean isDragInProgress() {
		return dragInProgress;
	}
	
	/**
	 * Sets the visibility of an element.
	 * @param b true=visible
	 */
	public void setElementVisible(boolean b) {
		newComponentInstance.setVisible(b);
	}

	/**
	 * Called at the end of a drag&drop operation.
	 */
	public void dragEnd() {
		dragInProgress = false;
		aPanel.setCursor(Cursor.getDefaultCursor());
		updatePaintStatus(Formula.PAINTSTATUS_SELECTED);
		aPanel.getFormulaPanel().attach(tempPPInputs,tempPPOutputs);
		aPanel.getFormulaPanel().doLayout();
		aPanel.getFormulaPanel().checkBounds();
		aPanel.getFormulaPanel().repaint();
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
	}
	

	/**
	 * Moves an element/tree around if dragInProgress or insertInProgress.
	 * @param point where to move to
	 */	
	public void move(Point point) {
		if (dragInProgress) {
			moveTo(selectedComponentRoot,new Point(selectedRelativePoint.x+point.x,selectedRelativePoint.y+point.y));
			// update possible connections:
			refreshPinPointList();
			aPanel.getFormulaPanel().setTempPinPoints(tempPPInputs,tempPPOutputs);
			aPanel.getFormulaPanel().repaint();
		} else if (insertInProgress) {
			point.translate(-newComponentInstance.getWidth()/2,-newComponentInstance.getHeight()/2);
			moveTo(newComponentInstance,point);
			// update possible connections:
			refreshPinPointList();
			aPanel.getFormulaPanel().setTempPinPoints(tempPPInputs,tempPPOutputs);
			aPanel.getFormulaPanel().repaint();
			//aPanel.getFormulaPanel().checkBounds();
		}
	}
	
	public void delete() {
		if (selectedComponents.size() > 0) {
			Formula form;
			for (int i=0;i<selectedComponents.size();i++) {
				form = (Formula)selectedComponents.get(i);
				if (form instanceof ConstVarFormula) {
					ConstVarFormula cvForm = (ConstVarFormula)form;
					ConstVarFormula.deleteVarList(cvForm,cvForm.getInputVarName());
				}
				aPanel.getFormulaPanel().delete(form);
			}
			aPanel.getFormulaPanel().validate();
			aPanel.getFormulaPanel().doLayout();
			aPanel.getFormulaPanel().checkBounds();
			aPanel.getFormulaPanel().repaint();
		}
	}


}
