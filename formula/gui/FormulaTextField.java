/* $Id: FormulaTextField.java,v 1.9 2004/10/19 21:26:05 shadowice Exp $
 * Created on 16.05.2004
 *
 */
package gui;

import java.awt.*;
import utils.*;
import formula.*;

/**
 * TextField to display the string of a formula tree.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.9 $
 */
public class FormulaTextField extends TextField {

	private ControlPanel cPanel = null;


	/**
	 * Creates a new FormulaTextField
	 * @param cp ControlPanel this TextField is in
	 * @param string text
	 */
	public FormulaTextField(ControlPanel cp,String string) {
		super(string);
		setEditable(false);
		this.cPanel = cp;
	}


	/**
	 * Updates the text in this TextField to the string representing the formula tree or an error message. 
	 */
	public void updateControlPanelText() {
		Formula[] treeArray = cPanel.getAppletPanel().getTreeList().getTreeArray();
		if (treeArray != null) {
			if (treeArray.length > 1) {
				setText(Messages.getString("Error.TooManyTrees"));			
			} else if (treeArray.length == 1) {
				if (treeArray[0].isCompleteSubTree()) {
					setText(treeArray[0].toString());	
				} else {
					setText(Messages.getString("Error.IncompleteFormula"));
				}
			} else {
				setText(Messages.getString("Error.NoTrees"));
			}
		} else {
			setText(Messages.getString("Error.NoTrees"));
		}
	}

}
