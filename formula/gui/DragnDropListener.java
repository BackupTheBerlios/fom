/* $Revision: 1.28 $
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;

import formula.*;

/**
 * Listener that handles drag&drop as well as point&click
 * for (re)placing formula-elements or whole trees and connecting them.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.28 $
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	private AppletPanel aPanel		= null;				// root panel of everything
	
	private Selection selection		= null;
	

	/**
	 * Creates a new Drag&Drop-Listener.
	 * @param ap the AppletPanel where this listener is used
	 */
	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
		selection = new Selection(ap);
	}

	public Selection getSelection() {
		return selection;
	}

	public void mouseClicked(MouseEvent me) {
		// if event happened in the ElementPanel:
		if (me.getComponent().getParent().getParent() instanceof ElementPanel) {
			Component targetComponent = me.getComponent().getComponentAt(me.getPoint());
			if (targetComponent != null) { // if clicked on a Formula-object
				selection.selectNewElement((Formula)targetComponent);
			}
		// if event happened in the FormulaPanel:
		} else if (me.getComponent() instanceof FormulaPanel){
			// if a component is selected from the ElementPanel (newComponentInstance != null),
			// a mouseClicked event within the FormulaPanel will place it there.
			if (selection.isInsertInProgress()) {
				selection.placeElement(me.getPoint());
			} else if (!(me.getComponent().getComponentAt(me.getPoint()) instanceof Formula)) {
				selection.deselect();
			}
			
		}
	}

	public void mouseEntered(MouseEvent me) {
		if (me.getComponent() instanceof FormulaPanel) {
			if (selection.isInsertInProgress()) {
				selection.setElementVisible(true);
			}
		}
		
	}

	public void mouseExited(MouseEvent me) {
		if (me.getComponent() instanceof FormulaPanel) {
			if (selection.isInsertInProgress()) {
				Component comp = me.getComponent();
				if (comp.getComponentAt(me.getPoint()) == null) {
					selection.setElementVisible(false);
				}
			}
		}
	}

	public void mousePressed(MouseEvent me) {
		// mouse pressed over a Formula in the FormulaPanel:
		if (me.getComponent() instanceof FormulaPanel) {
			if (!selection.isInsertInProgress() && (me.getComponent().getComponentAt(me.getPoint()) instanceof Formula)) {
				selection.deselect();
				// initiate drag&drop:
				selection.recursiveSelect((Formula)me.getComponent().getComponentAt(me.getPoint()),me.getPoint());
				selection.updatePaintStatus(Formula.PAINTSTATUS_MOVING);
			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		if (selection.isDragInProgress()) {
			selection.dragEnd();
		}
		aPanel.getFormulaPanel().repaint();
		aPanel.getFormulaPanel().doLayout();
		aPanel.getFormulaPanel().checkBounds();
	}

	public void mouseDragged(MouseEvent me) {
		if (selection.isDragInProgress()) {
			// dragging only works in the FormulaPanel (atm)
			if (me.getComponent() instanceof FormulaPanel) {
				// move to new location:
				Point p = me.getPoint();
				selection.move(me.getPoint());
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
			if (selection.isInsertInProgress()) {
				// move to new location:
				selection.move(me.getPoint());
			}
		}
	}
	
	
}
