/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		formulaName = "                     Constant";
		result = new Boolean(false);
		inputBoolean = new Button("false");
		if (elementChooser) {
			inputBoolean.enable(false);
		} else {
			inputBoolean.addActionListener(this);
		}
		inputBoolean.setBounds(3, RESULTHEIGHT+CONNECTHEIGHT+4, (FORMULAWIDTH)/2, BOXHEIGHT-6);
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

}