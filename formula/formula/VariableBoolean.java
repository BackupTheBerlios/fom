/*
 * Created on 27.06.2004
 */
package formula;

import java.awt.*;
import java.awt.event.*;
import gui.*;

/**
 * Class for variable booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableBoolean extends ConstVarFormula implements TextListener {

	private TextField inputVarName;
	private String oldName;

	private AppletPanel aPanel;

	/**
	 * Creates a variable boolean.
	 */
	public VariableBoolean() {
		this(false);
	}


	/**
	 * Creates a variable boolean.
	 * @param enabled Enables inputVarName for input.
	 */
	public VariableBoolean(boolean elementChooser) {
		super();
		formulaName = "                    Boolean";
		result = new Boolean(false);
		inputVarName = new TextField();
		inputVarName.setFont(new Font("Arial", Font.PLAIN, 11));
		inputVarName.setBackground(SystemColor.text);
		inputVarName.setText("boolean1");
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);		
		if (elementChooser) {
			inputVarName.setEnabled(false);
		} else {
			inputVarName.addTextListener(this);
		}
		oldName = new String("boolean1");
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
	public final void setInputVarName(String text) {
		inputVarName.setText(text);
		repaint();
	}


	public final void textValueChanged(TextEvent arg) {
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
			result = new Boolean(false);
			inputVarName.setBackground(Color.RED);
		}
		oldName = newName;

		if (getParent() instanceof FormulaPanel) {
			((AppletPanel)getParent().getParent().getParent()).getControlPanel().getFormulaTextField().updateControlPanelText();
		}

		repaint();
	}


	public void init(AppletPanel ap) {
		this.aPanel = ap;
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

}