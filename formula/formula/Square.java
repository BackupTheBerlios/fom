/*
 * Created on 22.05.2004
 *
 */
package formula;

import Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Square extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double(input[0].getDoubleResult() * input[0].getDoubleResult());
	}
	
	public String toString() {
		return "("+input[0].toString()+")²";
	}

	/**
	 * Creates a "²" with an input.
	 */
	public Square() {
		input=new Formula[1];
		formulaName="²";
	}

}