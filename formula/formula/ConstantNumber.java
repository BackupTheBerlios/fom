/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.TextField;

/**
 * Class for constant numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantNumber extends ConstVarFormula {

//	TODO Mit Maurice besprechen

	protected TextField inputNumber;

	public void setResult(Number result) {
		this.result = result;
	}

	public final String toString() {
		return result.toString();
	}

	public ConstantNumber() {
		formulaName = "ConstantNumber";
		result = new Double (0);
		inputNumber = new TextField();
//		inputNumber.setBounds(2, RESULTHEIGHT+CONNECTHEIGHT+2, FORMULAWIDTH-2, FORMULAHEIGHT-CONNECTHEIGHT-2);
//		inputNumber.setLocation(2, RESULTHEIGHT+CONNECTHEIGHT+2);
	}

}