/*
 * Created on 08.07.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Deg2grad extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (100*input[0].getDoubleResult()/90);
	}

	/**
	 * Creates a "deg to grad" with 1 input.
	 */
	public Deg2grad() {
		input = new Formula[1];
		formulaName = "deg to grad";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(grad (";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "))";
		return outString;
	}

}