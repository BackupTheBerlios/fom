/*
 * Created on 05.04.2004
 *
 */
package formula;

/**
 * Exception for nearly everything that can happen within the formula-tree (calculating and creating).
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version 0.1.1 (21.04.2004)
 */

import Messages;

public class FormulaException extends Exception {
	
	public FormulaException() {
		super(Messages.getString("Error.Unknown"));
	}
	
	public FormulaException(String arg) {
		super(arg);
	}
	
}
