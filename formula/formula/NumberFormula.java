/* $Id: NumberFormula.java,v 1.17 2004/09/07 13:40:00 shadowice Exp $
 * Created on 05.04.2004
 */
package formula;

import utils.*;

/**
 * Class for all formula elements that use only numbers (double) to calculate.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.17 $
 */
public abstract class NumberFormula extends Formula {

	protected Number result;


	public final double getDoubleResult() throws FormulaException {
		if (result != null)
			return result.doubleValue();
		else
			throw new FormulaException(Messages.getString("Error.NoDoubleResult"));
	}
	

	public final boolean getBooleanResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalBforN"));
	}


	public final boolean isResultCalculated() {
		return (result != null);
	}


	public final String getStringResult() {
		if (result != null) {
			return result.toString();
		} else {
			return null;
		}
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
	
	
	public boolean hasBooleanResult() {
		return false;
	}


	public boolean hasDoubleResult() {
		return result != null;
	}

}