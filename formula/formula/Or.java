/*
 * Created on 27.06.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that calculates the OR-connection of two boolean values.
 * f(a,b) = a OR b
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Or extends BooleanFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean (input[0].getBooleanResult() || input[1].getBooleanResult());
	}
	
	/**
	 * Creates an "OR" with 2 inputs.
	 */
	public Or() {
		super();
		input = new Formula[2];
		formulaName = "OR";
	}

	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += " OR ";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}