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
public abstract class NumberFormula extends Formula {

	protected Number result;

	public boolean isValidInput(Object in, int index) {
		return false;
	}

	public boolean isValidOutput(Object out) {
		return false;
	}

/*	public long getLongResult() {
		return 0;
	}*/

	public double getDoubleResult() throws FormulaException {
		return 0.0;
	}

	public boolean getBooleanResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalBforN"));
	}

	/**
	 * Calculates this fomula-element by using the results from it's inputs.
	 *
	 * @throws FormulaException Is thrown, when the inputs have no results.
	 */
	public abstract void calc() throws FormulaException;

	public final void clearResult() {
		result = null;
	}

}