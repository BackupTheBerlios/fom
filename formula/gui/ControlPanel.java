/* $Id: ControlPanel.java,v 1.21 2004/09/01 15:08:32 shadowice Exp $
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.*;

import utils.*;

/**
 * The ControlPanel contains all buttons and other elements to control,
 * how the formula-tree is calculated or what to do with it.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.21 $
 */
public class ControlPanel extends Panel {

	private Button btnReset;
	private Button btnCalcAni;
	private Button btnCalcStep;
	private Button btnVariables;
	private Button btnCalcAll;
	private Scrollbar sbSpeed;
	private FormulaTextField ftfFormula;
	private TextField tfResult;
	private Label lblSpeed;
	private Label lblEqualsign;
	//private Choice chZoom;
	private AppletPanel aPanel;

	private static final int SPEED_MULTIPLIER = 20;

	/**
	 * Creates the control panel.
	 */
	public ControlPanel(AppletPanel ap) {
		this.aPanel 		= ap;
		//creating all objects:
		btnCalcAll				= new Button(Messages.getString("ControlPanel.BtnCalcAll"));
		btnCalcAni			= new Button(Messages.getString("ControlPanel.BtnCalcAni"));
		btnCalcStep			= new Button(Messages.getString("ControlPanel.BtnCalcStep"));
		btnReset				= new Button(Messages.getString("ControlPanel.BtnReset"));
		btnVariables			= new Button(Messages.getString("ControlPanel.BtnVariables"));
		lblSpeed				= new Label(Messages.getString("ControlPanel.LblSpeed"),Label.RIGHT);
		lblEqualsign			= new Label("=",Label.CENTER);
		ftfFormula				= new FormulaTextField(this,"");
		tfResult					= new TextField(10);
		sbSpeed				= new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 200);
		//chZoom				= new Choice();
		GridBagLayout gbl	= new GridBagLayout();
		GridBagLayout gblt= new GridBagLayout();
		ControlPanelListener cpListener = new ControlPanelListener(aPanel,this);

		//chZoom.add("25%");
		//chZoom.add("50%");
		//chZoom.add("75%");
		//chZoom.add("100%");
		//chZoom.add("150%");
		//chZoom.add("200%");
		
		tfResult.setEditable(false);
		setLayout(gbl);

		//adding the components (in sub-panels) to this panel:
		Panel plTexts=new Panel(gblt);
		FOMToolkit.addComponent(plTexts,gblt,ftfFormula,0,0,1,1,1.0,0.0,GridBagConstraints.BOTH);
		FOMToolkit.addComponent(plTexts,gblt,lblEqualsign,1,0,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(plTexts,gblt,tfResult,2,0,1,1,0.0,0.0,GridBagConstraints.BOTH);
		
		FOMToolkit.addComponent(this,gbl,plTexts,0,0,6,1,1.0,0.0,GridBagConstraints.BOTH);
		
		FOMToolkit.addComponent(this,gbl,btnVariables,0,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,btnCalcStep,1,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,btnCalcAll,2,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,btnCalcAni,3,1,1,1,0.0,0.0,GridBagConstraints.NONE);
				
		Panel plSpeeds=new Panel(new BorderLayout(8,8));
		plSpeeds.add(lblSpeed,BorderLayout.WEST);
		plSpeeds.add(sbSpeed,BorderLayout.CENTER);
		
		FOMToolkit.addComponent(this,gbl,plSpeeds,4,1,1,1,1.0,0.0,GridBagConstraints.BOTH);
		FOMToolkit.addComponent(this,gbl,btnReset,5,1,1,1,0.0,0.0,GridBagConstraints.NONE);
		
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


	public FormulaTextField getFormulaTextField() {
		return ftfFormula;
	}

	public void setTfFormula(FormulaTextField tfFormula) {
		this.ftfFormula = tfFormula;
	}
	
	public void updateTfFormula(String text) {
		this.ftfFormula.setText(text);
	}

	public void updateTfResult(String text) {
		this.tfResult.setText(text);
	}

	public int getSpeed() {
		return sbSpeed.getValue()*SPEED_MULTIPLIER;		
	}

	public AppletPanel getAppletPanel() {
		return aPanel;
	}

	public void setAnimating(boolean anim) {
		if (anim) {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAniStop"));
		} else {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAni"));
		}
	}

}
