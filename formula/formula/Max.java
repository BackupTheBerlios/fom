/*
 * Created on 07.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that checks which of the two numbers is the greater one. 
 * f(x,y) = x for x>=y, y for x<y
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Max extends NumberFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (Math.max (input[0].getDoubleResult(), input[1].getDoubleResult()));
	}
	
	/**
	 * Creates a "max" with 2 inputs.
	 */
	public Max() {
		super();
		input = new Formula[2];
		formulaName = "max";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "max (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "; ";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}