/*
 * Created on 07.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the power with base 'e'. 
 * f(x) = e^x
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Euler extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (Math.pow(Math.E, input[0].getDoubleResult()));
	}
	
	/**
	 * Creates an "e^x" with 1 input.
	 */
	public Euler() {
		super();
		input = new Formula[1];
		formulaName = "e^x";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "e^(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}