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
	private Label lblSpeed;

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
		btnCalcAll	= new Button(Messages.getString("ControlPanel.BtnCalcAll"));
		btnCalcAni	= new Button(Messages.getString("ControlPanel.BtnCalcAni"));
		btnCalcStep	= new Button(Messages.getString("ControlPanel.BtnCalcStep"));
		btnReset	= new Button(Messages.getString("ControlPanel.BtnReset"));
		btnVariables= new Button(Messages.getString("ControlPanel.BtnVariables"));
		lblSpeed	= new Label(Messages.getString("ControlPanel.LblSpeed"));
		lblFormula	= new Label();
		sbSpeed		= new Scrollbar(Scrollbar.HORIZONTAL, 50, 10, 0, 100);
		chZoom		= new Choice();
		chZoom.add("25%");
		chZoom.add("50%");
		chZoom.add("75%");
		chZoom.add("100%");
		chZoom.add("150%");
		chZoom.add("200%");
		
		GridBagLayout gridbag=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gridbag);
		// lblFormula:
		gbc.fill=GridBagConstraints.BOTH;
		gbc.gridheight=2;
		gridbag.setConstraints(lblFormula,gbc);
		add(lblFormula);
		// btnReset, btnCalcAll, btnCalcAni, btnCalcStep:
		gbc.gridheight=1;
		gridbag.setConstraints(btnReset,gbc);
		add(btnReset);
		gridbag.setConstraints(btnCalcAll,gbc);
		add(btnCalcAll);
		gridbag.setConstraints(btnCalcAni,gbc);
		add(btnCalcAni);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(btnCalcStep,gbc);
		add(btnCalcStep);
		gbc.weightx=0.0;
		
	}

}
