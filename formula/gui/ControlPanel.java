/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.Label;
import java.awt.*;

import Messages;

/**
 * The ControlPanel contains all buttons and other elements to control,
 * how the formula-tree is calculated or what to do with it.
 *
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

	public ControlPanel() {
		btnCalcAll=new Button(Messages.getString("BtnCalcAll"));
	}

}
