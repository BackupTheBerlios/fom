/*
 * Created on 08.07.2004
 */
package formula;

/**
 * This class provides an element that represents a radom number.
 * f = RANDOM
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Random extends NumberFormula {

	public final void calc() throws FormulaException {
		result = new Double (Math.random());
	}
	
	/**
	 * Creates a "RANDOM".
	 */
	public Random() {
		super();
		input = new Formula[0];
		formulaName = "RANDOM";
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		String outString = "(RANDOM)";
		if (result != null) {
			try {
				outString = "(" + getDoubleResult() + ")";
			} catch (FormulaException e) {
				System.err.println(e.getMessage());
			}
		}
		return outString;
	}

}