/*
 * Created on 05.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the cotangent of a number.
 * f(x) = cot (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Cot extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (1 / Math.tan(input[0].getDoubleResult()));
	}

	/**
	 * Creates a "cot" with 1 input.
	 */
	public Cot() {
		super();
		input = new Formula[1];
		formulaName = "cot";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "cot (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}