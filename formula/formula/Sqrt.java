/*
 * Created on 22.05.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the square root of a number.
 * f(x) = SQRT(x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Sqrt extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else if (input[0].getDoubleResult() < 0.0) {
			throw new FormulaException(Messages.getString("Error.ArithmeticError"));
		} else {
			result = new Double (Math.sqrt(input[0].getDoubleResult()));
		}
	}

	/**
	 * Creates a "SQRT" with 1 input.
	 */
	public Sqrt() {
		super();
		input = new Formula[1];
		formulaName = "SQRT";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "SQRT(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}