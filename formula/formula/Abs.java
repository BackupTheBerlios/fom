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
public class Abs extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (Math.abs(input[0].getDoubleResult()));
	}

	/**
	 * Creates an "abs" with 1 input.
	 */
	public Abs() {
		input = new Formula[1];
		formulaName = "abs";
	}	
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "|";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "|";
		return outString;
	}

}