/*
 * Created on 15.04.2004
 *
 */
package formula;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class IfThenElse extends MixedFormula {
	/* (non-Javadoc)
	 * @see formula.MixedFormula#calc()
	 */
	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null) || (input[2] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		else if (getOutputTypes()[0] == Boolean.class) {
			if (input[0].getBooleanResult())
				result = new Boolean (input[1].getBooleanResult());
			else
				result = new Boolean (input[2].getBooleanResult()); }
		else {
			if (input[0].getBooleanResult())
				result = new Double (input[1].getDoubleResult());
			else
				result = new Double (input[2].getDoubleResult()); }
	}

	/* (non-Javadoc)
	 * @see formula.Formula#toString()
	 */
	public final String toString() {
		String outString = "(If(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")then(";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")else(";
		if (input[2] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}


	public final double getDoubleResult() throws FormulaException {
		if (result != null && result instanceof Double)
			return ((Double)result).doubleValue();
		else
			throw new FormulaException(Messages.getString("Error.NoDoubleResult"));
	}

//	public final long getLongResult() {
//		return 0;
//	}

	public final boolean getBooleanResult() throws FormulaException {
		if (result != null && result instanceof Boolean)
			return ((Boolean)result).booleanValue();
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	public final Class[] getInputTypes(int index) throws FormulaException {
		Class[] classArray;
		if (index == 0) {
			classArray = new Class[1];
			classArray[0] = Boolean.class; }
		else if (index == 1) {
			if (input[2] == null && output == null) {
				classArray = new Class[2];
				classArray[0] = Boolean.class;
				classArray[1] = Number.class; }
			else if (input[2] != null)
				classArray = input[2].getOutputTypes();
			else
				classArray = output.getInputTypes(indexOfInput(this, output)); }
		else if (index == 2) {
			if (input[1] == null && output == null) {
				classArray = new Class[2];
				classArray[0] = Boolean.class;
				classArray[1] = Number.class; }
			else if (input[1] != null)
				classArray = input[1].getOutputTypes();
			else
				classArray = output.getInputTypes(indexOfInput(this, output)); }
		else {
			throw new FormulaException(Messages.getString("Error.OutOfBound")); }
		return classArray;
	}

	public final Class[] getOutputTypes() throws FormulaException {
		Class[] classArray;
		if (input[1] == null && input[2] == null) {
			classArray = new Class[2];
			classArray[0] = Boolean.class;
			classArray[1] = Number.class; }
		else if (input[1] != null)
			classArray = input[1].getOutputTypes();
		else
			classArray = input[2].getOutputTypes();
		return classArray;
	}

//	public final boolean isValidInput(Formula in, int whichInput) {
//		return false;
//	}
//
//	public final boolean isValidOutput(Formula in, int whichInput) {
//		return false;
//	}

}