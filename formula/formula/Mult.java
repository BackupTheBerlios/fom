/*
 * Created on 22.05.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the product of two numbers.
 * f(x,y) = x * y
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Mult extends NumberFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (input[0].getDoubleResult() * input[1].getDoubleResult());
	}

	/**
	 * Creates a "*" with 2 inputs.
	 */
	public Mult() {
		super();
		input = new Formula[2];
		formulaName = "*";
	}	
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += " * ";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}