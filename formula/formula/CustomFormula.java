/* $Id: CustomFormula.java,v 1.6 2004/09/07 13:40:00 shadowice Exp $
 * Created on 26.08.2004
 */
 
package formula;

import java.util.*;

import utils.*;

/**
 * CustomFormula is a container class for a formula tree. Its inputs are the variables in the tree.
 * If there are no variables, the CustomFormula won't have any inputs.  
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.6 $
 */
public class CustomFormula extends Formula {

	private Formula root;
	private VariableList variables;
	private Class[] validInputTypes;

	private Object result;

	private Vector treeList;


	/**
	 * Creates a new CustomFormula that is ready to use.
	 *
	 * @param name name of the CustomFormula
	 * @param root root Formula of the tree
	 * @param variables list of variables
	 * @throws FormulaException is thrown if the tree is incomplete
	 */
	public CustomFormula(String name, Formula root, VariableList variables) throws FormulaException {
		super();
		if (root != null) {
			if (!root.isCompleteSubTree()) {
				throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
			}
		}
		this.formulaName = name;
		this.variables = variables;
		this.input = new Formula[variables.size()];
		this.root = root;
		createTreeList();
	}


	/**
	 * Creates a new CustomFormula without a name.
	 *
	 * @param root root Formula of the tree
	 * @param variables list of variables
	 * @throws FormulaException is thrown if the tree is incomplete
	 */
	public CustomFormula(Formula root, VariableList variables) throws FormulaException {
		this("",root,variables);
	}


	/**
	 * Creates an empty CustomFormula. This object won't work until it get's a root and a list of variables (can be empty).
	 */
	public CustomFormula() {
		super();
	}


	/**
	 * Maps the input values to the variables. 
	 * @throws FormulaException Thrown if the input is null or the data type is unknown.
	 */
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
		for (int i=0;i<treeList.size();i++) {
			((Formula)treeList.elementAt(i)).clearResult();
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
		return result.toString();
	}


	public boolean hasBooleanResult() {
		return (result != null) && (result instanceof Boolean);
	}


	public boolean hasDoubleResult() {
		return (result != null) && (result instanceof Double);
	}


	public final void calc() throws FormulaException {
		mapInputsOnVariables();
		clearResult();

		for (int i=treeList.size()-1;i>=0;i--) {
			((Formula)treeList.elementAt(i)).calc();
		}
		
		if (root.hasDoubleResult()) {
			result = new Double(root.getDoubleResult());
		} else if (root.hasBooleanResult()) {
			result = new Boolean(root.getBooleanResult());
		} else {
			result = null;
			throw new FormulaException(Messages.getString("Error.IllegalDataType"));
		}
	}


	public final String toString() {
		String outString = formulaName + "(";
		for (int i=0; i < variables.size(); i++) {
			outString += getInput(i).toString();
			if (i+1 != variables.size()) {
				outString += "; ";
			}
		}
		return outString + ")";
	}


	public final Class[] getInputTypes(int index) throws FormulaException {
		TypeConstVar variable = (TypeConstVar)variables.get(index);
		if (variable.getValue() instanceof Double) {
			return new Class[]{Number.class};
		} else if (variable.getValue() instanceof Boolean) {
			return new Class[]{Boolean.class};
		} else {
			throw new FormulaException(Messages.getString("Error.Unknown"));
		}
	}


	public final Class[] getOutputTypes() throws FormulaException {
		return root.getOutputTypes();
	}


	public void setRoot(Formula treeRoot) {
		this.root = treeRoot;
		createTreeList();
	}


	public Formula getRoot() {
		return this.root;
	}


	/**
	 * Sets the variable list of a CustomFormula and creates a new array for inputs.
	 * @param varList
	 */
	public void setVariables(VariableList varList) {
		this.variables = varList;
		input = new Formula[variables.size()];
	}


	public VariableList getVariables() {
		return this.variables;
	}


	public final int getInputCount() {
		return variables.size();
	}


	public Object clone() {
		CustomFormula clonedCF = (CustomFormula)super.clone();
		Formula[] inputBackup = clonedCF.input;
		clonedCF.setVariables(variables);	// no clone of variables
		clonedCF.setRoot(root);					// no clone of the tree
		clonedCF.result = null;
		clonedCF.input = inputBackup;
		return clonedCF;
	}


	/**
	 * Creates a Vector containing all formula elements in the tree.
	 */
	private void createTreeList() {
		treeList = new Vector();
		if (root != null) {
			createTreeListRec(root);
		}
	}


	/**
	 * Recursive function to add all elements from a formula tree into a Vector.
	 * @param form formula to add
	 */
	private void createTreeListRec(Formula form) {
		treeList.addElement(form);
		for (int i=0;i<form.getInputCount();i++) {
			createTreeListRec(form.getInput(i));
		}
	}

}
