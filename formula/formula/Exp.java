/*
 * Created on 07.07.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Exp extends NumberFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (input[0].getDoubleResult() * Math.pow(10, input[1].getDoubleResult()));
	}
	
	/**
	 * Creates an "Exp" with 2 inputs.
	 */
	public Exp() {
		input = new Formula[1];
		formulaName = "Exp";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += " * 10^(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "))";
		return outString;
	}

}