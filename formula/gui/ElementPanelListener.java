/* $Id: ElementPanelListener.java,v 1.22 2004/10/20 18:43:58 shadowice Exp $
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;
import java.awt.event.*;

import formula.*;
import utils.*;

/**
 * Listener for the choice component and button(s) on the left side panel (ElementPanel).
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
		aPanel.getSelection().deselect();
	}


	public void actionPerformed(ActionEvent aEvent) {
		if (aEvent.getActionCommand().equals(Messages.getString("ElementPanel.BtnAddFormula"))) {
			if (aPanel.getTreeList().isCompleteGlobalTree()) {
				try {
					Formula clonedTree = (Formula)((Formula)aPanel.getTreeList().elementAt(0)).clone();
					VariableList clonedVarList = new VariableList();
					createVariableList(clonedVarList,clonedTree);
					clonedVarList.sort();
					CustomFormula custForm = new CustomFormula(clonedTree,clonedVarList);
					custForm.setFormulaName(cfDialog.showDialog(custForm,clonedVarList));
					ePanel.getCategories().addCategoryElement(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-1)),custForm);	// All
					ePanel.getCategories().addCategoryElement(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-2)),custForm);	// Custom Elements
					ePanel.refreshElementList();
					ePanel.getCategoryList().select(ePanel.getCategoryList().getItemCount()-2);
					Formula[] elements=ePanel.getCategories().getCategoryElements(Messages.getString("Elements.Category_"+(Integer.parseInt(Messages.getString("Elements.Categories"))-2)));
					ePanel.updateElementList(elements);
					aPanel.getSelection().deselect();
				} catch (FormulaException fe) {
					fe.printStackTrace(System.err);
				}
			} else {
				// TODO: Fehlerdialog
			}
		} else if (aEvent.getActionCommand().equals(Messages.getString("ElementPanel.BtnClearFormulas"))) {
			aPanel.getFormulaPanel().deleteAll();
			aPanel.getControlPanel().updateTfResult("");
			aPanel.getFormulaPanel().invalidate();
			aPanel.getFormulaPanel().getParent().validate();
			aPanel.getFormulaPanel().repaint();
			aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
			System.gc();	// slows everything down a bit
		}
	}


	/**
	 * Recursive function to add all variables in a formula tree into a VariableList.
	 * 
	 * @param varList VariableList to write to
	 * @param form current formula element
	 */
	private void createVariableList(VariableList varList, Formula form) {
		if (form instanceof VariableBoolean) {
			varList.addVarList((VariableBoolean)form);
		} else if (form instanceof VariableNumber) {
			varList.addVarList((VariableNumber)form);
		}
		for (int i=0;i<form.getInputCount();i++) {
			createVariableList(varList,form.getInput(i));
		}
	}

}