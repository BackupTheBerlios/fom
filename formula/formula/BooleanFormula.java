/*
 * Created on 05.04.2004
 *
 */
package formula;

import Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class BooleanFormula extends Formula {

	protected Boolean result;

	public abstract void calc() throws FormulaException;

	public boolean isValidInput(Object in, int index) {
		return false;
	}

	public boolean isValidOutput(Object out) {
		return false;
	}

/*	public long getLongResult() throws FormulaException {
		return 0;
	}*/

	public boolean getBooleanResult() throws FormulaException {
		if(result!=null)
			return result.booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
			
	}

	public double getDoubleResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalDforB"));
//		return 0;
	}

	public final void clearResult() {
		result = null;
	}

}