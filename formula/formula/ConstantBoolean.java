/*
 * Created on 27.06.2004
 */
package formula;

import java.awt.*;
import java.awt.event.*;

import gui.*;

/**
 * Class for constant booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantBoolean extends ConstVarFormula implements ActionListener {

	private Button inputBoolean;


	/**
	 * Creates a constant boolean.
	 */
	public ConstantBoolean() {
		this(true);
	}


	/**
	 * Creates a constant boolean.
	 * @param elementChooser Disables inputBoolean for input.
	 */
	public ConstantBoolean(boolean elementChooser) {
		super();
		formulaName = "                    Constant";
		result = new Boolean(false);
		inputBoolean = new Button("false");
		inputBoolean.setFont(DEFAULT_FONT);
		inputBoolean.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);
		if (elementChooser) {
			inputBoolean.addActionListener(this);
		} else {
			inputBoolean.setEnabled(false);
		}
		add(inputBoolean);		
	}


	/**
	 * Toggles result between true and false.
	 * @param arg import Method needs argument, but isn't used.
	 */
	public void actionPerformed(ActionEvent arg) {
		if (inputBoolean.getLabel() == "false") {
			inputBoolean.setLabel("true");
			result = new Boolean (true);
		} else {
			inputBoolean.setLabel("false");
			result = new Boolean (false);
		}
		
		if (getParent() instanceof FormulaPanel) {
			((AppletPanel)getParent().getParent().getParent()).getControlPanel().getFormulaTextField().updateControlPanelText();
		}
		
		repaint();
	}


	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputBoolean.setVisible(vis);
	}


	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		inputBoolean.setEnabled(enabled);
	}


	public String getInputVarName() {
		return null;
	}


	public boolean hasBooleanResult() {
		return true;
	}


	public boolean hasDoubleResult() {
		return false;
	}


	public Object clone() {
		ConstantBoolean clonedCB = (ConstantBoolean)super.clone();
		clonedCB.inputBoolean.setLabel(inputBoolean.getLabel());
		clonedCB.result = new Boolean(((Boolean)result).booleanValue());
		return clonedCB;
	}

}