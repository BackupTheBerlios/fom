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

	public double getDoubleResult() throws FormulaException {
		if (result != null)
			return result.doubleValue();
		else
			throw new FormulaException(Messages.getString("Error.NoDoubleResult"));
	}

	public boolean getBooleanResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalBforN"));
	}

	/**
	 * Calculates this fomula-element by using the results from it's inputs.
	 * @throws FormulaException when the inputs are empty.
	 */
	public abstract void calc() throws FormulaException;

	/**
	 * @param index Number of the input (0=left...max-1=right)
	 * @return Returns an array of all possible classes for the input.
	 */
	public Class[] getInputTypes(int index) throws FormulaException {
		Class[] classArray = {Number.class}; 
		return classArray; 
	}

	public Class[] getOutputTypes() throws FormulaException {
		Class[] classArray = {Number.class}; 
		return classArray; 
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public final void clearResult() {
		result = null;
	}

	public boolean isValidOutput(Formula in, int whichInput) {
		return false;
	}
	
	public boolean isValidInput(Formula in, int index) {
		if (input[index] instanceof NumberFormula)
			return true;
		else
			return false;
	}

}