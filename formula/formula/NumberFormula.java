/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.lang.Number;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class NumberFormula extends Formula {

	protected Number result;

	public boolean isValidInput(Object in, int index) {
		return false;
	}

	public boolean isValidOutput(Object out) {
		return false;
	}

	public long getLongResult() {
		return 0;
	}

	public double getDoubleResult() {
		return 0;
	}

	public boolean getBooleanResult() {
		return false;
	}

	public abstract Number calc() throws FormulaException;

	public final void clearResult() {
		result = null;
	}

}