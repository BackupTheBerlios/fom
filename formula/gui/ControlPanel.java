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
	private FormulaLabel lblFormula;
	private Choice chZoom;
	private Label lblSpeed;
	private AppletPanel aPanel;


	public Label getLblFormula() {
		return lblFormula;
	}

	public void setLblFormula(FormulaLabel lblFormula) {
		this.lblFormula = lblFormula;
	}

	public Choice getChZoom() {
		return chZoom;
	}

	public void setChZoom(Choice chZoom) {
		this.chZoom = chZoom;
	}

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
		lblFormula			= new FormulaLabel("The formula tree is incomplete.");  //Text nur zum testen der Länge :)
		sbSpeed				= new Scrollbar(Scrollbar.HORIZONTAL, 10, 1, 0, 20);
		chZoom				= new Choice();
		GridBagLayout gbl	= new GridBagLayout();
		ControlPanelListener cpListener = new ControlPanelListener(aPanel,this);

		chZoom.add("25%");
		chZoom.add("50%");
		chZoom.add("75%");
		chZoom.add("100%");
		chZoom.add("150%");
		chZoom.add("200%");
		
		setLayout(gbl);
		
		//adding the components (in sub-panels) to this panel:
		Panel plLabel=new Panel();
		plLabel.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		plLabel.add(lblFormula);
		GUIToolkit.addComponent(this,gbl,plLabel,0,0,1,2,0.0,1.0,GridBagConstraints.NONE);
		
		Panel plTop=new Panel(new GridLayout(1,4,8,8));
		plTop.add(btnReset);
		plTop.add(btnCalcAni);
		plTop.add(btnCalcStep);
		plTop.add(btnCalcAll);
		GUIToolkit.addComponent(this,gbl,plTop,1,0,1,1,1.0,0.0,GridBagConstraints.BOTH);
		
		Panel plBottom=new Panel(new BorderLayout(8,8));
		plBottom.add(lblSpeed,BorderLayout.WEST);
		plBottom.add(sbSpeed,BorderLayout.CENTER);
		GUIToolkit.addComponent(this,gbl,plBottom,1,1,1,1,1.0,0.0,GridBagConstraints.BOTH);

		GUIToolkit.addComponent(this,gbl,btnVariables,2,0,1,1,0.0,0.0,GridBagConstraints.NONE);

		//adding the listeners:
		btnCalcAll.addActionListener(cpListener);
		btnCalcAni.addActionListener(cpListener);
		btnCalcStep.addActionListener(cpListener);
		btnReset.addActionListener(cpListener);
		btnVariables.addActionListener(cpListener);
		sbSpeed.addAdjustmentListener(cpListener);
		chZoom.addItemListener(cpListener);

		setBackground(SystemColor.activeCaptionBorder);		
	}
	

}
