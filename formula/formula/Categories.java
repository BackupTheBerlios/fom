/*
 * Created on 02.06.2004
 *
 */
package formula;

import java.util.*;
import utils.Messages;
/**
 * The class Categories is used to store the different categories of formula-elements
 * and the elements in each category.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class Categories {

	private static String[]		categories;
	private static HashMap		categoryElements;
	private static Categories	categoryObj			= new Categories();

	/**
	 * Private constructor because there'll be only one category-list.
	 */
	private Categories() {
		categories = new String[Integer.parseInt(Messages.getString("Elements.Categories"))];
		categoryElements = new HashMap(16);
		for(int i=0;i<categories.length;i++) {
			categories[i] = Messages.getString("Elements.Category_"+Integer.toString(i));
		}
		
		//TODO Liste fertig machen, wenn mal alle Operatoren fertig sind.
		//Category Default:
		Formula[] form = new Formula[4];
		form[0] = new Add();
		form[1] = new Sub();
		form[2] = new Mult();
		form[3] = new Div();
		categoryElements.put(categories[0],form);
		
		//Category Constants:
		form = new Formula[0];
		
		
		//Category Trigonometrics:
		form = new Formula[0];
		
		//Category Hyperbola:
		form = new Formula[0];
		
		//Category Boolean:
		form = new Formula[0];
		
		//Category Logical Operators:
		form = new Formula[0];
		
		//Category Others:
		form = new Formula[0];
		
		//Category Custom:
		form = new Formula[0];
		
	}

	/**
	 * @return all available categories
	 */
	public static final String[] getCategories() {
		return categories;
	}

	/**
	 * @param category The category you want elements from.
	 * @return an array of all Formula-elements of the given category
	 */
	public static final Formula[] getCategoryElements(String category) {
		return (Formula[])categoryElements.get(category);
	}

	/**
	 * Adds a Formula-object to a category.
	 * @param category
	 * @param formula
	 */
	public static final void addCategoryElement(String category, Formula formula) {
		Formula[] form		 = getCategoryElements(category);
		Formula[] newForm;
		if (form != null) {
			newForm = new Formula[form.length+1];
			for (int i=0;i<form.length;i++)
				newForm[i] = form[i];
		} else {
			newForm = new Formula[1];
		}
		newForm[newForm.length-1] = formula;
		categoryElements.put(category,newForm);
	}

}
