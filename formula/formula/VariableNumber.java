/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/**
 * Class for variable numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableNumber extends ConstVarFormula implements TextListener {

//TODO Variablen interaktiv machen

	protected TextField inputVarName;
	protected String oldName;

	/**
	 * Creates a variable number.
	 */
	public VariableNumber() {
		super();
		formulaName = "                      Number";
		result = new Double(0);
		inputVarName = new TextField();
		inputVarName.addTextListener(this);
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		inputVarName.setText("number1");
		oldName = new String(inputVarName.getText());
		addVarList(this);
		add(inputVarName);
	}

	/**
	 * Creates a variable boolean.
	 * @param enabled Enables inputVarName for input.
	 */
	public VariableNumber(boolean elementChooser) {
		super();
		formulaName = "                     Constant";
		result = new Double (0);
		inputVarName = new TextField();
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		if (elementChooser) {
			inputVarName.enable(false);
			addVarList(this);
		} else {
			inputVarName.addTextListener(this);
		}
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
		if (newName.length() == 0) {
			deleteVarList(this, oldName);
			result = new Double(0);
		} else {
			if (oldName.length() != 0) {
				deleteVarList(this, oldName);
			}
			addVarList(this);
		}
		oldName = newName;
		this.repaint();
	}

}