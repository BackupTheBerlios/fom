/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.Label;
import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class ControlPanel extends Panel {

	private Button btnReset;

	private Button btnCalcAni;

	private Button btnCalcStep;

	private Button btnVariables;

	private Button btnCalcAll;

	private Scrollbar sbSpeed;

	private Label lblFormula;

	private Choice chZoom;

	public Label getLblFormula() {
		return lblFormula;
	}

	public void setLblFormula(Label lblFormula) {
		this.lblFormula = lblFormula;
	}

	public Choice getChZoom() {
		return chZoom;
	}

	public void setChZoom(Choice chZoom) {
		this.chZoom = chZoom;
	}

}
