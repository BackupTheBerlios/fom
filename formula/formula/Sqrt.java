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
public class Sqrt extends NumberFormula {

	public final void calc() throws FormulaException {
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double(input[0].getDoubleResult()); //Quadratwurzel muss hier noch her!
	}
	
	public String toString() {
		try { //Das ist Quickfix!
			if (input[0].getDoubleResult() < 0)
				return "("+input[0].toString()+")^1/2"; //Dieses Symbol ist doof.
			else
				throw new FormulaException(Messages.getString("Error.IncompleteFormula")); //Anderer Fehler?
		} catch (FormulaException e) { //Das ist Quickfix!
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a "^1/2" with an input.
	 */
	public Sqrt() {
		input=new Formula[1];
		formulaName="^1/2";
	}

}