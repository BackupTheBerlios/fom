/*
 * Created on 08.07.2004
 */
package formula;

/**
 * This class provides an element that represents the constant pi (approx. 3,14159...).
 * f = pi
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstPi extends ConstVarFormula {

	/**
	 * Creates a "Pi".
	 */
	public ConstPi() {
		super();
		input = new Formula[0];
		formulaName = "Pi";
		result = new Double (Math.PI);
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		return "Pi";
	}

}