/*
 * Created on 26.05.2004
 */
package formula;

import utils.Messages;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class KonstVarFormula extends Formula {

	protected Object result;

	/**
	 * Overwrites function. Konstants/Variables have no input.
	 */
	public int getInputCount() {
		return 0;
	}

	/**
	 * Overwrites function. Konstants/Variables have no input.
	 */
	public Formula getInput(int index) {
		return null;
	}

	/**
	 * Overwrites function. Konstants/Variables have no input.
	 */
	public void setInput(Formula in, int index) {
	}

// TODO calc / getDoubleResult / getBooleanResult
	public abstract void calc() throws FormulaException;

	public double getDoubleResult() {
		return 0;
	}

//	public long getLongResult() {
//		return 0;
//	}

	public boolean getBooleanResult() {
		return false;
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
		result = null;
	}
//TODO getInputTypes
	public Class[] getInputTypes(int index) throws FormulaException {
		return new Class[0];
	}

	public Class[] getOutputTypes() throws FormulaException {
		Class[] classArray;
		if (result == null) {
			classArray = new Class[2];
			classArray[0] = Boolean.class;
			classArray[1] = Number.class; }
		else {
			classArray = new Class[1];
			if (result instanceof Boolean)
				classArray[0] = Boolean.class;
			else if (result instanceof Number)
				classArray[0] = Number.class;
			else
				throw new FormulaException(Messages.getString("Error.IllegalDataType"));
		}
		return classArray;
	}

//	public boolean isValidInput(Formula in, int whichInput) {
//		return false;
//	}
//
//	public boolean isValidOutput(Formula in, int whichInput) {
//		return false;
//	}

}