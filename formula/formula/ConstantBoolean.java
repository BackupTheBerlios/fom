/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for constant booleans.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantBoolean extends ConstVarFormula implements ActionListener {

	protected Button inputBoolean;

	/**
	 * Creates a constant boolean.
	 */
	public ConstantBoolean() {
		this(false);
	}

	/**
	 * Creates a constant boolean.
	 * @param elementChooser Disables inputBoolean for input.
	 */
	public ConstantBoolean(boolean elementChooser) {
		super();
		formulaName = "                    Constant";
		result = new Boolean(false);
		inputBoolean = new Button("false");
		inputBoolean.setFont(new Font("Arial", Font.PLAIN, 11));
		inputBoolean.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, FORMULAWIDHT/2, BOXHEIGHT-6);
		if (elementChooser) {
			inputBoolean.enable(false);
		} else {
			inputBoolean.addActionListener(this);
		}
		add(inputBoolean);		
	}

	/**
	 * Toggles result between true and false.
	 * @param arg import Method needs argument, but isn't used.
	 */
	public void actionPerformed(ActionEvent arg) {
		if (inputBoolean.getLabel() == "false") {
			inputBoolean.setLabel("true");
			result = new Boolean (true);
		} else {
			inputBoolean.setLabel("false");
			result = new Boolean (false);
		}
		repaint();
	}

	
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		inputBoolean.setVisible(vis);
	}
	
	public String getInputVarName() {
		return null;
	}

}