/*
 * Created on 12.05.2004
 *
 */
package gui;

import java.awt.event.*;
//import java.awt.*;
import formula.*;
import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
 *
 */
public class ControlPanelListener implements ActionListener, ItemListener, AdjustmentListener {
	
	
	private AppletPanel aPanel;
	private ControlPanel cPanel;
	private CalculatorThread calcThread;
	
	public ControlPanelListener(AppletPanel ap, ControlPanel cp){
		this.aPanel = ap;
		this.cPanel = cp;
		this.calcThread = new CalculatorThread(ap);
		calcThread.start();
	}
	

	public void actionPerformed(ActionEvent event) {
		String actionCommand=event.getActionCommand();
		if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAll"))) {
			if (Formula.isCompleteGlobalTree()) {
				clearResults();
				calcThread.initCalculation(Formula.getTreeList()[0]);
				calcThread.calcAll();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAni"))) {
			if (Formula.isCompleteGlobalTree()) {
				clearResults();
				cPanel.setAnimating(true);
				calcThread.initCalculation(Formula.getTreeList()[0]);
				calcThread.calcAnimated();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcAniStop"))) {
			calcThread.stopAnimation();
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnCalcStep"))) {
			if (Formula.isCompleteGlobalTree()) {
				if (!calcThread.isInitialized()) {
					calcThread.initCalculation(Formula.getTreeList()[0]);
				}
				calcThread.calcStep();
			}
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnReset"))) {
			clearResults();
		} else if (actionCommand.equals(Messages.getString("ControlPanel.BtnVariables"))) {
			DialogVariables dv = new DialogVariables(aPanel);
		}
	}

	public void itemStateChanged(ItemEvent event) {

	}

	public void adjustmentValueChanged(AdjustmentEvent event) {

	}
	
	private void clearResults() {
		calcThread.stopAnimation();
		Formula[] formList = Formula.getTreeList();
		for (int i=0;i<formList.length;i++) {
			clearResultsRec(formList[i]);
		}
	}

	private void clearResultsRec(Formula form) {
		form.clearResult();
		form.repaint();
		for (int i=0;i<form.getInputCount();i++) {
			if (form.getInput(i) != null) {
				clearResultsRec(form.getInput(i));
			}
		}
	}

}
