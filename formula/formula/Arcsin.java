/*
 * Created on 05.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the arcsin of a number.
 * f(x) = arcsin (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Arcsin extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else if (Math.abs(input[0].getDoubleResult()) > 1.0) {
			throw new FormulaException(Messages.getString("Error.ArithmeticError"));
		} else {
			result = new Double (Math.asin(input[0].getDoubleResult()));
		}
	}

	/**
	 * Creates an "arcsin" with 1 input.
	 */
	public Arcsin() {
		super();
		input = new Formula[1];
		formulaName = "arcsin (radiant)";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "arcsin(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}