/*
 * Created on 07.07.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Factorial extends NumberFormula {

	public final void calc() throws FormulaException {	
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
		//MAURICE: Kann ich aus Double eine Int hinkriegen?
//			result = new Double (Factorial.fact((input[0].getDoubleResult())));
			result = new Double (1234567890);
	}
	
	/**
	 * Creates a "fact" with 2 inputs.
	 */
	public Factorial() {
		input = new Formula[2];
		formulaName = "fact";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(fact ";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ", ";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

}