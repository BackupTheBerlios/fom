/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.lang.Boolean;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class BooleanFormula extends Formula {

	protected Boolean result;

	public abstract Boolean calc() throws FormulaException;

	public boolean isValidInput(Object in, int index) {
		return false;
	}

	public boolean isValidOutput(Object out) {
		return false;
	}

	public long getLongResult() {
		return 0;
	}

	public boolean getBooleanResult() {
		return false;
	}

	public double getDoubleResult() {
		return 0;
	}

	public final void clearResult() {
		result = null;
	}

}