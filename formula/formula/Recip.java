/*
 * Created on 22.05.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Recip extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (1 / input[0].getDoubleResult());
	}

	/**
	 * Creates a "1/x" with 1 input.
	 */
	public Recip() {
		input = new Formula[1];
		formulaName = "1/x";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(1 / ";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}