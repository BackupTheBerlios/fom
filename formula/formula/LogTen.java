/*
 * Created on 15.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the logarithm of a number with base ten. 
 * f(x) = log (x)
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class LogTen extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else {
			result = new Double (Math.log(input[0].getDoubleResult())/Math.log(10));
		}
	}

	/**
	 * Creates a "log" with 1 input.
	 */
	public LogTen() {
		super();
		input = new Formula[1];
		formulaName = "log";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "log (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}