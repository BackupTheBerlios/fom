/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

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
		formulaName = "                     Constant";
		result = new Double (0);
		inputNumber = new TextField();
		inputNumber.addTextListener(this);
		inputNumber.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		add(inputNumber);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	/**
	 * After each keypress, result has to be updated, if textfield contains
	 * still a valid number.
	 * @param arg import Method needs argument, but isn't used.
	 */
	public void textValueChanged(TextEvent arg) {
		String newInput = inputNumber.getText();
		if (newInput.length() == 0) {
			newInput = "0";
		}
		if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
			result = new Double(newInput);
			repaint();
		}
	}

}