/*
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.event.*;
import java.awt.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 26.04.2004
 *
 */
public class CategoryListListener implements ItemListener {

	public void itemStateChanged(ItemEvent ievent) {
		//TODO get new list
		String category=(String)ievent.getItem();
		Formula[] elements=Categories.getCategoryElements(category);
		((ElementPanel)((Choice)ievent.getSource()).getParent()).updateElementList(elements);
		((ElementPanel)((Choice)ievent.getSource()).getParent()).repaint();
	}
}
