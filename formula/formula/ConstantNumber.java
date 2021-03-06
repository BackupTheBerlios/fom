/* $Id: ConstantNumber.java,v 1.31 2004/09/06 13:08:01 br3001 Exp $
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
 * @version $Revision: 1.31 $
 */
public class ConstantNumber extends ConstVarFormula implements TextListener {

	private TextField inputNumber;
	private MouseForwardListener mfListener;

	/**
	 * Creates a constant number.
	 */
	public ConstantNumber() {
		this(true);
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
		mfListener = new MouseForwardListener();
		inputNumber.addMouseListener(mfListener);
		inputNumber.addMouseMotionListener(mfListener);
		if (elementChooser) {
			inputNumber.addTextListener(this);
		} else {
			setEnabled(false);
		}
		add(inputNumber);
		inputNumber.setBackground(Color.white);
		inputNumber.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);
		inputNumber.setFont(DEFAULT_FONT);
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

		try {
			result = new Double(newInput.replace(',','.'));
			inputNumber.setBackground(Color.white);
		} catch (NumberFormatException nfe) {
			inputNumber.setBackground(Color.red);
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
		inputNumber.setEditable(enabled);		// if inputNumber would be disabled, the Mouse(Motion)Listeners would not work!
	}


	public String getInputVarName() {
		return "";
	}


	public boolean hasBooleanResult() {
		return false;
	}


	public boolean hasDoubleResult() {
		return true;
	}
	
	
	public Object clone() {
		ConstantNumber clonedCN = (ConstantNumber)super.clone();
		clonedCN.inputNumber.setText(inputNumber.getText());
		clonedCN.result = new Double(((Double)result).doubleValue());
		return clonedCN;
	}

}