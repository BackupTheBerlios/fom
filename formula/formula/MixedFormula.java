/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.lang.Object;
import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class MixedFormula extends Formula {

	protected Object result;

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
		result = null;
		repaint();
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
	
}