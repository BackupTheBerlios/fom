/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import formula.*;

/**
 * Listener that handles drag&drop as well as point&click
 * for (re)placing formula-elements or whole trees.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	private static boolean dragInProgress		= false;	// true if something is dragged
	private static AppletPanel aPanel			= null;		// root panel of everything
	private static Point selectedStartPoint		= null;		// the starting point of DnD actions
	private static Point selectedRelativePoint	= null;		// the relative point within the dragged element
	private static Formula selectedComponent	= null;		// component that is dragged or selected
	private static PinPoint[] pPInputs			= null;		// list of input PinPoints of the selected element(s)
	private static PinPoint[] pPOutputs			= null;		// list of output PinPoints of the selected element(s)
	
	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
	}

	public void mouseClicked(MouseEvent me) {
		// if event happened in the ElementPanel:
		if (me.getComponent().getParent().getParent() instanceof ElementPanel) {
			Component targetComponent = me.getComponent().getComponentAt(me.getPoint());
			if (targetComponent != null) { // if clicked on a Formula-object
				selectedComponent = (Formula)targetComponent;
				int inCount = selectedComponent.getInputCount();
				pPInputs = new PinPoint[inCount];
				pPOutputs = new PinPoint[1];
				for (int i=0;i<inCount;i++) {
					pPInputs[i] = new PinPoint();
				}
			}
		// if event happened in the FormulaPanel:
		} else if (me.getComponent() instanceof FormulaPanel){
			// if a component is selected from the ElementPanel,
			// a mouseClicked event within the FormulaPanel will place it there.
			if (selectedComponent != null) {
				try {
					// TODO: rewrite to FormulaPanel.addFormula(Formula,Coordinates);
					Formula newFormula = (Formula)(selectedComponent.getClass().newInstance());
					((FormulaPanel)me.getComponent()).add(newFormula);
					newFormula.setBounds((int)me.getPoint().getX()-newFormula.getWidth()/2,(int)me.getPoint().getY()-newFormula.getHeight()/2,newFormula.getWidth(),newFormula.getHeight());
					aPanel.getFormulaPanel().doLayout();
				} catch (IllegalAccessException iae) {
					iae.printStackTrace();
				} catch (InstantiationException ie) {
					ie.printStackTrace();
				} finally {
					selectedComponent = null;										
				}
			}
		}
	}

	public void mouseEntered(MouseEvent me) {
		
	}

	public void mouseExited(MouseEvent me) {
		
	}

	public void mousePressed(MouseEvent me) {
		if (me.getComponent().getComponentAt(me.getPoint()) instanceof Formula) {
			dragInProgress = true;
			selectedComponent = (Formula)me.getComponent().getComponentAt(me.getPoint());
			selectedStartPoint = selectedComponent.getLocation();
			selectedRelativePoint = new Point((int)(selectedStartPoint.getX()-me.getPoint().getX()),(int)(selectedStartPoint.getY()-me.getPoint().getY()));
			aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseReleased(MouseEvent me) {
		if (dragInProgress) {
			dragInProgress = false;
			aPanel.setCursor(Cursor.getDefaultCursor());
			selectedComponent = null;
		}
	}

	public void mouseDragged(MouseEvent me) {
		if ((dragInProgress) && (selectedComponent != null)) {
			// dragging only works in the FormulaPanel (atm)
			if (me.getComponent() instanceof FormulaPanel) {
				selectedComponent.setLocation((int)(selectedRelativePoint.getX()+me.getPoint().getX()),(int)(selectedRelativePoint.getY()+me.getPoint().getY()));
			}
		}
	}
	
	public void mouseMoved(MouseEvent me) {

	}

}
