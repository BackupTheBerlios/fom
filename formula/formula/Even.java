/*
 * Created on 27.06.2004
 */
package formula;


import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Even extends ComparisonFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean ((input[0].getDoubleResult() % 2.0) == 0.0);
	}

	public Even() {
		input = new Formula[1];
		formulaName = "Even";
	}

	/**
	 * @return Returns the string-equvalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public final String toString() {
		String outString = "(Even? ";
		if (input[1] != null)
			outString += input[0].toString();
		outString += ")";
		return outString;
	}

}