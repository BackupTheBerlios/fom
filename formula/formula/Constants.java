/*
 * Created on 18.06.2004
 *
 */
package formula;

import utils.Messages;
import java.awt.TextField;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Constants extends ConstVarFormula {

	protected TextField infield = new TextField();

	public final void calc() throws FormulaException {
		if (infield == null)
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
//		else
//			return
	}
	
	public String toString() {
		if(output!=null)
			return "("+input[0].toString()+"+"+input[1].toString()+")";
		else
			return input[0].toString()+"+"+input[1].toString();
	}

	/**
	 * Creates a "+" with 2 inputs.
	 */
//	public Constants() {
//		g.getFontMetrics().stringWidth(formulaName)
//	}

}