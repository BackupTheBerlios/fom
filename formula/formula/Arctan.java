/*
 * Created on 05.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the arctan of a number.
 * f(x) = arctan (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Arctan extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (Math.atan(input[0].getDoubleResult()));
	}

	/**
	 * Creates an "arctan" with 1 input.
	 */
	public Arctan() {
		super();
		input = new Formula[1];
		formulaName = "arctan (rad)";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "arctan(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}