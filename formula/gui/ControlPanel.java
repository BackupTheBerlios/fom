/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.Label;
import java.awt.*;

import utils.Messages;

/**
 * The ControlPanel contains all buttons and other elements to control,
 * how the formula-tree is calculated or what to do with it.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class ControlPanel extends Panel {

	private Button btnReset;
	private Button btnCalcAni;
	private Button btnCalcStep;
	private Button btnVariables;
	private Button btnCalcAll;
	private Scrollbar sbSpeed;
	private FormulaTextField tfFormula;
	private Label lblSpeed;
	//private Choice chZoom;
	private AppletPanel aPanel;

	private static final int SPEED_MULTIPLIER = 20;

	/**
	 * Creates the control panel.
	 */
	public ControlPanel(AppletPanel ap) {
		this.aPanel 		= ap;
		//creating all objects:
		btnCalcAll			= new Button(Messages.getString("ControlPanel.BtnCalcAll"));
		btnCalcAni			= new Button(Messages.getString("ControlPanel.BtnCalcAni"));
		btnCalcStep			= new Button(Messages.getString("ControlPanel.BtnCalcStep"));
		btnReset			= new Button(Messages.getString("ControlPanel.BtnReset"));
		btnVariables		= new Button(Messages.getString("ControlPanel.BtnVariables"));
		lblSpeed			= new Label(Messages.getString("ControlPanel.LblSpeed"),Label.RIGHT);
		tfFormula			= new FormulaTextField(this,"");
		sbSpeed				= new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 200);
		//chZoom				= new Choice();
		GridBagLayout gbl	= new GridBagLayout();
		ControlPanelListener cpListener = new ControlPanelListener(aPanel,this);

		//chZoom.add("25%");
		//chZoom.add("50%");
		//chZoom.add("75%");
		//chZoom.add("100%");
		//chZoom.add("150%");
		//chZoom.add("200%");
		
		setLayout(gbl);
		
		//adding the components (in sub-panels) to this panel:
		GUIToolkit.addComponent(this,gbl,tfFormula,0,0,6,1,1.0,0.0,GridBagConstraints.BOTH);
		
		GUIToolkit.addComponent(this,gbl,btnVariables,0,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		GUIToolkit.addComponent(this,gbl,btnCalcStep,1,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		GUIToolkit.addComponent(this,gbl,btnCalcAll,2,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		GUIToolkit.addComponent(this,gbl,btnCalcAni,3,1,1,1,0.0,0.0,GridBagConstraints.NONE);
				
		Panel plSpeeds=new Panel(new BorderLayout(8,8));
		plSpeeds.add(lblSpeed,BorderLayout.WEST);
		plSpeeds.add(sbSpeed,BorderLayout.CENTER);
		
		GUIToolkit.addComponent(this,gbl,plSpeeds,4,1,1,1,1.0,0.0,GridBagConstraints.BOTH);
		GUIToolkit.addComponent(this,gbl,btnReset,5,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		
		//adding the listeners:
		btnCalcAll.addActionListener(cpListener);
		btnCalcAni.addActionListener(cpListener);
		btnCalcStep.addActionListener(cpListener);
		btnReset.addActionListener(cpListener);
		btnVariables.addActionListener(cpListener);
		sbSpeed.addAdjustmentListener(cpListener);
		//chZoom.addItemListener(cpListener);

		setBackground(SystemColor.activeCaptionBorder);		
	}


	public FormulaTextField getLblFormula() {
		return tfFormula;
	}

	public void setLblFormula(FormulaTextField lblFormula) {
		this.tfFormula = lblFormula;
	}
	
	public void updateLblFormula(String text) {
		this.tfFormula.setText(text);
	}


	public int getSpeed() {
		return sbSpeed.getValue()*SPEED_MULTIPLIER;		
	}


	public void setAnimating(boolean anim) {
		if (anim) {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAniStop"));
		} else {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAni"));
		}
	}

}
