/*
 * Created on 02.06.2004
 *
 */
package formula;

import java.util.*;
import utils.Messages;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Categories {

	private static String[] categories;

	private static HashMap categoryElements;

	private static Categories categoryObj=new Categories();

	/**
	 * Private constructor because there'll be only one category-list.
	 */
	private Categories() {
		categories=new String[Integer.parseInt(Messages.getString("Elements.Categories"))];
		categoryElements=new HashMap(16);
		for(int i=0;i<categories.length;i++) {
			categories[i]=Messages.getString("Elements.Category_"+Integer.toString(i));
		}
		
		//TODO Liste fertig machen, wenn mal alle Operatoren fertig sind.
		//Category Default:
		Formula[] form=new Formula[4];
		form[0]=new Add();
		form[1]=new Sub();
		form[2]=new Mult();
		form[3]=new Div();
		categoryElements.put(categories[0],form);
		
		//Category Constants:
		form=new Formula[0];
		
		
		//Category Trigonometrics:
		
		//Category Hyperbola:
		
		//Category Boolean:
		
		//Category Logical Operators:
		
		//Category Others:
		
		//Category Custom:
		
	}

	public static final String[] getCategories() {
		return categories;
	}

	public static final Formula[] getCategoryElements(String category) {
		return (Formula[])categoryElements.get(category);
	}

}
