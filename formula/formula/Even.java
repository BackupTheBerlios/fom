/*
 * Created on 27.06.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that checks whether a number is even or not. 
 * f(x) = TRUE for x is even, FALSE for x is odd
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Even extends ComparisonFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean ((input[0].getDoubleResult() % 2.0) == 0.0);
	}
	
	/**
	 * Creates an "EVEN" with 1 input.
	 */
	public Even() {
		super();
		input = new Formula[1];
		formulaName = "EVEN";
	}

	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public final String toString() {
		String outString = "EVEN? (";
		if (input[1] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}