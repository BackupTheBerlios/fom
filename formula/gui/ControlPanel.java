/* $Id: ControlPanel.java,v 1.27 2004/10/19 21:26:05 shadowice Exp $
 * Created on 22.04.2004
 *
 */
package gui;

import java.awt.*;

import utils.*;

/**
 * The ControlPanel contains all buttons and other elements to control,
 * how the formula-tree is calculated or what to do with it. It is located
 * at the bottom of the applet.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.27 $
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
		tfResult					= new TextField(19);
		sbSpeed				= new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 200);
		GridBagLayout gbl	= new GridBagLayout();
		GridBagLayout gblt= new GridBagLayout();
		ControlPanelListener cpListener = new ControlPanelListener(aPanel,this);

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

		setBackground(SystemColor.activeCaptionBorder);
	}


	/**
	 * @return returns the text field containing a string representation of the formula tree
	 */
	public FormulaTextField getFormulaTextField() {
		return ftfFormula;
	}


	/**
	 * Sets the formula textfield (not its value!).
	 * @param tfFormula
	 */
	public void setTfFormula(FormulaTextField tfFormula) {
		this.ftfFormula = tfFormula;
	}


	/**
	 * Sets the value of the formula textfield.
	 * @param text normally a string that represents the formula tree, or an error message
	 */
	public void updateTfFormula(String text) {
		this.ftfFormula.setText(text);
	}


	/**
	 * Result of the tree.
	 * @param text should be a number or boolean value
	 */
	public void updateTfResult(String text) {
		this.tfResult.setText(text);
	}


	/**
	 * @return returns the speedbar value
	 */
	public int getSpeed() {
		return sbSpeed.getValue()*SPEED_MULTIPLIER;		
	}


	/**
	 * @return returns the parent AppletPanel object
	 */
	public AppletPanel getAppletPanel() {
		return aPanel;
	}


	/**
	 * Sets the state of the animation button (start/stop)
	 * @param anim true = button shows stop, false = start
	 */
	public void setAnimating(boolean anim) {
		if (anim) {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAniStop"));
		} else {
			btnCalcAni.setLabel(Messages.getString("ControlPanel.BtnCalcAni"));
		}
	}

}
