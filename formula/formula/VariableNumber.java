/*
 * Created on 27.06.2004
 */
package formula;

import java.awt.*;
import java.awt.event.*;
import gui.*;

/**
 * Class for variable numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableNumber extends ConstVarFormula implements TextListener {

	private TextField inputVarName;
	private String oldName;

	private AppletPanel aPanel;


	/**
	 * Creates a variable number.
	 */
	public VariableNumber() {
		this(false);
	}


	/**
	 * Creates a variable boolean.
	 * @param enabled Enables inputVarName for input.
	 */
	public VariableNumber(boolean elementChooser) {
		super();
		formulaName = "                    Number";
		result = new Double (0);
		inputVarName = new TextField();
		inputVarName.setFont(DEFAULT_FONT);
		inputVarName.setBackground(SystemColor.text);
		inputVarName.setText("number1");
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);
		MouseForwardListener mfl = new MouseForwardListener();
		inputVarName.addMouseListener(mfl);
		inputVarName.addMouseMotionListener(mfl);		
		if (elementChooser) {
			inputVarName.setEnabled(false);
		} else {
			inputVarName.addTextListener(this);
		}
		oldName = new String("number1");
		add(inputVarName);
	}


	/**
	 * @return Returns the variable's name
	 */
	public final String toString() {
		return inputVarName.getText();
	}


	/**
	 * @return Returns textFields' text.
	 */
	public final String getInputVarName() {
		return inputVarName.getText();
	}


	/**
	 * @param text Sets text field content to text.
	 */
	public void setInputVarName(String text) {
		inputVarName.setText(text);
		repaint();
	}


	public void textValueChanged(TextEvent arg) {
		String newName = inputVarName.getText();
		VariableList varList = aPanel.getVariableList();
		if (varList.isValidName(newName, result)) {
			if (varList.isValidName(oldName, result)) {
				varList.deleteVarList(this, oldName);
			}
			varList.addVarList(this);
			inputVarName.setBackground(SystemColor.text);
		} else {
			varList.deleteVarList(this, oldName);
			result = new Double(0);
			inputVarName.setBackground(Color.RED);
		}
		oldName = newName;
		
		if (getParent() instanceof FormulaPanel) {
			((AppletPanel)getParent().getParent().getParent()).getControlPanel().getFormulaTextField().updateControlPanelText();
		}
		
		repaint();
	}
	

	public void init(AppletPanel ap) {
		aPanel = ap;
		aPanel.getVariableList().addVarList(this);
	}

	
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputVarName.setVisible(vis);	
	}
	
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		inputVarName.setEnabled(enabled);
	}


	public boolean hasBooleanResult() {
		return false;
	}


	public boolean hasDoubleResult() {
		return true;
	}

}