/*
 * Created on 20.08.2004
 *
 */
package formula;

import java.util.Vector;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class FormulaList extends Vector {
	
	
	/**
	 * @return Validates, that only one tree exists and isn't missing some inputs.
	 */
	public final boolean isCompleteGlobalTree() {
		boolean complete = false;
		if (size() == 1) {
			complete = getFormula(0).isCompleteSubTree();
		}
		return complete;
	}

	
	/**
	 * Returns an array of formula objects that don't have anything connected
	 * to their output-pins.
	 * @return array of formulas
	 */
	public final Formula[] getTreeArray() {
		Formula[] treeListArray = new Formula[size()];
		for (int i=0; i < treeListArray.length; i++) {
			treeListArray[i] = (Formula)get(i);
		}
		return treeListArray;
	}
	
	
	public final Formula getFormula(int i) {
		return (Formula)get(i);
	}
	
	
	/**
	 * Recurively deletes previously calculated results from all formulas in one tree.
	 * 
	 * @param form current formula to work with
	 */
	private void clearResultsRec(Formula form) {
		form.clearResult();
		form.repaint();
		for (int i=0;i<form.getInputCount();i++) {
			if (form.getInput(i) != null) {
				clearResultsRec(form.getInput(i));
			}
		}
	}
	

	public void clearResults() {
		for (int i=0;i<size();i++) {
			clearResultsRec(getFormula(i));
		}
	}
	
}
