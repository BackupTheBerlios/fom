/*
 * Created on 27.06.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Not extends BooleanFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean (! input[0].getBooleanResult());
	}
	
	/**
	 * Creates a "NOT" with 1 input.
	 */
	public Not() {
		input = new Formula[1];
		formulaName = "NOT";
	}

	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public final String toString() {
		String outString = "(NOT ";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}