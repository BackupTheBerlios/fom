/* $Id: VariableBoolean.java,v 1.21 2004/09/10 11:09:24 shadowice Exp $
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
 * @version $Revision: 1.21 $
 */
public class VariableBoolean extends ConstVarFormula implements TextListener {

	private TextField inputVarName;
	private String oldName;
	private MouseForwardListener mfListener;

	private AppletPanel aPanel;

	/**
	 * Creates a variable boolean.
	 */
	public VariableBoolean() {
		this(true);
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
		inputVarName.setFont(DEFAULT_FONT);
		inputVarName.setBackground(Color.white);
		inputVarName.setText("boolean1");
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDTH/2, BOXHEIGHT-6);
		mfListener = new MouseForwardListener();
		inputVarName.addMouseListener(mfListener);
		inputVarName.addMouseMotionListener(mfListener);		
		if (elementChooser) {
			inputVarName.addTextListener(this);
		} else {
			setEnabled(false);
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
			inputVarName.setBackground(Color.white);
		} else {
			varList.deleteVarList(this, oldName);
			result = new Boolean(false);
			inputVarName.setBackground(Color.red);
		}
		oldName = newName;

		if (getParent() instanceof FormulaPanel) {
			((AppletPanel)getParent().getParent().getParent()).getControlPanel().getFormulaTextField().updateControlPanelText();
		}

		repaint();
	}


	/**
	 * This method must be called if the VariableBoolean is added to the FormulaPanel!
	 * @param ap root AppletPanel
	 */
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
		inputVarName.setEditable(enabled);
	}


	public boolean hasBooleanResult() {
		return true;
	}


	public boolean hasDoubleResult() {
		return false;
	}


	public Object clone() {
		VariableBoolean clonedVB = (VariableBoolean)super.clone();
		clonedVB.inputVarName.setText(inputVarName.getText());
		clonedVB.result = new Boolean(result.toString());
		return clonedVB;
	}

}