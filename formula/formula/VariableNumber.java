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

	/**
	 * Creates a variable number.
	 */
	public VariableNumber() {
		formulaName = "                      Number";
		result = new Double (0);
		inputVarName = new TextField();
		inputVarName.addTextListener(this);
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		inputVarName.setText("number1");
		add(inputVarName);
	}

	public void textValueChanged(TextEvent arg) {



	}

}