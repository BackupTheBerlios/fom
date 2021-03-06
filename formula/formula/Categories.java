/*
 * Created on 02.06.2004
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

	private String[]		categories;
	private Hashtable		categoryElements;

	
	/**
	 * Private constructor because there'll be only one category-list.
	 */
	public Categories() {
		categories = new String[Integer.parseInt(Messages.getString("Elements.Categories"))];
		categoryElements = new Hashtable(16);
		for(int i=0;i<categories.length;i++) {
			categories[i] = Messages.getString("Elements.Category_"+Integer.toString(i));
		}

		//Category Default:
		Formula[] form = new Formula[8];
		form[0] = new Add();
		form[1] = new Sub();
		form[2] = new Mult();
		form[3] = new Div();
		form[4] = new ConstantNumber(false);
		form[5] = new Sign();
		form[6] = new Square();
		form[7] = new Reciprocal();
		categoryElements.put(categories[0], form);

		//Category Variables:
		form = new Formula[6];
		form[0] = new ConstantNumber(false);
		form[1] = new ConstantBoolean(false);
		form[2] = new VariableNumber(false);
		form[3] = new VariableBoolean(false);
		form[4] = new ConstE();
		form[5] = new ConstPi();
		categoryElements.put(categories[1], form);

		//Category Arithmetics
		form = new Formula[11];
		form[0] = new Sqrt();
		form[1] = new Pow();
		form[2] = new Ln();
		form[3] = new LogTen();
		form[4] = new Ld();
		form[5] = new LogBase();
		form[6] = new Euler();
		form[7] = new Factorial();
		form[8] = new Modulo();
		form[9] = new Abs();
		form[10] = new Exp();				
		categoryElements.put(categories[2], form);

		//Category Trigonometrics:
		form = new Formula[17];
		form[0] = new Sin();
		form[1] = new Cos();
		form[2] = new Tan();
		form[3] = new Cot();
		form[4] = new Arcsin();
		form[5] = new Arccos();
		form[6] = new Arctan();
		form[7] = new Sinh();
		form[8] = new Cosh();
		form[9] = new Tanh();
		form[10] = new Coth();
		form[11] = new Deg2grad();
		form[12] = new Deg2rad();
		form[13] = new Grad2deg();
		form[14] = new Grad2rad();
		form[15] = new Rad2deg();
		form[16] = new Rad2grad();	
		categoryElements.put(categories[3], form);

		//Category Comparison:
		form = new Formula[9];
		form[0] = new Equal();
		form[1] = new Unequal();
		form[2] = new Less();
		form[3] = new LessEqual();
		form[4] = new Greater();
		form[5] = new GreaterEqual();		
		form[6] = new Even();
		form[7] = new Odd();
		form[8] = new Eqv();
		categoryElements.put(categories[4], form);

		//Category Logical Operators:
		form = new Formula[8];
		form[0] = new Not();
		form[1] = new And();
		form[2] = new Or();
		form[3] = new Xor();
		form[4] = new Eqv();
		form[5] = new Impl();
		form[6] = new ConstantBoolean(false);
		form[7] = new VariableBoolean(false);
		categoryElements.put(categories[5], form);

		//Category Others:
		form = new Formula[4];
		form[0] = new IfThenElse();
		form[1] = new Min();
		form[2] = new Max();
		form[3] = new Random();
		categoryElements.put(categories[6], form);

		//Category Custom:
		form = new Formula[0];
		
		//Category All:
		form = new Formula[60];
		form[0] = new Add();
		form[1] = new Sub();
		form[2] = new Mult();
		form[3] = new Div();
		form[4] = new Sign();
		form[5] = new Square();
		form[6] = new Reciprocal();
		form[7] = new ConstantNumber(false);
		form[8] = new ConstantBoolean(false);
		form[9] = new VariableNumber(false);
		form[10] = new VariableBoolean(false);
		form[11] = new ConstE();
		form[12] = new ConstPi();
		form[13] = new Sqrt();
		form[14] = new Pow();
		form[15] = new Ln();
		form[16] = new LogTen();
		form[17] = new Ld();
		form[18] = new LogBase();
		form[19] = new Euler();
		form[20] = new Factorial();
		form[21] = new Modulo();
		form[22] = new Abs();
		form[23] = new Exp();				
		form[24] = new Sin();
		form[25] = new Cos();
		form[26] = new Tan();
		form[27] = new Cot();
		form[28] = new Arcsin();
		form[29] = new Arccos();
		form[30] = new Arctan();
		form[31] = new Sinh();
		form[32] = new Cosh();
		form[33] = new Tanh();
		form[34] = new Coth();
		form[35] = new Deg2grad();
		form[36] = new Deg2rad();
		form[37] = new Grad2deg();
		form[38] = new Grad2rad();
		form[39] = new Rad2deg();
		form[40] = new Rad2grad();	
		form[41] = new Equal();
		form[42] = new Unequal();
		form[43] = new Less();
		form[44] = new LessEqual();
		form[45] = new Greater();
		form[46] = new GreaterEqual();		
		form[47] = new Even();
		form[48] = new Odd();
		form[49] = new Eqv();
		form[50] = new Not();
		form[51] = new And();
		form[52] = new Or();
		form[53] = new Xor();
		form[54] = new Eqv();
		form[55] = new Impl();
		form[56] = new IfThenElse();
		form[57] = new Min();
		form[58] = new Max();
		form[59] = new Random();

		categoryElements.put(categories[8], form);
	}


	/**
	 * @return all available categories
	 */
	public final String[] getCategories() {
		return categories;
	}
	

	/**
	 * @param category The category you want elements from.
	 * @return an array of all Formula-elements of the given category
	 */
	public final Formula[] getCategoryElements(String category) {
		return (Formula[])categoryElements.get(category);
	}
	

	/**
	 * Adds a Formula-object to a category.
	 * @param category the category to add the formula to
	 * @param formula formula to add
	 */
	public final void addCategoryElement(String category, Formula formula) {
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
