/*
 * Created on 22.05.2004
 */
package formula;

/**
 * This is an abstract class for all Formula-Object, which are accepting
 * number values for input and returning boolean values.
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class ComparisonFormula extends Formula {

	protected Boolean result;

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public final void clearResult() {
		result = null;
	}

	public abstract void calc();

	public boolean getBooleanResult() {
		return false;
	}

	public double getDoublteResult() {
		return 0;
	}
//TODO Wenn in BooleanFormula isValidInput funktioniert, auch diese fertigstellen.
	public boolean isValidInput(Formula in, int index) {
		return false;
	}

	public boolean isValidOutput(Formula in) {
		return false;
	}

}