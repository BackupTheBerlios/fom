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

	public boolean isValidInput(Formula in, int index) {
		//TODO IsValidInput muss MixedFormula akzeptieren.
		if ((in instanceof NumberFormula) || (in instanceof ComparisonFormula))
			return true;
		else if (in instanceof MixedFormula)
		//Funktioniert noch nicht.
			return false;
		else
			return false;
	}
	//TODO Wenn isValidInput funktioniert, auch isValidOutput fertigstellen.
	public boolean isValidOutput(Formula out) {
		return false;
	}

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

	public final void clearResult() {
		result = null;
	}

}