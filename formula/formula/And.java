/*
 * Created on 15.04.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the AND-connection of two boolean values.
 * f(a,b) = a AND b
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class And extends BooleanFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean (input[0].getBooleanResult() && input[1].getBooleanResult());
	}
	
	/**
	 * Creates an "AND" with 2 inputs.
	 */
	public And() {
		super();
		input = new Formula[2];
		formulaName = "AND";
	}

	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += " AND ";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}