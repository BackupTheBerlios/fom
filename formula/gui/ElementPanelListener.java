/* $Id: ElementPanelListener.java,v 1.10 2004/08/30 19:30:52 shadowice Exp $
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;
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
	
	private CustomFormulaDialog cfDialog = new CustomFormulaDialog(new Frame());

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
			// TODO CustomFormula statt Add verwenden.
			if (aPanel.getTreeList().isCompleteGlobalTree()) {
				Add add=new Add();
				add.setName(cfDialog.showDialog(add));	// TODO setFormulaName wenn mit CustomFormula
				ePanel.getCategories().addCategoryElement(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-1)),add);
				ePanel.refreshElementList();
			}
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