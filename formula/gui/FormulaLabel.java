/*
 * Created on 16.05.2004
 *
 */
package gui;

import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 16.05.2004
 *
 */
public class FormulaLabel extends Label {

	/**
	 * @param string
	 */
	public FormulaLabel(String string) {
		super(string);
		setBackground(SystemColor.text);
	}

	public Dimension getMinimumSize() {
		return new Dimension(150,48);
	}

	public Dimension getPreferredSize() {
		return new Dimension(250,48);
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(250,48);
	}

}
