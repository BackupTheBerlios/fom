/*
 * Created on 26.08.2004
 */
package formula;

import utils.Messages;

/** TODO JavaDoc ergänzen
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class CustomFormula extends Formula {

	protected Object result;

	protected Class[] validInputTypes;

	public CustomFormula(Formula root) throws FormulaException {
		if (root.isCompleteSubTree()) {
			
		} else {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		}
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public final void clearResult() {
		result = null;
	}

	public final double getDoubleResult() throws FormulaException {
		if ((result != null) && (result instanceof Double))
			return ((Double)result).doubleValue();
		else
			throw new FormulaException(Messages.getString("Error.NoDoubleResult"));
	}


	public final boolean getBooleanResult() throws FormulaException {
		if ((result != null) && (result instanceof Boolean))
			return ((Boolean)result).booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	public final boolean isResultCalculated() {
		return (result != null);
	}

	public final String getStringResult() {
		if (result != null) {
			if (result instanceof Boolean)
				return ((Boolean)result).toString();
			else if (result instanceof Double)
				return ((Double)result).toString();
			else
				return null;
			}
		else
			return null;
	}

	public final void calc() throws FormulaException {
		
	}

	public final String toString() {
		return "";
	}

	public final Class[] getInputTypes(int index) throws FormulaException {
		return new Class[] {Object.class};
	}

	public final Class[] getOutputTypes() throws FormulaException {
		return new Class[] {Object.class};
	}

}
