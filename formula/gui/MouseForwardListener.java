/*
 * Created on 25.08.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;


/**
 * This listener will forward all events to the parent component.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class MouseForwardListener implements MouseListener {

	/**
	 * 
	 */
	public MouseForwardListener() {
		super();
	}
	/*
 	 * MouseEvent(Component source,int id,long when,int modifiers,int x,int y,int clickCount,boolean popupTrigger)
 	 */
	public void mouseClicked(MouseEvent mEvent) {
		System.out.println("mouseClicked: "+mEvent);
		mEvent.consume();
		Component comp = mEvent.getComponent();
		Component parent = comp.getParent();
		if (parent != null) {
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


	public void mouseEntered(MouseEvent mEvent) {

	}


	public void mouseExited(MouseEvent mEvent) {

	}


	public void mousePressed(MouseEvent mEvent) {

	}


	public void mouseReleased(MouseEvent mEvent) {

	}

}
