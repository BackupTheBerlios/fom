/*
 * Created on 05.07.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Rad2deg extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (180*input[0].getDoubleResult()/Math.PI);
	}

	/**
	 * Creates a "rad to deg" with 1 input.
	 */
	public Rad2deg() {
		input = new Formula[1];
		formulaName = "rad to deg";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(rad (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "))";
		return outString;
	}

}