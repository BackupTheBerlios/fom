/*
 * Created on 05.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the hyperbolic tangent of a number.
 * f(x) = coth (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Coth extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double ((Math.pow (Math.E, 2*(input[0].getDoubleResult()))+1) / (Math.pow(Math.E, 2*(input[0].getDoubleResult()))-1));
	}

	/**
	 * Creates a "coth" with 1 input.
	 */
	public Coth() {
		super();
		input = new Formula[1];
		formulaName = "coth";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "coth(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}