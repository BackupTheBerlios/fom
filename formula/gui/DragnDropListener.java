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

	private static boolean dragInProgress	= false;
	private static AppletPanel aPanel				= null;
	private static Point dragStartPoint			= null;
	private static Point dragRelativePoint		= null;
	private static Formula dragComponent	= null;
	
	public DragnDropListener(AppletPanel ap) {
		aPanel = ap;
	}

	public void mouseClicked(MouseEvent me) {

	}

	public void mouseEntered(MouseEvent me) {
		
	}

	public void mouseExited(MouseEvent me) {
		
	}

	public void mousePressed(MouseEvent me) {
		if (me.getComponent().getComponentAt(me.getPoint()) instanceof Formula) {
			dragInProgress = true;
			dragComponent = (Formula)me.getComponent().getComponentAt(me.getPoint());
			dragStartPoint = dragComponent.getLocation();
			dragRelativePoint = new Point((int)(dragStartPoint.getX()-me.getPoint().getX()),(int)(dragStartPoint.getY()-me.getPoint().getY()));
			aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseReleased(MouseEvent me) {
		dragInProgress=false;
		aPanel.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent me) {
		if ((dragInProgress) && (dragComponent != null)) {
			dragComponent.setLocation((int)(dragRelativePoint.getX()+me.getPoint().getX()),(int)(dragRelativePoint.getY()+me.getPoint().getY()));
		}
	}
	
	public void mouseMoved(MouseEvent me) {
		if (dragInProgress) {
			
		}
	}

}
