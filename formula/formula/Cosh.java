/*
 * Created on 05.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the hyperbolic cosine of a number.
 * f(x) = cosh (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Cosh extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (0.5*(Math.pow (Math.E, (input[0].getDoubleResult())) + Math.pow(Math.E, -(input[0].getDoubleResult()))));
	}

	/**
	 * Creates a "cosh" with 1 input.
	 */
	public Cosh() {
		super();
		input = new Formula[1];
		formulaName = "cosh";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "cosh (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}