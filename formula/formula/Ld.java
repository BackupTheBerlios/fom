/*
 * Created on 02.09.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates base 2 logarithm of a number. 
 * f(x) = ld (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Ld extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else {
			result = new Double (Math.log(input[0].getDoubleResult())/Math.log(2));
		}
	}

	/**
	 * Creates a "ld" with 1 input.
	 */
	public Ld() {
		super();
		input = new Formula[1];
		formulaName = "ld";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "ld (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}