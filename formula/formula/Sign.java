/*
 * Created on 22.05.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Sign extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double(- input[0].getDoubleResult());
	}
	
	public String toString() {
		try {
			if (input[0].getDoubleResult() > 0)
				return "(-"+input[0].toString()+")";
			else
				return input[0].toString();
		} catch (FormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a "+/-" with an input.
	 */
	public Sign() {
		input=new Formula[1];
		formulaName="+/-";
	}

}