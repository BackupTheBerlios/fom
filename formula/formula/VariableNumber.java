/*
 * Created on 27.06.2004
 */
package formula;

/**
 * Class for variable numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableNumber extends ConstVarFormula {

//TODO Mit Maurice besprechen

	public void setResult(Number result) {
		this.result = result;
	}

	public final String toString() {
		return result.toString();
	}

	public VariableNumber() {
		input = new Formula[0];
		formulaName = "VariableNumber";
		result = new Double (0);
	}

}