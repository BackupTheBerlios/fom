/*
 * Created on 08.07.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that converts grad to degree.
 * f([grad]) = [degree]
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Grad2deg extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (90*input[0].getDoubleResult()/100);
	}

	/**
	 * Creates a "grad to deg" with 1 input.
	 */
	public Grad2deg() {
		super();
		input = new Formula[1];
		formulaName = "grad to deg";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "deg (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}