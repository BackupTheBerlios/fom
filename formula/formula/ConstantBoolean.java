/*
 * Created on 27.06.2004
 */
package formula;

/**
 * Class for constant booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantBoolean extends ConstVarFormula {

//	TODO Mit Maurice besprechen

	public void setResult(Number result) {
		this.result = result;
	}

	public final String toString() {
		return result.toString();
	}

	public ConstantBoolean() {
		input = new Formula[0];
		formulaName = "ConstantNumber";
		result = new Boolean (false);
	}

}