/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.*;
import java.util.*;
import formula.*;

/**
 * The FormulaPanel displays the formula-trees, created by the user.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class FormulaPanel extends Panel {


	// These lists store all input/output pins.
	private LinkedList inputPointList = new LinkedList();
	private LinkedList outputPointList = new LinkedList();
	

	/**
	 * Creates a new FormulaPanel.
	 */
	public FormulaPanel() {
		setLayout(new TreeLayout());
		setBackground(SystemColor.text);
	}

}
