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
		if (input[0] == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
		//MAURICE: Kann ich aus Double eine Int hinkriegen?
		// mit Typecast: (int)1.5D = 1
		// mit Math.round: Math.round(1.5D) = 2L
		// FRAGE: Wozu ist denn "factorial" da?
//			result = new Double (Factorial.fact((input[0].getDoubleResult())));
			result = new Double (1234567890);
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