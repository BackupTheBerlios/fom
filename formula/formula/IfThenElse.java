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
	 * MAURICE Wie kann ich den Typ eine Object abfragen?
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
		return null;
	}


	public final double getDoubleResult() {
		return 0;
	}

//	public final long getLongResult() {
//		return 0;
//	}

	public final boolean getBooleanResult() {
		return false;
	}

	public final Class[] getInputTypes(int in) {
		return new Class[0];
	}

	public final Class[] getOutputTypes() {
		return new Class[0];
	}

	public final boolean isValidInput(Formula in, int whichInput) {
		return false;
	}

	public final boolean isValidOutput(Formula in, int whichInput) {
		return false;
	}

}