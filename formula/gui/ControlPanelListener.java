/*
 * Created on 12.05.2004
 *
 */
package gui;

import java.awt.event.*;

import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class ControlPanelListener implements ActionListener, ItemListener, AdjustmentListener {
	
	
	private AppletPanel aPanel;
	private ControlPanel cPanel;
	private CalculatorThread calcThread;
	
	/**
	 * Creates a new ControlPanelListener.
	 * 
	 * @param ap root applet panel
	 * @param cp control panel on the applet panel
	 */
	public ControlPanelListener(AppletPanel ap, ControlPanel cp){
		this.aPanel = ap;
		this.cPanel = cp;
		this.calcThread = new CalculatorThread(ap);
		calcThread.start();
	}
	

	public void actionPerformed(ActionEvent event) {
		String actionCommand=event.getActionCommand();
		if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAll"))) {
			if (aPanel.getTreeList().isCompleteGlobalTree()) {
				calcThread.stopAnimation();
				aPanel.getTreeList().clearResults();
				calcThread.initCalculation(aPanel.getTreeList().getTreeArray()[0]);
				calcThread.calcAll();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAni"))) {
			if (aPanel.getTreeList().isCompleteGlobalTree()) {
				aPanel.getTreeList().clearResults();
				cPanel.setAnimating(true);
				calcThread.initCalculation(aPanel.getTreeList().getTreeArray()[0]);
				calcThread.calcAnimated();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAniStop"))) {
			calcThread.stopAnimation();
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcStep"))) {
			if (aPanel.getTreeList().isCompleteGlobalTree()) {
				if (!calcThread.isInitialized()) {
					calcThread.initCalculation(aPanel.getTreeList().getTreeArray()[0]);
				}
				calcThread.calcStep();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnReset"))) {
			calcThread.stopAnimation();
			aPanel.getTreeList().clearResults();
			aPanel.getControlPanel().updateTfResult("");
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnVariables"))) {
			DialogVariables dv = new DialogVariables(aPanel);
		}
	}


	public void itemStateChanged(ItemEvent event) { }
	public void adjustmentValueChanged(AdjustmentEvent event) { }
	
}
