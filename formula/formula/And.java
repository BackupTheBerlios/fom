/*
 * Created on 15.04.2004
 *
 */
package formula;

import Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class And extends BooleanFormula {

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else
			result = new Boolean (input[0].getBooleanResult() && input[1].getBooleanResult());
	}

	public String toString() {
		// TODO Auto-generated method stub
		// Maurice: Muss hier irgendwas abgefangen werden?
		return "(" + input[0].toString() + "&" + input[1].toString() + ")";
	}

	public And() {
		input=new Formula[2];
		//Maurice: Warum geht diese Zeile nicht? Nur bei Arrays benötigt?
		//output=new Formula;
		formulaName="&";
		result=null;
	}

}