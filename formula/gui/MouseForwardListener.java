/*
 * Created on 25.08.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;


/**
 * This listener will forward all events to the parent's parent component.
 * It's only used for textfields or buttons in Formula elements, because otherwise
 * events that happen over these components won't reach the Formula-/ElementPanel.
 * 
 * Note that this listener will only be functional if the parent is disabled!
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class MouseForwardListener implements MouseListener, MouseMotionListener {

	/**
	 * Constructor
	 */
	public MouseForwardListener() {
		super();
	}


	public void mouseClicked(MouseEvent mEvent) {
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
			if (!parent.isEnabled()) {
				MouseEvent newEvent = new MouseEvent(parent.getParent(),
											mEvent.getID(),
											mEvent.getWhen(),
											mEvent.getModifiers(),
											parent.getX()+comp.getX() + mEvent.getPoint().x,
											parent.getY()+comp.getY() + mEvent.getPoint().y,
											mEvent.getClickCount(),
											mEvent.isPopupTrigger());
				parent.getParent().dispatchEvent(newEvent);
			}
		}
	}


	public void mouseEntered(MouseEvent mEvent) {
	}


	public void mouseExited(MouseEvent mEvent) {
	}


	public void mousePressed(MouseEvent mEvent) {
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
			if (!parent.isEnabled()) {
				MouseEvent newEvent = new MouseEvent(parent.getParent(),
											mEvent.getID(),
											mEvent.getWhen(),
											mEvent.getModifiers(),
											parent.getX()+comp.getX() + mEvent.getPoint().x,
											parent.getY()+comp.getY() + mEvent.getPoint().y,
											mEvent.getClickCount(),
											mEvent.isPopupTrigger());
				parent.getParent().dispatchEvent(newEvent);
			}
		}
	}


	public void mouseReleased(MouseEvent mEvent) {
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
			if (!parent.isEnabled()) { 
				MouseEvent newEvent = new MouseEvent(parent.getParent(),
											mEvent.getID(),
											mEvent.getWhen(),
											mEvent.getModifiers(),
											parent.getX()+comp.getX() + mEvent.getPoint().x,
											parent.getY()+comp.getY() + mEvent.getPoint().y,
											mEvent.getClickCount(),
											mEvent.isPopupTrigger());
				parent.getParent().dispatchEvent(newEvent);
			}
		}
	}
	

	public void mouseDragged(MouseEvent mEvent) {
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
			if (!parent.isEnabled()) {
				MouseEvent newEvent = new MouseEvent(parent.getParent(),
											mEvent.getID(),
											mEvent.getWhen(),
											mEvent.getModifiers(),
											parent.getX()+comp.getX() + mEvent.getPoint().x,
											parent.getY()+comp.getY() + mEvent.getPoint().y,
											mEvent.getClickCount(),
											mEvent.isPopupTrigger());
				parent.getParent().dispatchEvent(newEvent);
			}
		}
	}


	public void mouseMoved(MouseEvent mEvent) {
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
			if (!parent.isEnabled()) {
				MouseEvent newEvent = new MouseEvent(parent.getParent(),
											mEvent.getID(),
											mEvent.getWhen(),
											mEvent.getModifiers(),
											parent.getX()+comp.getX() + mEvent.getPoint().x,
											parent.getY()+comp.getY() + mEvent.getPoint().y,
											mEvent.getClickCount(),
											mEvent.isPopupTrigger());
				parent.getParent().dispatchEvent(newEvent);
			}
		}
	}

}
