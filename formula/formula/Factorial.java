/*
 * Created on 07.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the factorial of a number. 
 * f(x) = x!
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Factorial extends NumberFormula {

	/**
	 * Calculates the factorial.
	 */
	private final static long fact(long n) {
		long result = 1;
		for (long i = 2; i <= n; i++) {
			result = result*i;
		}
		return result;
	}

	public final void calc() throws FormulaException {	
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (fact((int)input[0].getDoubleResult()));
	}
	
	/**
	 * Creates a "fact" with 1 input.
	 */
	public Factorial() {
		super();
		input = new Formula[1];
		formulaName = "fact";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")!";
		return outString;
	}

}