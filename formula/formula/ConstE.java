/*
 * Created on 08.07.2004
 */
package formula;

/**
 * This class provides an element that represents the constant e (approx. 2,71828...).
 * f = e
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstE extends ConstVarFormula {

	/**
	 * Creates an "e".
	 */
	public ConstE() {
		super();
		input = new Formula[0];
		formulaName = "e";
		result = new Double (Math.E);
	}
	
	/**
	 * @return Returns the string-equivalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */	
	public final String toString() {
		return "e";
	}

	public String getInputVarName() {
		return null;
	}


	public boolean hasBooleanResult() {
		return false;
	}


	public boolean hasDoubleResult() {
		return true;
	}

}