/*
 * Created on 02.06.2004
 *
 */
package formula;

//import gui.DragnDropListener;
import java.util.*;
//import java.awt.event.*;
import gui.*;
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
		Formula[] form = new Formula[8];
		form[0] = new Add();
		form[1] = new Sub();
		form[2] = new Mult();
		form[3] = new Div();
		form[4] = new ConstantNumber();
		form[5] = new Sign();
		form[6] = new Square();
		form[7] = new Reciprocal();
		categoryElements.put(categories[0], form);

		//Category Variables:
		form = new Formula[6];
		form[0] = new ConstantNumber();
		form[1] = new ConstantBoolean();
		form[2] = new VariableNumber();
		form[3] = new VariableBoolean();
		form[4] = new ConstE();
		form[5] = new ConstPi();
		categoryElements.put(categories[1], form);

		//Category Arithmetics
		form = new Formula[10];
		form[0] = new Sqrt();
		form[1] = new Pow();
		form[2] = new Ln();
		form[3] = new LogTen();
		form[4] = new LogBase();
		form[5] = new Euler();
		form[6] = new Factorial();
		form[7] = new Modulo();
		form[8] = new Abs();
		form[9] = new Exp();				
		categoryElements.put(categories[2], form);

		//Category Trigonometrics:
		form = new Formula[7];
		form[0] = new Sin();
		form[1] = new Cos();
		form[2] = new Tan();
		form[3] = new Cot();
		form[4] = new Arcsin();
		form[5] = new Arccos();
		form[6] = new Arctan();
		categoryElements.put(categories[3], form);

		//Category Hyperbola:
		form = new Formula[4];
		form[0] = new Sinh();
		form[1] = new Cosh();
		form[2] = new Tanh();
		form[3] = new Coth();
		categoryElements.put(categories[4], form);

		//Category Comparison:
		form = new Formula[8];
		form[0] = new Equal();
		form[1] = new Unequal();
		form[2] = new Less();
		form[3] = new LessEqual();
		form[4] = new Greater();
		form[5] = new GreaterEqual();		
		form[6] = new Even();
		form[7] = new Odd();		
		categoryElements.put(categories[5], form);

		//Category Logical Operators:
		form = new Formula[6];
		form[0] = new Not();
		form[1] = new And();
		form[2] = new Or();
		form[3] = new Xor();
		form[4] = new Eqv();
		form[5] = new Impl();		
		categoryElements.put(categories[6], form);

		//Category Others:
		form = new Formula[10];
		form[0] = new IfThenElse();
		form[1] = new Min();
		form[2] = new Max();
		form[3] = new Random();
		form[4] = new Deg2grad();
		form[5] = new Deg2rad();
		form[6] = new Grad2deg();
		form[7] = new Grad2rad();
		form[8] = new Rad2deg();
		form[9] = new Rad2grad();	
		categoryElements.put(categories[7], form);


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
	
	public static void initMouseListener(AppletPanel ap) {
/*		for(int i=0;i<categories.length;i++) {
			Formula[] form = (Formula[])categoryElements.get(categories[i]);
			if (form!=null) {
				for(int j=0;j<form.length;j++) {
					DragnDropListener dnd = new DragnDropListener(ap);
					form[j].addMouseListener(dnd);
					form[j].addMouseMotionListener(dnd);
				}
			}
		}*/
	}
}
