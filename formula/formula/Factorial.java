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

	/**
	 * Calculates the factorial.
	 */
	static int fact(int n) {
		int e = 1;

		if (n == 0 || n == 1)
		  return e;

		if (n > 1)
		  for (int i = 1; i <= n; i++)
			e = e*i;
		return e;
	  }

	public final void calc() throws FormulaException {	
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Double (Factorial.fact((int)input[0].getDoubleResult()));
	}
	
	/**
	 * Creates a "fact" with 1 input.
	 */
	public Factorial() {
		input = new Formula[1];
		formulaName = "fact";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += "!)";
		return outString;
	}

}