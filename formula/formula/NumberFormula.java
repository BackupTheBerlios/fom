/*
 * Created on 05.04.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class NumberFormula extends Formula {

	protected Number result;

	public boolean isValidInput(Formula in, int index) {
		if ((input[0] instanceof NumberFormula) && (input[1] instanceof NumberFormula))
			return true;
		else
			return false;
	}

	public boolean isValidOutput(Formula out) {
		return false;
	}

	public double getDoubleResult() throws FormulaException {
		return 0.0;
	}

	public boolean getBooleanResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalBforN"));
	}

	/**
	 * Calculates this fomula-element by using the results from it's inputs.
	 * @throws FormulaException when the inputs are empty.
	 */
	public abstract void calc() throws FormulaException;

	public final void clearResult() {
		result = null;
	}

}