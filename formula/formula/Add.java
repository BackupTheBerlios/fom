/*
 * Created on 07.04.2004
 *
 */
package formula;

//import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 07.04.2004
 *
 */
public class Add extends NumberFormula {

	// braucht kein Javadoc
	public final void calc() throws FormulaException {
		result=new Double(input[0].getDoubleResult()+input[1].getDoubleResult());
	}
	

	// braucht kein Javadoc
	public String toString() {
		if(output!=null)
			return "("+input[0].toString()+"+"+input[1].toString()+")";
		else
			return input[0].toString()+"+"+input[1].toString();
	}


	/**
	 * Creates a "+" with 2 inputs.
	 */
	public Add() {
		input=new Formula[2];
		formulaName="+";
	}
	
}
