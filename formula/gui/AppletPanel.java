/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class AppletPanel extends Applet {

	private FormulaPanel pnlFormula;

	private ControlPanel pnlControls;

	private ElementPanel pnlElements;

	public void init() {
	}

	public FormulaPanel getPnlFormula() {
		return pnlFormula;
	}

	public void setPnlFormula(FormulaPanel pnlFormula) {
		this.pnlFormula = pnlFormula;
	}

	public ElementPanel getPnlElements() {
		return pnlElements;
	}

	public void setPnlElements(ElementPanel pnlElements) {
		this.pnlElements = pnlElements;
	}

	public ControlPanel getPnlControls() {
		return pnlControls;
	}

	public void setPnlControls(ControlPanel pnlControls) {
		this.pnlControls = pnlControls;
	}

	public AppletPanel() {
	}

}
