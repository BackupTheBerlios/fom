/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for constant numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantNumber extends ConstVarFormula implements TextListener {

	protected TextField inputNumber;

	/**
	 * Creates a constant number.
	 */
	public ConstantNumber() {
		this(false);
	}

	/**
	 * Creates a constant number.
	 * @param elementChooser Disables inputNumber for input.
	 */
	public ConstantNumber(boolean elementChooser) {
		super();
		formulaName = "                     Constant";
		result = new Double(0);
		inputNumber = new TextField();
		if (elementChooser) {
			inputNumber.enable(false);
		} else {
			inputNumber.addTextListener(this);
		}
		inputNumber.setBackground(SystemColor.text);
		inputNumber.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		add(inputNumber);
	}

// TODO Ist der non-Javadoc Kommentar hier gewollt?
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	/**
	 * After each keypress the result has to be updated if the textfield contains
	 * still a valid number.
	 * @param arg import Method needs argument, but isn't used.
	 */
	public void textValueChanged(TextEvent arg) {
		String newInput = inputNumber.getText();
		if (newInput.length() == 0) {
			newInput = "0";
		}
		if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
			result = new Double(newInput.replace(',','.'));
			inputNumber.setBackground(SystemColor.text);
			repaint();
		} else {
			inputNumber.setBackground(Color.RED);
		}
	}

	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputNumber.setVisible(vis);
	}

}