/*
 * Created on 17.07.2004
 */
package formula;

import java.util.LinkedList;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class TypeConstVar {

	private Object value;
	private String name;
	private LinkedList varInnerList;


	//MAURIEC Wird das hier eigentlich benötigt?
	public TypeConstVar() {
		super();
		value = new Object();
		name = new String();
		varInnerList = new LinkedList();
	}

	public Object getValue() {
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
			varInnerList.add(toAdd);
		}
	}

	/**
	 * @param toDelete Deletes toDelete from this list.
	 */
	public final void deleteVarInnerList(ConstVarFormula toDelete) {
		varInnerList.remove(toDelete);
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
	 * @return returns varInnerList as an array.
	 */
	public final ConstVarFormula[] getVarInnerList() {
		return (ConstVarFormula[])varInnerList.toArray();
	}


}
