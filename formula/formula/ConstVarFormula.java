/*
 * Created on 26.05.2004
 */
package formula;

import utils.Messages;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class ConstVarFormula extends Formula {

	protected Object result;

	/**
	 * Overwrites function. Constants/Variables have no input.
	 */
	public final int getInputCount() {
		return 0;
	}

	/**
	 * Overwrites function. Constants/Variables have no input.
	 */
	public final Formula getInput(int index) {
		return null;
	}

	/**
	 * Overwrites function. Constants/Variables have no input.
	 */
	public final void setInput(Formula in, int index) {
	}


	public abstract void calc() throws FormulaException;

	public double getDoubleResult() {
		return ((Double)result).doubleValue();
	}

//	public long getLongResult() {
//		return 0;
//	}

	public boolean getBooleanResult() {
		return ((Boolean)result).booleanValue();
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public void clearResult() {
	}
	

	public Class[] getInputTypes(int index) throws FormulaException {
		throw new FormulaException(Messages.getString("Error.ConstVarInput"));
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

}