/* $Id: FormulaTextField.java,v 1.5 2004/08/25 18:21:54 shadowice Exp $
 * Created on 16.05.2004
 *
 */
package gui;

import java.awt.*;
import utils.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.5 $
 */
public class FormulaTextField extends TextField {

	private ControlPanel cPanel = null;

	/**
	 * @param string
	 */
	public FormulaTextField(ControlPanel cp,String string) {
		super(string);
		setEditable(false);
		setBackground(SystemColor.text);
		this.cPanel = cp;
	}

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
