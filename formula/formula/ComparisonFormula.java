/*
 * Created on 22.05.2004
 */
package formula;

import utils.Messages;

/**
 * This is an abstract class for all Formula-Object, which are accepting
 * number values for input and returning boolean values.
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class ComparisonFormula extends Formula {

	protected Boolean result;

	public abstract void calc() throws FormulaException;

	public boolean getBooleanResult() throws FormulaException {
		if (result != null)
			return result.booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
		result = null;
	}
//TODO getInputTypes / getOutputTypes
	public Class[] getInputTypes(int in) {
		return new Class[0];
	}

	public Class[] getOutputTypes() {
		return new Class[0];
	}

	public double getDoubleResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalDforB"));
	}

}