/*
 * Created on 05.04.2004
 *
 */
package formula;

import utils.Messages;

/**
 * This is an abstract class for all Formula-Object, which are accepting
 * boolean values for input/output.
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class BooleanFormula extends Formula {

	protected Boolean result;

	/**
	 * Calculates the result of this formula-object and stores it in result.
	 * @throws Throws a FormulaException when there aren't all inputs connected with a formula-object.
	 */
	public abstract void calc() throws FormulaException;

/*	public long getLongResult() throws FormulaException {
		return 0;
	}*/

	public boolean getBooleanResult() throws FormulaException {
		if (result != null)
			return result.booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	public double getDoubleResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalDforB"));
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
		result = null;
	}

	/**
	 * @param index Number of the input (0=left...max-1=right)
	 * @return Returns an array of all possible classes for the input.
	 */
	public Class[] getInputTypes(int in) {
		return new Class[0];
	}

	public Class[] getOutputTypes() {
		return new Class[0];
	}

	public boolean isValidInput(Formula in, int whichInput) {
		return false;
	}

	public boolean isValidOutput(Formula in, int whichInput) {
		return false;
	}

}