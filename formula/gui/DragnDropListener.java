/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import formula.*;

/**
 * Listener that handles drag&drop as well as point&click
 * for (re)placing formula-elements or whole trees and connecting them.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	public static final int MOUSE_POINT_DISTANCE = 25;

	private static AppletPanel aPanel			= null;				// root panel of everything
	
	private static boolean dragInProgress		= false;			// true if something is dragged
	private static boolean insertInProgress		= false;			// true if element is selected from the ElementPanel
	private static Point selectedStartPoint		= null;				// the starting point of DnD actions
	private static Point selectedRelativePoint	= null;				// the relative point within the dragged element
	private static Formula selectedComponentRoot= null;				// component that is dragged or selected (or root of a component)
	private static LinkedList selectedComponents= new LinkedList(); // list of all selected formula-objects
	private static Formula newComponentInstance = null;				// new instance of a selected component
	private static LinkedList pPInputs			= new LinkedList();	// list of input PinPoints of the selected element(s)
	private static LinkedList pPOutputs			= new LinkedList();	// list of output PinPoints of the selected element(s)
	private static LinkedList tempPPInputs		= new LinkedList(); // list of temporary connections between input and output
	private static LinkedList tempPPOutputs		= new LinkedList(); // list of temporary connections between output and input
	private static LinkedList inactivePPInputs	= new LinkedList(); // list of input pins, already connected to the selection (only move around)
	private static LinkedList inactivePPOutputs	= new LinkedList(); // list of output pins, already connected to the selection (only move around)

	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
	}

	public void mouseClicked(MouseEvent me) {
		// if event happened in the ElementPanel:
		if (me.getComponent().getParent().getParent() instanceof ElementPanel) {
			Component targetComponent = me.getComponent().getComponentAt(me.getPoint());
			if (targetComponent != null) { // if clicked on a Formula-object
				selectNewElement((Formula)targetComponent);
			}
		// if event happened in the FormulaPanel:
		} else if (me.getComponent() instanceof FormulaPanel){
			// if a component is selected from the ElementPanel (newComponentInstance != null),
			// a mouseClicked event within the FormulaPanel will place it there.
			if (insertInProgress) {
				insertInProgress = false;
				newComponentInstance.setLocation((int)me.getPoint().getX()-newComponentInstance.getWidth()/2,(int)me.getPoint().getY()-newComponentInstance.getHeight()/2);
				selectedComponentRoot.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
				newComponentInstance.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
				newComponentInstance = null;
				aPanel.getFormulaPanel().setCursor(Cursor.getDefaultCursor());
				aPanel.getFormulaPanel().doLayout();
				// add PinPoints to FormulaPanel:
				for (int i=0;i<pPInputs.size();i++) {
					aPanel.getFormulaPanel().addInputPin((PinPoint)pPInputs.get(i));
				}
				for (int i=0;i<pPOutputs.size();i++) {
					aPanel.getFormulaPanel().addOutputPin((PinPoint)pPOutputs.get(i));
				}
				aPanel.getFormulaPanel().attach(tempPPInputs,tempPPOutputs);
				deselect();
			} else if (!(me.getComponent().getComponentAt(me.getPoint()) instanceof Formula)) {
				deselect();
			}
			
		}
	}

	public void mouseEntered(MouseEvent me) {
		if (me.getComponent() instanceof FormulaPanel) {
			if (insertInProgress) {
				newComponentInstance.setVisible(true);
			}
		}
		
	}

	public void mouseExited(MouseEvent me) {
		if (me.getComponent() instanceof FormulaPanel) {
			if (insertInProgress) {
				newComponentInstance.setVisible(false);
			}
		}
	}

	public void mousePressed(MouseEvent me) {
		// mouse pressed over a Formula in the FormulaPanel:
		if (me.getComponent() instanceof FormulaPanel) {
			if (!insertInProgress && me.getComponent().getComponentAt(me.getPoint()) instanceof Formula) {
				deselect();
				dragInProgress = true;
				// initiate drag&drop:
				selectedComponentRoot = (Formula)me.getComponent().getComponentAt(me.getPoint());
				selectedStartPoint = selectedComponentRoot.getLocation();
				selectedRelativePoint = new Point((int)(selectedStartPoint.getX()-me.getPoint().getX()),(int)(selectedStartPoint.getY()-me.getPoint().getY()));
				aPanel.getFormulaPanel().detach(selectedComponentRoot);
				recursiveSelect(selectedComponentRoot);
				updatePaintStatus(Formula.PAINTSTATUS_MOVING);
				aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		if (dragInProgress) {
			dragInProgress = false;
			aPanel.setCursor(Cursor.getDefaultCursor());
			updatePaintStatus(Formula.PAINTSTATUS_SELECTED);
			aPanel.getFormulaPanel().attach(tempPPInputs,tempPPOutputs);
		}
		aPanel.getFormulaPanel().repaint();
	}

	public void mouseDragged(MouseEvent me) {
		if ((dragInProgress) && (selectedComponentRoot != null)) {
			// dragging only works in the FormulaPanel (atm)
			if (me.getComponent() instanceof FormulaPanel) {
				// move to new location:
				Point p = me.getPoint();
				moveTo(selectedComponentRoot,new Point(selectedRelativePoint.x+p.x,selectedRelativePoint.y+p.y));
				// update possible connections:
				refreshPinPointList();
				aPanel.getFormulaPanel().setTempPinPoints(tempPPInputs,tempPPOutputs);
				aPanel.getFormulaPanel().repaint();
			}
		}
	}
	
	/*
	 * Used when a component is selected from the ElementPanel and the mouse is within
	 * the FormulaPanel to place it there.
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me) {
		if (me.getComponent() instanceof FormulaPanel) {
			if (insertInProgress) {
				// move to new location:
				Point point = me.getPoint();
				point.translate(-newComponentInstance.getWidth()/2,-newComponentInstance.getHeight()/2);
				moveTo(newComponentInstance,point);
				// update possible connections:
				refreshPinPointList();
				aPanel.getFormulaPanel().setTempPinPoints(tempPPInputs,tempPPOutputs);
				aPanel.getFormulaPanel().repaint();
			}
		}
	}
	
	
	/**
	 * Moves a formula, all its sub-formulas and all pPInputs and pPOutputs (relative) to a given point.
	 * This point will be the top left corner of the formula. 
	 * @param formula the formula to move
	 * @param point target point to move to
	 */
	private void moveTo(Formula formula, Point point) {
		Point oldLocation = formula.getLocation();
		int xOffset = point.x - oldLocation.x;
		int yOffset = point.y - oldLocation.y;
		for (int i=0;i<pPInputs.size();i++) {
			((PinPoint)pPInputs.get(i)).translate(xOffset,yOffset); 
		}
		for (int i=0;i<pPOutputs.size();i++) {
			((PinPoint)pPOutputs.get(i)).translate(xOffset,yOffset);
		}
		for (int i=0;i<inactivePPInputs.size();i++) {
			((PinPoint)inactivePPInputs.get(i)).translate(xOffset,yOffset); 
		}
		for (int i=0;i<inactivePPOutputs.size();i++) {
			((PinPoint)inactivePPOutputs.get(i)).translate(xOffset,yOffset); 
		}
		Formula form;
		for (int i=0; i<selectedComponents.size();i++) {
			form = (Formula)selectedComponents.get(i);
			oldLocation = form.getLocation();
			form.setLocation(oldLocation.x+xOffset,oldLocation.y+yOffset);
		}
		if (newComponentInstance != null) {
			oldLocation = newComponentInstance.getLocation();
			newComponentInstance.setLocation(oldLocation.x+xOffset,oldLocation.y+yOffset);
		}
	}
	
	/**
	 * Refreshs the list of connections to other formula elements.
	 */
	// NOTE: would probably be better in a thread
	// BUG: Wenn 2 Input-Pins der selektierten Elemente in Reichweite eines Output-Pins sind,
	// dann wird der erste in der Liste verbunden, und der 2. nicht....egal wie weit sie entfernt sind! 	
	private void refreshPinPointList() {
		FormulaPanel fp = aPanel.getFormulaPanel();
		PinPoint targetPP;
		PinPoint pin;
		int index;
		// clear marks:
		for (int i=0;i<tempPPInputs.size();i++) {
			((PinPoint)tempPPInputs.get(i)).getTarget().setMark(false);
		}
		tempPPInputs.clear();
		for (int i=0;i<tempPPOutputs.size();i++) {
			((PinPoint)tempPPOutputs.get(i)).getTarget().setMark(false);
		}
		tempPPOutputs.clear();
		// Outputs:
		for (int i=0;i<pPOutputs.size();i++) {
			targetPP = fp.getNearestInputPin((PinPoint)pPOutputs.get(i));
			pin = (PinPoint)pPOutputs.get(i);
			if (targetPP != null) {
				
				tempPPOutputs.add(pin);
				
				pin.setTarget(targetPP);
				targetPP.setMark(true);
								
			} else {
				pin.setTarget(null);
			}
		}
		// Inputs:
		for (int i=0;i<pPInputs.size();i++) {
			targetPP = fp.getNearestOutputPin((PinPoint)pPInputs.get(i));
			if (targetPP != null) {
				((PinPoint)pPInputs.get(i)).setTarget(targetPP);
				targetPP.setMark(true);
				tempPPInputs.add(pPInputs.get(i));
			} else {
				((PinPoint)pPInputs.get(i)).setTarget(null);
			}
		}
	}
	
	
	/**
	 * Selects a whole subtree with form as the root.
	 * @param form root formula
	 */
	private void recursiveSelect(Formula form) {
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
				recursiveSelect(form.getInput(i));
			}
		}
	}
	
	
	/**
	 * Updates all selected elements to a new paintStatus.
	 * @param status paintStatus from Formula
	 */
	private void updatePaintStatus(int status) {
		Formula form;
		for (int i=0;i<selectedComponents.size();i++) {
			form = (Formula)selectedComponents.get(i);
			form.setPaintStatus(status);
		}
	}
	
	/**
	 * Resets everything to it's init-state. 
	 */
	private void deselect() {
		dragInProgress = false;
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
	}
	
	/**
	 * Selects a formula element in the ElementPanel, creates a new instance of it
	 * and adds it to the FormulaPanel (invisible until mouse enters FormulaPanel).
	 * Also creates the PinPoints for this formula element.
	 * 
	 * @param targetComponent the target formula element
	 */
	private void selectNewElement(Formula targetFormula) {
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
		for (int i=0;i<inCount;i++) {
			pp = new PinPoint(newComponentInstance,xPos+(i+1)*width/(inCount+1),yPos+height,i);
			pp.setMouseTargetPoint(xPos-width/2+(i+1)*width*2/(inCount+1),yPos+height+MOUSE_POINT_DISTANCE);
			pPInputs.add(pp);
		}
		pp = new PinPoint(newComponentInstance,xPos+width/2,yPos);
		pp.setMouseTargetPoint(xPos+width/2,yPos-MOUSE_POINT_DISTANCE);
		pPOutputs.add(pp);
		aPanel.getFormulaPanel().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		aPanel.getFormulaPanel().validate();
	}
}
