/*
 * Created on 12.05.2004
 *
 */
package gui;

import java.awt.event.*;
//import java.awt.*;

import utils.Messages;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class ControlPanelListener implements ActionListener, ItemListener, AdjustmentListener {
	
	
	AppletPanel aPanel;
	ControlPanel cPanel;
	
	public ControlPanelListener(AppletPanel ap, ControlPanel cp){
		this.aPanel = ap;
		this.cPanel = cp;
	}
	

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		// TODO Implement functionality to the if-blocks.
		String actionCommand=event.getActionCommand();
		if(actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAll"))) {

		}else if(actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAni"))) {

		}else if(actionCommand.equals(Messages.getString("ControlPanel.BtnCalcStep"))) {

		}else if(actionCommand.equals(Messages.getString("ControlPanel.BtnReset"))) {

		}else if(actionCommand.equals(Messages.getString("ControlPanel.BtnVariables"))) {
			//DialogVariables dv = new DialogVariable(aPanel);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	public void adjustmentValueChanged(AdjustmentEvent event) {
		// TODO Auto-generated method stub

	}

}
