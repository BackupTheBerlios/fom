/*
 * Created on 07.04.2004
 *
 */
package formula;

import Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Add extends NumberFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double(input[0].getDoubleResult() + input[1].getDoubleResult());
	}
	
	public String toString() {
		if(output!=null)
			return "("+input[0].toString()+"+"+input[1].toString()+")";
		else
			return input[0].toString()+"+"+input[1].toString();
	}


	/**
	 * Creates a "+" with 2 inputs.
	 */
	public Add() {
		input=new Formula[2];
		formulaName="+";
	}
	
}
