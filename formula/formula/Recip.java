/*
 * Created on 22.05.2004
 *
 */
package formula;

import Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Recip extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double(1 / input[0].getDoubleResult());
	}
	
	public String toString() {
		try {
			if (input[0].getDoubleResult() == 0)
				return "(1/"+input[0].toString()+")";
			else
				throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} catch (FormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a "1/x" with an input.
	 */
	public Recip() {
		input=new Formula[1];
		formulaName="1/x";
	}

}