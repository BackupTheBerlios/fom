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

	/**
	 * Creates the control panel.
	 */
	public ControlPanel() {
		//creating all objects:
		btnCalcAll			= new Button(Messages.getString("ControlPanel.BtnCalcAll"));
		btnCalcAni			= new Button(Messages.getString("ControlPanel.BtnCalcAni"));
		btnCalcStep			= new Button(Messages.getString("ControlPanel.BtnCalcStep"));
		btnReset			= new Button(Messages.getString("ControlPanel.BtnReset"));
		btnVariables		= new Button(Messages.getString("ControlPanel.BtnVariables"));
		lblSpeed			= new Label(Messages.getString("ControlPanel.LblSpeed"),Label.RIGHT);
		lblFormula			= new Label();
		sbSpeed				= new Scrollbar(Scrollbar.HORIZONTAL, 10, 1, 0, 20);
		chZoom				= new Choice();
		GridBagLayout gbl	= new GridBagLayout();
		ControlPanelListener cplistener = new ControlPanelListener();

		chZoom.add("25%");
		chZoom.add("50%");
		chZoom.add("75%");
		chZoom.add("100%");
		chZoom.add("150%");
		chZoom.add("200%");

		setLayout(gbl);
		
		//adding the components to this panel:
		addComponent(this,gbl,lblFormula  ,0,0,1,2,1.0,1.0);
		addComponent(this,gbl,btnReset	  ,1,0,1,1,0.0,0.0);
		addComponent(this,gbl,btnCalcAni  ,2,0,1,1,0.0,0.0);
		addComponent(this,gbl,btnCalcStep ,3,0,1,1,0.0,0.0);
		addComponent(this,gbl,btnCalcAll  ,4,0,1,1,0.0,0.0);
		addComponent(this,gbl,lblSpeed	  ,1,1,1,1,0.0,0.0);
		addComponent(this,gbl,sbSpeed     ,2,1,2,1,1.0,1.0);
		addComponent(this,gbl,btnVariables,4,1,1,1,0.0,0.0);
	
		//adding the listeners:
		btnCalcAll.addActionListener(cplistener);
		btnCalcAni.addActionListener(cplistener);
		btnCalcStep.addActionListener(cplistener);
		btnReset.addActionListener(cplistener);
		btnVariables.addActionListener(cplistener);
		sbSpeed.addAdjustmentListener(cplistener);
		chZoom.addItemListener(cplistener);
	}
	
	/**
	 * Helper-method for adding components to the container.
	 * Note: This should be somewhere else then in this class.
	 * 
	 * @param cont The container, where the component c should be added.
	 * @param gbl The LayoutManager of "cont".
	 * @param c The component that should be added to "cont".
	 * @param x gridx of the GridBagConstraints
	 * @param y gridy of the GridBagConstraints
	 * @param width gridwidth of the GridBagConstraints
	 * @param height gridheight of the GridBagConstraints
	 * @param weightx weightx of the GridBagConstraints
	 * @param weighty weighty of the GridBagConstraints
	 */
	private static void addComponent(Container cont,GridBagLayout gbl,Component c,int x,int y,int width,int height,double weightx,double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbc.insets = new Insets(2,2,2,2);
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}

}
