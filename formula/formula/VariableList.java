/* $Id: VariableList.java,v 1.5 2004/09/10 15:38:19 shadowice Exp $
 * Created on 24.08.2004
 */
package formula;

import java.util.*;

/**
 * Class to store a list of variables.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.5 $
 */
public class VariableList extends Vector {

	/**
	 * Creates a new empty variable list. 
	 */
	public VariableList() {
		super();
	}


	/**
	 * Searches in this VariableList for a TypeConstVar with a certain variable name.
	 * 
	 * @param name TypeConstVar must have this name.
	 * @return TypeConstVar if found, null if not.
	 */
	private final TypeConstVar getVarListName(String name) {
		TypeConstVar content;
		for (int i = 0; i < size(); i++) {
			content = (TypeConstVar)elementAt(i);
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
	 * 
	 * @param toAdd Which VariableBoolean has to be added.
	 */
	public final void addVarList(VariableBoolean toAdd) {
		TypeConstVar content = getVarListName(toAdd.getInputVarName());
		TypeConstVar newVariable;
		if (content == null) {
			//Variablenname existiert noch nicht.
			newVariable = new TypeConstVar(toAdd.getInputVarName(), (Boolean)toAdd.result);
			newVariable.addVarInnerList(toAdd);
			addElement(newVariable);
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
	 * 
	 * @param toAdd Which VariableNumber has to be added.
	 */
	public final void addVarList(VariableNumber toAdd) {
		TypeConstVar content = getVarListName(toAdd.getInputVarName());
		if (content == null) {
			//Variablenname existiert noch nicht.
			content = new TypeConstVar(toAdd.getInputVarName(), (Double)toAdd.result);
			content.addVarInnerList(toAdd);
			addElement(content);
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
	public final void deleteVarList(ConstVarFormula toDelete, String name) {
		TypeConstVar content = getVarListName(name);
		if (content != null) {
			content.deleteVarInnerList(toDelete);
			if (content.sizeVarInnerList() == 0) {
				removeElement(content);
			}
		}
	}


	/**
	 * Resolves content of text field and deletes it from varList.
	 * @param toDelete Which has to be deleted.
	 */
	public final void deleteVarList(VariableBoolean toDelete) {
		deleteVarList(toDelete, toDelete.getInputVarName());
	}


	/**
	 * Resolves content of text field and deletes it from varList.
	 * @param toDelete Which has to be deleted.
	 */
	public final void deleteVarList(VariableNumber toDelete) {
		deleteVarList(toDelete, toDelete.getInputVarName());
	}


	/**
	 * Deletes a whole Branch of ConstVarFormula from varList.
	 * 
	 * @param toDelete Name of Variables.
	 */
	public final void deleteVarBranch(String toDelete) {
		TypeConstVar content = getVarListName(toDelete);
		if (content != null) {
			removeElement(content);
		}		
	}


	/**
	 * @return Returns contents of VariableList as an array.
	 */
	public final TypeConstVar[] toVarArray() {
		TypeConstVar[] resultArray = new TypeConstVar[size()];
		for (int i=0; i < resultArray.length; i++) {
			resultArray[i] = (TypeConstVar)elementAt(i);
		}
		return resultArray;
	}


	/**
	 * Changes all Variable with a specific name to another.
	 * @param oldName Changes from this name.
	 * @param newName Changes to this name.
	 */
	public final void setVarNameAll(String oldName, String newName) {
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
	public final void setVarValueAll(String name, Object value) {
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
	protected final boolean isValidName(String isValid, Object value) {
		boolean valid = true;
		TypeConstVar content;
		//name must have at least one non-whitespace character.
		if (isValid.trim().equals("")) {
			valid = false;
		}
		//Double names of wrong data type is not valid.
		for (int i = 0; i < size() && valid; i++) {
			content = (TypeConstVar)(elementAt(i));
			if ((isValid.equals(content.getName())) && (!value.getClass().equals(content.getValue().getClass()))) {
				valid = false;
			}
		}
		return valid;
	}
	

	/**
	 * BubbleSort to sort this vector.
	 */
	public void sort() {
		int a=0;
		TypeConstVar var_a,var_b;
		while (a < size()) {
			int b = a + 1;
			while (b < size()) {
				var_a = (TypeConstVar)elementAt(a);
				var_b = (TypeConstVar)elementAt(b);
				if (var_a.getName().compareTo(var_b.getName()) > 0) {
					setElementAt(var_a,b);
					setElementAt(var_b,a);					
				}
				b++;
			}
			a++;
		}
	}

}
