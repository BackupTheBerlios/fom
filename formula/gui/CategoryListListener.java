/*
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.event.*;
import formula.*;
import utils.*;

/**
 * Listener for the Choice and Button(s) on the left side panel (ElementPanel).
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class CategoryListListener implements ItemListener, ActionListener {

	private ElementPanel ePanel;

	/**
	 * Creates the listener. This constructor is required, otherwise it won't work.
	 * @param root the root-panel (ElementPanel) of all components using this listener
	 */	
	public CategoryListListener(ElementPanel root) {
		ePanel = root;
	}


	public void itemStateChanged(ItemEvent ievent) {
		String category=(String)ievent.getItem();
		Formula[] elements=Categories.getCategoryElements(category);
		ePanel.updateElementList(elements);
	}


	public void actionPerformed(ActionEvent aevent) {
		if (aevent.getActionCommand().equals(Messages.getString("ElementPanel.BtnAddFormula"))) {
			// TODO use the formula from FormulaPanel!
			Categories.addCategoryElement(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-1)),new Add());
			ePanel.refreshElementList();
		}
	}
	
}
