/*
 * Created on 16.05.2004
 *
 */
package gui;

import java.awt.*;
import utils.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 16.05.2004
 *
 */
public class FormulaLabel extends Label {

	private ControlPanel cPanel = null;

	/**
	 * @param string
	 */
	public FormulaLabel(ControlPanel cp,String string) {
		super(string);
		setBackground(SystemColor.text);
		this.cPanel = cp;
	}

	public void updateControlPanelText() {
		Formula[] treeArray = Formula.getTreeList();
		if (treeArray != null) {
			if (treeArray.length > 1) {
				cPanel.updateLblFormula(Messages.getString("Error.TooManyTrees"));			
			} else if (treeArray.length == 1) {
				if (treeArray[0].isCompleteSubTree()) {
					cPanel.updateLblFormula(treeArray[0].toString());	
				} else {
					cPanel.updateLblFormula(Messages.getString("Error.IncompleteFormula"));
				}
			} else {
				cPanel.updateLblFormula(Messages.getString("Error.NoTrees"));
			}
		} else {
			cPanel.updateLblFormula(Messages.getString("Error.NoTrees"));
		}
	}

	// NOTE: hardcoded size:
	public Dimension getMinimumSize() {
		return new Dimension(250,48);
	}

	public Dimension getPreferredSize() {
		return new Dimension(350,48);
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(350,48);
	}
	


}
