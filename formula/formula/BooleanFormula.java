/*
 * Created on 05.04.2004
 */
package formula;

import utils.Messages;

/**
 * This is an abstract class for all Formula-objects which accept
 * boolean values for input.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class BooleanFormula extends Formula {

	protected Boolean result;
//	TODO Einige auskommentierte Sachen!
/*	public long getLongResult() throws FormulaException {
		return 0;
	}*/

	public final boolean getBooleanResult() throws FormulaException {
		if (result != null)
			return result.booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	public final double getDoubleResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalDforB"));
	}

	public final String getStringResult() {
		if (result != null)
			return result.toString();
		else
			return null;
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
	public Class[] getInputTypes(int index) throws FormulaException {
		Class[] classArray = {Boolean.class};
		return classArray;
	}

	public Class[] getOutputTypes() throws FormulaException {
		Class[] classArray = {Boolean.class};
		return classArray;
	}

}