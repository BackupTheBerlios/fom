/*
 * Created on 07.07.2004
 */
package formula;

import utils.Messages;
import java.math.*;

/**
 * This class provides an element that calculates the factorial of a number. 
 * f(x) = x!
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Factorial extends NumberFormula {

	/**
	 * Calculates the factorial.
	 */
	private final static BigInteger fact(long n) {
		BigInteger result = BigInteger.ONE;
		for (long i = 2L; i <= n; i++) {
			BigInteger j = BigInteger.valueOf(i);
			result = result.multiply(j);
		}
		return result;
	}

	public final void calc() throws FormulaException {	
		if (input[0] == null) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else if (input[0].getDoubleResult() < 0.0) {
			result = new Double (Double.NaN);
		} else {
			result = new Double (fact((long)input[0].getDoubleResult()).doubleValue());
		}
	}
	
	/**
	 * Creates a "fact" with 1 input.
	 */
	public Factorial() {
		super();
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
		outString += ")!";
		return outString;
	}

}