/*
 * Created on 26.05.2004
 */
package formula;

import utils.Messages;
/**
 * Abstract Class for all Constants and Variables
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class ConstVarFormula extends Formula {

	//TODO Variablen interaktiv gestallten

	protected Object result;

	public ConstVarFormula() {
		super();
		input = new Formula[0];
	}

	/**
	 * No calculations need to be done for Constants/Variables
	 */
	public final void calc() throws FormulaException {		
	}

	public final String toString() {
		return result.toString();
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

//	public long getLongResult() {
//	 return 0;
//	}
	
	/**
	 * Overwrites existing Method, because Constants/Variables aren't calculated
	 */
	public final void clearResult() {
	}
	

	public final Class[] getInputTypes(int index) throws FormulaException {
		throw new FormulaException(Messages.getString("Error.ConstVarInput"));
	}

	public final Class[] getOutputTypes() throws FormulaException {
		Class[] classArray;
		classArray = new Class[1];
		if (result instanceof Boolean)
			classArray[0] = Boolean.class;
		else if (result instanceof Number)
			classArray[0] = Number.class;
		else
			throw new FormulaException(Messages.getString("Error.IllegalDataType"));
		return classArray;
	}

}