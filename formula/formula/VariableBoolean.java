/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

/**
 * Class for variable booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableBoolean extends ConstVarFormula implements TextListener {

//	TODO Variablen interaktiv machen

	protected TextField inputVarName;

	/**
	 * Creates a variable boolean.
	 */
	public VariableBoolean() {
		formulaName = "                      Boolean";
		result = new Boolean (false);
		inputVarName = new TextField();
		inputVarName.addTextListener(this);
		inputVarName.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);		
		inputVarName.setText("boolean1");
		add(inputVarName);
	}

	public void textValueChanged(TextEvent arg) {



	}

}