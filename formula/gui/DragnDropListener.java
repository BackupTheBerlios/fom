/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 05.05.2004
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	private static boolean dragInProgress = false;

	public void mouseClicked(MouseEvent me) {

	}

	public void mouseEntered(MouseEvent me) {
		
	}

	public void mouseExited(MouseEvent me) {
		
	}

	public void mousePressed(MouseEvent me) {
		dragInProgress=true;
		((Component)me.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void mouseReleased(MouseEvent me) {
		dragInProgress=false;
		((Component)me.getSource()).setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent me) {

	}

	public void mouseMoved(MouseEvent arg0) {

	}

}
