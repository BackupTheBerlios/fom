/*
 * Created on 07.04.2004
 *
 */
package formula;

//import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 07.04.2004
 *
 */
public class Add extends NumberFormula {

	public final Number calc() throws FormulaException {
		return null;
	}

	public String toString() {
		return "("+input[0].toString()+"+"+input[1].toString()+")";
	}

	public Add() {
		input=new Formula[2];
		formulaName="+";
	}
	
/*	public static void main(String[] args){
 *		Frame f=new Frame();
 *		f.setSize(300,200);
 *		f.setLayout(new FlowLayout());
 *		Add a=new Add();
 *		f.add(a);
 *		f.setVisible(true);
 *	}
 */
}
