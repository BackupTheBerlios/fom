/*
 * Created on 17.05.2004
 *
 */
package formula;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Sub extends NumberFormula {

	public final Number calc() throws FormulaException {
		return null;
	}

	public String toString() {
		return "("+input[0].toString()+"-"+input[1].toString()+")";
	}

	public Sub() {
		input=new Formula[2];
		formulaName="-";
	}

}