/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import formula.*;

/**
 * Listener that handles drag&drop as well as point&click for (re)placing formula-elements or whole trees.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	private static boolean dragInProgress		= false;
	private static AppletPanel aPanel					= null;
	private static Point selectedStartPoint			= null;
	private static Point selectedRelativePoint		= null;
	private static Formula selectedComponent	= null;
	
	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
	}

	public void mouseClicked(MouseEvent me) {
		if (me.getComponent() instanceof ElementPanel) {
			Component targetComponent = me.getComponent().getComponentAt(me.getPoint());
			if (targetComponent != null) {
				if (targetComponent instanceof Formula) {

				}
			}
		} else if (me.getComponent() instanceof FormulaPanel){
			if (selectedComponent != null) {
				((FormulaPanel)me.getComponent()).add(selectedComponent);						
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
		dragInProgress=false;
		aPanel.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent me) {
		if ((dragInProgress) && (selectedComponent != null)) {
			if (me.getComponent() instanceof FormulaPanel) {
				selectedComponent.setLocation((int)(selectedRelativePoint.getX()+me.getPoint().getX()),(int)(selectedRelativePoint.getY()+me.getPoint().getY()));
			}
		}
	}
	
	public void mouseMoved(MouseEvent me) {
		if (dragInProgress) {
			
		}
	}

}
