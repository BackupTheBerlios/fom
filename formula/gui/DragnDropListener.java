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

	private static boolean dragInProgress		= false;	// true if something is dragged
	private static boolean insertInProgress		= false;	// true if element is selected from the ElementPanel
	private static AppletPanel aPanel			= null;		// root panel of everything
	private static Point selectedStartPoint		= null;		// the starting point of DnD actions
	private static Point selectedRelativePoint	= null;		// the relative point within the dragged element
	private static Formula selectedComponent	= null;		// component that is dragged or selected
	private static Formula newComponentInstance = null;		// new instance of a selected component
	private static PinPoint[] pPInputs			= null;		// list of input PinPoints of the selected element(s)
	private static PinPoint[] pPOutputs			= null;		// list of output PinPoints of the selected element(s)
	

	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
	}

	public void mouseClicked(MouseEvent me) {
		// if event happened in the ElementPanel:
		// select that component and create a pinpoint 
		if (me.getComponent().getParent().getParent() instanceof ElementPanel) {
			Component targetComponent = me.getComponent().getComponentAt(me.getPoint());
			if (targetComponent != null) { // if clicked on a Formula-object
				dragInProgress = false;
				insertInProgress = true;
				selectedComponent = (Formula)targetComponent;
				selectedComponent.setPaintStatus(Formula.PAINTSTATUS_SELECTED);
				selectedComponent.repaint();
				//create new instance:
				try {
					newComponentInstance = (Formula)selectedComponent.getClass().newInstance();
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
				pPInputs = new PinPoint[inCount];
				pPOutputs = new PinPoint[1];
				for (int i=0;i<inCount;i++) {
					pPInputs[i] = new PinPoint(newComponentInstance,xPos+(i+1)*width/(inCount+1),yPos+height,i);
					pPInputs[i].setMouseTargetPoint(xPos+(i+1)*2*width/(inCount+1)-width,yPos+height+MOUSE_POINT_DISTANCE);
				}
				pPOutputs[0] = new PinPoint(newComponentInstance,xPos+width/2,yPos);
				pPOutputs[0].setMouseTargetPoint(xPos+width/2,yPos-MOUSE_POINT_DISTANCE);
				aPanel.getFormulaPanel().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				aPanel.getFormulaPanel().validate();
			}
		// if event happened in the FormulaPanel:
		} else if (me.getComponent() instanceof FormulaPanel){
			// if a component is selected from the ElementPanel (newComponentInstance != null),
			// a mouseClicked event within the FormulaPanel will place it there.
			if (insertInProgress) {
				newComponentInstance.setLocation((int)me.getPoint().getX()-newComponentInstance.getWidth()/2,(int)me.getPoint().getY()-newComponentInstance.getHeight()/2);
				selectedComponent.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
				selectedComponent.repaint();
				newComponentInstance.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
				newComponentInstance.repaint();
				selectedComponent = null;
				newComponentInstance = null;										
				aPanel.getFormulaPanel().setCursor(Cursor.getDefaultCursor());
				aPanel.getFormulaPanel().doLayout();
				insertInProgress = false;
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
				// first, deselect all selected components:
				if (selectedComponent != null) {
					selectedComponent.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
					selectedComponent.repaint();
					pPInputs = null;
					pPOutputs = null;
					if (newComponentInstance != null) {
						aPanel.getFormulaPanel().remove(newComponentInstance);
						newComponentInstance = null;
					}
				}
				// initiate drag&drop:
				dragInProgress = true;
				selectedComponent = (Formula)me.getComponent().getComponentAt(me.getPoint());
				selectedComponent.setPaintStatus(Formula.PAINTSTATUS_MOVING);
				selectedStartPoint = selectedComponent.getLocation();
				selectedRelativePoint = new Point((int)(selectedStartPoint.getX()-me.getPoint().getX()),(int)(selectedStartPoint.getY()-me.getPoint().getY()));
				selectedComponent.repaint();
				aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		if (dragInProgress) {
			dragInProgress = false;
			aPanel.setCursor(Cursor.getDefaultCursor());
			selectedComponent.setPaintStatus(Formula.PAINTSTATUS_STANDARD);
			selectedComponent.repaint();
			selectedComponent = null;
		}
	}

	public void mouseDragged(MouseEvent me) {
		if ((dragInProgress) && (selectedComponent != null)) {
			// dragging only works in the FormulaPanel (atm)
			if (me.getComponent() instanceof FormulaPanel) {
				//selectedComponent.setLocation((int)(selectedRelativePoint.getX()+me.getPoint().getX()),(int)(selectedRelativePoint.getY()+me.getPoint().getY()));
				Point p = me.getPoint();
				moveTo(selectedComponent,new Point(selectedRelativePoint.x+p.x,selectedRelativePoint.y+p.y));
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
				Point point = me.getPoint();
				point.translate(-newComponentInstance.getWidth()/2,-newComponentInstance.getHeight()/2);
				moveTo(newComponentInstance,point);
				newComponentInstance.repaint();
			}
		}
	}
	
	
	/**
	 * Moves a formula, all its sub-formulas and all pPInputs and pPOutputs (relative) to a given point.
	 * This point will be the top left corner of the formula. 
	 * @param formula the formula to move
	 * @param point target point to move to
	 */
	private void moveTo(Formula formula,Point point) {
		Point oldLocation = formula.getLocation();
		int xOffset = point.x - oldLocation.x;
		int yOffset = point.y - oldLocation.y;
/*		for (int i=0;i<pPInputs.length;i++) {
			pPInputs[i].translate(xOffset,yOffset); 
		}
		for (int i=0;i<pPOutputs.length;i++) {
			pPOutputs[i].translate(xOffset,yOffset);
		}*/
		// NOTE: this is not really fast! maybe a recursive method is better
		Stack formStack = new Stack();
		formStack.push(formula);
		Formula form;
		while (!formStack.isEmpty()) {
			form = (Formula)formStack.pop();
			for (int i=0;i<form.getInputCount();i++) {
				if (form.getInput(i) != null) {
					formStack.push(form.getInput(i));
				}
			}
			oldLocation = form.getLocation();
			form.setLocation(oldLocation.x+xOffset,oldLocation.y+yOffset);
		}
	}
	
}
