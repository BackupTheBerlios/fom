/*
 * Created on 08.07.2004
 *
 */
package formula;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class ConstPi extends NumberFormula {

	public final void calc() throws FormulaException {
		result = new Double (Math.PI);
	}
	
	/**
	 * Creates a "pi".
	 */
	public ConstPi() {
		input = new Formula[0];
		formulaName = "pi";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(pi)";
		return outString;
	}

}