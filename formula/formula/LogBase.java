/*
 * Created on 15.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the logarithm of a number with any base. 
 * f(b,x) = log_b (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class LogBase extends NumberFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null)) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else {
			result = new Double (Math.log(input[1].getDoubleResult())/Math.log(input[0].getDoubleResult()));
		}
	}

	/**
	 * Creates a "log_b(x)" with 2 inputs (1st input = base).
	 */
	public LogBase() {
		super();
		input = new Formula[2];
		formulaName = "log_b(x)";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "log_";
		if (input[0] != null)
			outString += input[0].toString();
		outString += " (";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}