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

	public final void calc() throws FormulaException {

	}

	public String toString() {
		return "("+input[0].toString()+"-"+input[1].toString()+")";
	}

	public Sub() {
		input=new Formula[2];
		formulaName="-";
	}

}