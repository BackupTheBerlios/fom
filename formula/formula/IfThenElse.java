/*
 * Created on 15.04.2004
 */
package formula;

import utils.Messages;

/**
 * This class provides an element that returns a number/boolean dependent from a condition. 
 * f(a,x,y) = x for a is true, y for a is false
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class IfThenElse extends MixedFormula {

	/**
	 * Creates an "if-then-else" with 3 inputs.
	 */
	public IfThenElse() {
		super();
		formulaName = "if-then-else";
		input = new Formula[3];
	}

	public final void calc() throws FormulaException {
		if ((input[0] == null) || (input[1] == null) || (input[2] == null)) {
			throw new FormulaException(Messages.getString("Error.IncompleteFormula"));
		} else if (getOutputTypes()[0] == Boolean.class) {
			if (input[0].getBooleanResult())
				result = new Boolean (input[1].getBooleanResult());
			else
				result = new Boolean (input[2].getBooleanResult());
		} else {
			if (input[0].getBooleanResult()) {
				result = new Double (input[1].getDoubleResult());
			} else {
				result = new Double (input[2].getDoubleResult());
			}
		}
		repaint();
	}

	public final String toString() {
		String outString = "(If(";
		if (input[0] != null)
			outString += input[0].toString();
		outString += ")then(";
		if (input[1] != null)
			outString += input[1].toString();
		outString += ")else(";
		if (input[2] != null)
			outString += input[1].toString();
		outString += ")";
		return outString;
	}

	public final Class[] getInputTypes(int index) throws FormulaException {
		switch (index) {
			case 0: 
				return new Class[] {Boolean.class};
			case 1:
				if (input[2] == null && output == null) {
					return new Class[] {Boolean.class, Number.class};
				} else if (input[2] != null) {
					return input[2].getOutputTypes();
				} else {
					return output.getInputTypes(indexOfInput(this, output));
				}
			case 2:
				if (input[1] == null && output == null) {
					return new Class[] {Boolean.class, Number.class};	
				} else if (input[1] != null) {
					return input[1].getOutputTypes();
				} else {
					return output.getInputTypes(indexOfInput(this, output));
				}
			default:
				throw new FormulaException(Messages.getString("Error.OutOfBounds"));  
		}
	}


	public final Class[] getOutputTypes() throws FormulaException {
		if (input[1] == null && input[2] == null) {
			return new Class[]{Boolean.class,Number.class};
		} else if (input[1] != null) {
			return input[1].getOutputTypes();
		} else {
			return input[2].getOutputTypes();
		}
	}

// TODO Hier kann wohl was gelöscht werden?!
//	public static void main(String[] args){
//		IfThenElse ite=new IfThenElse();
//		ite.setInput(ite,0);
//		try{
//			Class[] itypes=ite.getInputTypes(1);
//			for(int i=0;i<itypes.length;i++){
//				System.out.println(itypes[i].getName());
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

}