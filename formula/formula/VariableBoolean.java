/*
 * Created on 27.06.2004
 */
package formula;

/**
 * Class for variable booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class VariableBoolean extends ConstVarFormula {

//	TODO Mit Maurice besprechen

	public void setResult(Number result) {
		this.result = result;
	}

	public final String toString() {
		return result.toString();
	}

	public VariableBoolean() {
		formulaName = "VariableBoolean";
		result = new Boolean (false);
	}

}