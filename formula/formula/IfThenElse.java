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
// TODO Alles
	/* (non-Javadoc)
	 * @see formula.MixedFormula#calc()
	 */
	public final Object calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null) || (input[2] == null))
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		return null;
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


	public final double getDoubleResult() {
		if (result != null && result instanceof Double)
			return result;
		else
			throw new FormulaException(Messages.getString("Error.NoDoubleResult"));
	}

//	public final long getLongResult() {
//		return 0;
//	}

	public final boolean getBooleanResult() {
		if (result != null && result instanceof Boolean)
			return result;
		else
			throw new FormulaException(Messages.getString("Error.NoBooleanResult"));
	}

	public final Class[] getInputTypes(int index) {
		Class[] classArray;
		if (index == 0) {
			classArray = new Class[1];
			classArray[0] = Boolean.class; }
		//TODO Else-Block noch falsch
		else {
			if (output == null && input[1] == null && input[2] == null) {
				classArray = new Class[2];
				classArray[0] = Boolean.class;
				classArray[1] = Number.class; }
			else {
			}}
		return classArray;
	}

	public final Class[] getOutputTypes() {
		return new Class[0];
	}

//	public final boolean isValidInput(Formula in, int whichInput) {
//		return false;
//	}
//
//	public final boolean isValidOutput(Formula in, int whichInput) {
//		return false;
//	}

}