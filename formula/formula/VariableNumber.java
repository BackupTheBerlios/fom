/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for variable numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableNumber extends ConstVarFormula implements TextListener {

	protected TextField inputVarName;
	protected String oldName;

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
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDHT/2, BOXHEIGHT-6);		
		if (elementChooser) {
			inputVarName.enable(false);
		} else {
			inputVarName.addTextListener(this);
			addVarList(this);
		}
		oldName = new String("number1");
		add(inputVarName);
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
		if (isValidName(newName, result)) {
			if (isValidName(oldName, result)) {
				deleteVarList(this, oldName);
			}
			addVarList(this);
			inputVarName.setBackground(SystemColor.text);
		} else {
			deleteVarList(this, oldName);
			result = new Double(0);
			inputVarName.setBackground(Color.RED);
		}
		oldName = newName;
		repaint();
	}
	
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputVarName.setVisible(vis);	
	}

}