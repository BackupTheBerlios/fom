/* $Id: TypeConstVar.java,v 1.7 2004/09/07 13:40:00 shadowice Exp $
 * Created on 17.07.2004
 */
package formula;

import java.util.*;

/**
 * This class is used to store all informations regarding variables from ConstVarFormula type objects.
 * These informations are:
 * <li>a name</li>
 * <li>a value belonging to this name</li>
 * <li>a list of ConstVarFormula objects that use this name</li>
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.7 $
 * @see VariableBoolean
 * @see VariableNumber
 */
public class TypeConstVar {

	private Object value;
	private String name;
	private Vector varInnerList;


	/**
	 * Creates a new TypeConstVar object with an empty list.
	 * 
	 * @param name name of this variable
	 * @param value value of this variable
	 */
	public TypeConstVar(String name, Object value) {
		this.value = value;
		this.name = name;
		varInnerList = new Vector();
	}


	/**
	 * @return returns the value of this variable
	 */
	public final Object getValue() {
		return value;
	}

	/**
	 * @param value Changes the value of all contents of varInnerList to value.
	 */
	public final void setValue(Object value) {
		ConstVarFormula content;
		this.value = value;
		for (int i = 0; i < varInnerList.size(); i++) {
			content = (ConstVarFormula)varInnerList.get(i);
			content.result = value;
			content.repaint();
		}
	}


	/**
	 * @return returns the name of this variable
	 */
	public final String getName() {
		return name;
	}


	/**
	 * @param name Changes the name of all contents of varInnerList to name.
	 */
	public final void setName(String name) {
		VariableBoolean bool;
		VariableNumber numb;
		Object content;
		this.name = name;
		for (int i = 0; i < varInnerList.size(); i++) {
			content = varInnerList.get(i);
			if (content instanceof VariableBoolean) {
				bool = (VariableBoolean)content;
				bool.setInputVarName(name);
				bool.repaint();
			} else if (content instanceof VariableNumber) { 
				numb = (VariableNumber)content;
				numb.setInputVarName(name);
				numb.repaint();
			}
		}
	}


	/**
	 * @param toAdd Adds toAdd to list if list doesn't already contains it. 
	 */
	public final void addVarInnerList(ConstVarFormula toAdd) {
		if (! varInnerList.contains(toAdd)) {
			varInnerList.addElement(toAdd);
		}
	}


	/**
	 * @param toDelete Deletes toDelete from this list.
	 */
	public final void deleteVarInnerList(ConstVarFormula toDelete) {
		varInnerList.removeElement(toDelete);
	}


	/**
	 * @param contains To be checked.
	 * @return return, if contains is in varInnerList.
	 */
	public final boolean containsVarInnerList(ConstVarFormula contains) {
		return varInnerList.contains(contains);
	}


	/**
	 * @return returns size of varInnerList.
	 */
	public final int sizeVarInnerList() {
		return varInnerList.size();
	}


	/**
	 * @return returns varInnerList.
	 */
	public final Vector getVarInnerList() {
		return varInnerList;
	}

}