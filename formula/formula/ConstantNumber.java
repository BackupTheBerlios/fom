/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.*;
import java.awt.event.*;
import gui.*;

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
		formulaName = "                    Constant";
		result = new Double(0);
		inputNumber = new TextField();
		inputNumber.setFont(DEFAULT_FONT);
		inputNumber.setBackground(SystemColor.text);
		inputNumber.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);
		inputNumber.addMouseListener(new MouseForwardListener());
		if (elementChooser) {
			inputNumber.setEnabled(false);
		} else {
			inputNumber.addTextListener(this);
		}
		add(inputNumber);
	}

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
// TODO Auch noch was auskommentiertes.		
		/*if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
			result = new Double(newInput.replace(',','.'));
			inputNumber.setBackground(SystemColor.text);
			repaint();
		} else {
			inputNumber.setBackground(Color.RED);
		}*/
			
		try {
			result = new Double(newInput.replace(',','.'));
			inputNumber.setBackground(SystemColor.text);
		} catch (NumberFormatException nfe) {
			inputNumber.setBackground(Color.RED);
		} finally {
			repaint();
		}
		
		if (getParent() instanceof FormulaPanel) {
			((AppletPanel)getParent().getParent().getParent()).getControlPanel().getFormulaTextField().updateControlPanelText();
		}
		
	}

	
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputNumber.setVisible(vis);
	}
	
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		inputNumber.setEnabled(enabled);
	}
	
	public String getInputVarName() {
		return "";
	}


}