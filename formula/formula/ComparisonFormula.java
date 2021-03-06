/* $Id: ComparisonFormula.java,v 1.12 2004/08/30 19:30:52 shadowice Exp $
 * Created on 22.05.2004
 */
package formula;

import utils.Messages;

/**
 * This is an abstract class for all Formula-objects which accept
 * number values for input and return boolean values.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.12 $
 */
public abstract class ComparisonFormula extends Formula {

	protected Boolean result;


	public boolean getBooleanResult() throws FormulaException {
		if (result != null)
			return result.booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}


	public String getStringResult() {
		if (result != null)
			return result.toString();
		else
			return null;
	}


	public final boolean isResultCalculated() {
		return (result != null);
	}


	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
		result = null;
		repaint();
	}


	public Class[] getInputTypes(int index) throws FormulaException {
		Class[] classArray = {Number.class};
		return classArray;
	}


	public Class[] getOutputTypes() throws FormulaException {
		Class[] classArray = {Boolean.class};
		return classArray;
	}


	public double getDoubleResult() throws FormulaException {
		throw new FormulaException(Messages.getString("Error.IllegalDforB"));
	}
	
	
	public boolean hasBooleanResult() {
		return result != null;
	}
	
	
	public boolean hasDoubleResult() {
		return false;
	}

}