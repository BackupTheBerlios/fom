/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class DragnDropListener implements MouseListener, MouseMotionListener {

	private static boolean dragInProgress	= false;
	private static AppletPanel aPanel				= null;
	
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
		if (me.getComponent() instanceof Formula) {
			dragInProgress = true;
			aPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseReleased(MouseEvent me) {
		dragInProgress=false;
		aPanel.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseDragged(MouseEvent me) {
		if (dragInProgress) {
			Component srcComponent = aPanel.getComponentAt(me.getPoint());
			System.out.println(srcComponent);
			System.out.println(me.getPoint());
			if (srcComponent != null) {
				Graphics srcGraphics = srcComponent.getGraphics();
				srcGraphics.drawOval(me.getX(),me.getY(),10,10);
			}
			
		}
	}
	
	public void mouseMoved(MouseEvent me) {
		if (dragInProgress) {

		}
	}

}
