/*
 * Created on 26.05.2004
 */
package formula;

import utils.Messages;
import java.util.LinkedList;

/**
 * Abstract class for all constants and variables.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class ConstVarFormula extends Formula {

	protected Object result;

	//Contains all VariableBoolean and VariableNumber as well as their names and values
	protected static LinkedList varList = new LinkedList();

	/**
	 * Defines a Constant/Variable.
	 * Constants/Variables don't have any inputs.
	 */
	public ConstVarFormula() {
		super();
		input = new Formula[0];
	}

	/**
	 * No calculations need to be done for Constants/Variables
	 */
	public final void calc() throws FormulaException {		
	}

	public String toString() {
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

	public final boolean isResultCalculated() {
		return true;
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

	/**
	 * Searches in varList for a TypeConstVar.
	 * @param name TypeConstVar must have this name.
	 * @return TypeConstVar if found, null if not.
	 */
	private static final TypeConstVar getVarListName(String name) {
		TypeConstVar content;
		for (int i = 0; i < varList.size(); i++) {
			content = (TypeConstVar)varList.get(i);
			if (name != null) {
				if (name.equals(content.getName())) {
					return content;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Adds a VariableBoolean to varList.
	 * @param toAdd Which VariableBoolean has to be added.
	 */
	public static final void addVarList(VariableBoolean toAdd) {
		TypeConstVar content = getVarListName(toAdd.getInputVarName());
		TypeConstVar newVariable;
		if (content == null) {
			//Variablenname existiert noch nicht.
			newVariable = new TypeConstVar((Boolean)toAdd.result, toAdd.getInputVarName());
			newVariable.addVarInnerList(toAdd);
			varList.addFirst(newVariable);
		} else {
			if (content.getValue() instanceof Boolean) {
				toAdd.result = content.getValue();
				content.addVarInnerList(toAdd);
				toAdd.repaint();
			}
		}
	}

	/**
	 * Adds a VariableNumber to varList.
	 * @param toAdd Which VariableNumber has to be added.
	 */

	public static final void addVarList(VariableNumber toAdd) {
		TypeConstVar content = getVarListName(toAdd.getInputVarName());
		TypeConstVar newVariable;
		if (content == null) {
			//Variablenname existiert noch nicht.
			newVariable = new TypeConstVar((Double)toAdd.result, toAdd.getInputVarName());
			newVariable.addVarInnerList(toAdd);
			varList.addFirst(newVariable);
		} else {
			if (content.getValue() instanceof Number) {
				toAdd.result = content.getValue();
				content.addVarInnerList(toAdd);
				toAdd.repaint();
			}
		}		
	}

	/**
	 * Deletes a ConstVarFormula from varList.
	 * @param toDelete Which has to be deleted.
	 * @param name Variable name of toDelete.
	 */
	public static final void deleteVarList(ConstVarFormula toDelete, String name) {
		TypeConstVar content = getVarListName(name);
		if (content != null) {
			content.deleteVarInnerList(toDelete);
			if (content.sizeVarInnerList() == 0) {
				varList.remove(content);
			}
		}
	}

	/**
	 * Resolves content of text field and deletes it from varList.
	 * @param toDelete Which has to be deleted.
	 */
	public static final void deleteVarList(VariableBoolean toDelete) {
		deleteVarList(toDelete, toDelete.getInputVarName());
	}

	/**
	 * Resolves content of text field and deletes it from varList.
	 * @param toDelete Which has to be deleted.
	 */
	public static final void deleteVarList(VariableNumber toDelete) {
		deleteVarList(toDelete, toDelete.getInputVarName());
	}

	/**
	 * Deletes a whole Branch of ConstVarFormula from varList.
	 * @param toDelete Name of Variables.
	 */
	public static final void deleteVarBranch(String toDelete) {
		TypeConstVar content = getVarListName(toDelete);
		if (content != null) {
			varList.remove(content);
		}		
	}

	/**
	 * @return Returns contents of varList as an array.
	 */
	public static final TypeConstVar[] getVarList() {
		TypeConstVar[] resultArray = new TypeConstVar[varList.size()];
		for (int i=0; i < resultArray.length; i++) {
			resultArray[i] = (TypeConstVar)varList.get(i);
		}
		return resultArray;
	}

	/**
	 * Changes all Variable with a specific name to another.
	 * @param oldName Changes from this name.
	 * @param newName Changes to this name.
	 */
	public static final void setVarNameAll(String oldName, String newName) {
		TypeConstVar content = getVarListName(oldName);
		if (content != null) {
			content.setName(newName);
		}
	}

	/**
	 * Changes all Variables' value with a specific name.
	 * @param name Changes Variables with this name
	 * @param value Changes their value to new one.
	 */
	public static final void setVarValueAll(String name, Object value) {
		TypeConstVar content = getVarListName(name);
		if (content != null) {
			content.setValue(value);
		}
	}

	/**
	 * Validates a variable name. Valid names have at least one non-space character and
	 * don't use an already existing name.
	 * @param isValid To be checked.
	 * @return Returns, if variable name is valid.
	 */
	protected static final boolean isValidName(String isValid, Object value) {
		boolean valid = true;
		TypeConstVar content;
		//name must have at least one non-whitespace character.
// TODO Auch noch ne auskommentierte Kleinigkeit		
		/*if (isValid.matches(" *")) {	// matches benötigt Java 1.4
			valid = false;
		}*/
		if (isValid.trim().equals("")) {
			valid = false;
		}
		//Double names of wrong data type is not valid.
		for (int i=0; i < varList.size() && valid; i++) {
			content = (TypeConstVar)(varList.get(i));
			if ((isValid.equals(content.getName())) && (! value.getClass().equals(content.getValue().getClass()))) {
				valid = false;
			}
		}
		return valid;
	}


	public abstract String getInputVarName();
}