/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.*;

/**
 * The FormulaPanel displays the formula-trees, created by the user.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class FormulaPanel extends Panel {

	/**
	 * Creates a new FormulaPanel.
	 */
	public FormulaPanel() {
		setLayout(new TreeLayout());
	}

}
