/* $Id: CustomFormulaDialog.java,v 1.5 2004/09/07 13:54:02 shadowice Exp $
 * Created on 26.08.2004
 *
 */
package gui;

import java.awt.event.*;
import java.awt.*;

import formula.*;
import utils.*;

/**
 * Dialog window for adding new CustomFormulas.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.5 $
 */
public class CustomFormulaDialog extends Dialog implements ActionListener, WindowListener, TextListener {

	GridBagLayout 	gbl					= new GridBagLayout();
	CustomFormula currentFormula	= null;
	String 			name					= "";
	Label 			lblEnterText;
	TextField 		tfFormulaName;
	Button 			btnOk;
	Panel			borderedPanel;
	List				lstVariables;

	/**
	 * Creates a new CustomFormulaDialog (modal=true).
	 * 
	 * @param owner owner Frame
	 */
	public CustomFormulaDialog(Frame owner) {
		super(owner,Messages.getString("CustomFormulaDialog.Title"),true);
		lblEnterText			= new Label(Messages.getString("CustomFormulaDialog.EnterNameLabel"),Label.CENTER);
		tfFormulaName		= new TextField();
		btnOk 					= new Button(Messages.getString("CustomFormulaDialog.OkButton"));
		borderedPanel		= new Panel(new FlowLayout(FlowLayout.CENTER,5,5));
		lstVariables			= new List(3,false);
		
		borderedPanel.setBackground(Color.white);
		setLayout(gbl);
		setResizable(false);
		tfFormulaName.addTextListener(this);
				
		btnOk.addActionListener(this);
		addWindowListener(this);

		FOMToolkit.addComponent(this,gbl,lblEnterText,		0,0,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,tfFormulaName,0,1,1,1,1.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,borderedPanel,	0,2,1,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,lstVariables,		0,3,1,1,0.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,btnOk,				0,4,1,1,0.0,0.0,GridBagConstraints.NONE);
	}

	
	/**
	 * Shows the Dialog for a certain CustomFormula
	 *
	 * @param form a custom formula
	 * @return returns the name of the formula
	 */
	public String showDialog(CustomFormula form,VariableList varList) {
		if (currentFormula != null) {
			borderedPanel.removeAll();
			lstVariables.removeAll();
		}
		name = form.getFormulaName();
		tfFormulaName.setText(name);
		currentFormula = form;
		borderedPanel.add(form);
		borderedPanel.validate();
		TypeConstVar cvt;
		if (varList.isEmpty()) {
			lstVariables.setVisible(false);
		} else {
			lstVariables.setVisible(true);
			for (int i=0;i<varList.size();i++) {
				cvt = (TypeConstVar)varList.elementAt(i);
				lstVariables.add(i+": "+cvt.getName());
			}
			lstVariables.validate();
		}

		pack();
		show();

		return tfFormulaName.getText();
	}


	public void actionPerformed(ActionEvent e) {
		hide();
	}


	public void windowClosing(WindowEvent e) {
		hide();
	}


	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }


	public void textValueChanged(TextEvent txtEvent) {
		if ((currentFormula != null) && (isVisible())) {
			currentFormula.setFormulaName(tfFormulaName.getText());
			currentFormula.repaint();
		}
	}

}
