/*
 * Created on 26.08.2004
 */
package formula;

import utils.Messages;

/** TODO JavaDoc ergänzen
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class CustomFormula extends Formula {

	protected Object result;

	protected Formula root;
	protected VariableList variables;
	protected Class[] validInputTypes;

	public CustomFormula(String name, Formula root, VariableList variables) throws FormulaException {
		super();
		if ((root == null) || ! root.isCompleteSubTree()) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else {
			this.variables = variables;
			this.root = root;
			input = new Formula[variables.size()];
			formulaName = name;
		}
	}

	private final void mapInputsOnVariables() throws FormulaException {
		TypeConstVar variable;
		for (int i=0; i < getInputCount(); i++) {
			if (getInput(i) == null) {
				throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
			} else {
				variable = (TypeConstVar)variables.get(i);
				if (getInput(i).hasBooleanResult()) {
					variable.setValue(new Boolean(getInput(i).getBooleanResult()));
				} else if (getInput(i).hasDoubleResult()) {
					variable.setValue(new Double(getInput(i).getDoubleResult()));
				} else {
					throw new FormulaException(Messages.getString("Error.IllegalDataType"));
				}
			}
		}
		
	}

	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public final void clearResult() {
		result = null;
		clearResultTree(root);
	}

	private final void clearResultTree(Formula root) {
		root.clearResult();
		for (int i=0; i < root.getInputCount(); i++) {
			if (root.getInput(i) != null) {
				clearResultTree(root.getInput(i));
			}
		}
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

	public boolean hasBooleanResult() {
		return (result != null) && (result instanceof Boolean);
	}

	public boolean hasDoubleResult() {
		return (result != null) && (result instanceof Double);
	}

	public final void calc() throws FormulaException {
		mapInputsOnVariables();
		root.calc();
		if (root.hasBooleanResult()) {
			result = new Boolean(root.getBooleanResult());
		} else if (root.hasDoubleResult()) {
			result = new Double(root.getDoubleResult());
		} else {
			throw new FormulaException(Messages.getString("Error.IllegalDataType"));
		}
	}

	public final String toString() {
		String outString = formulaName + "(";
		for (int i=0; i < variables.size(); i++) {
			outString += getInput(i).toString();
			if (i+1 != variables.size()) {
				outString += ",";
			}
		}
		return outString + ")";
	}

	public final Class[] getInputTypes(int index) throws FormulaException {
		TypeConstVar variable = (TypeConstVar)variables.get(index);
		return new Class[] {variable.getValue().getClass()};
	}

	public final Class[] getOutputTypes() throws FormulaException {
		return root.getOutputTypes();
	}

}
