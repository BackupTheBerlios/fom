/* $Revision: 1.7 $
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
public class ElementPanelListener implements ItemListener, ActionListener {

	private ElementPanel ePanel;
	private AppletPanel aPanel;

	/**
	 * Creates the listener. This constructor is required, otherwise it won't work.
	 * @param root the root-panel (ElementPanel) of all components using this listener
	 */	
	public ElementPanelListener(AppletPanel ap,ElementPanel root) {
		this.ePanel = root;
		this.aPanel = ap;
	}


	public void itemStateChanged(ItemEvent ievent) {
		String category=(String)ievent.getItem();
		Formula[] elements=ePanel.getCategories().getCategoryElements(category);
		ePanel.updateElementList(elements);
	}


	public void actionPerformed(ActionEvent aevent) {
		if (aevent.getActionCommand().equals(Messages.getString("ElementPanel.BtnAddFormula"))) {
			// TODO use the formula (CustomFormula) from FormulaPanel!
			ePanel.getCategories().addCategoryElement(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-1)),new Add());
			ePanel.refreshElementList();
		} else if (aevent.getActionCommand().equals(Messages.getString("ElementPanel.BtnClearFormulas"))) {
			aPanel.getFormulaPanel().deleteAll();
			aPanel.getControlPanel().updateTfResult("");
			aPanel.getFormulaPanel().validate();
			aPanel.getFormulaPanel().doLayout();
			aPanel.getFormulaPanel().repaint();
		}
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
	}
	
}