/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.lang.Object;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public abstract class MixedFormula extends Formula {

	protected Object result;

	public abstract Object calc() throws FormulaException;

	public final void clearResult() {
		result = null;
	}

}