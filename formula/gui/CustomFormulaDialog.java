/* $Id: CustomFormulaDialog.java,v 1.1 2004/08/26 16:54:38 shadowice Exp $
 * Created on 26.08.2004
 *
 */
package gui;

import java.awt.event.*;
import java.awt.*;

import formula.*;
import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.1 $
 */
public class CustomFormulaDialog extends Dialog implements ActionListener, WindowListener {

	GridBagLayout 	gbl				= new GridBagLayout();
	Formula 		currentFormula	= null;	// TODO Formula in CustomFormula ändern
	String 			name			= "";
	Label 			lblEnterText;
	TextField 		tfFormulaName;
	Button 			btnOk;
	Label			lblName;
	Panel			borderedPanel;

	/**
	 * Creates a new CustomFormulaDialog (modal=true).
	 * 
	 * @param owner owner Frame
	 */
	public CustomFormulaDialog(Frame owner) {
		super(owner,Messages.getString("CustomFormulaDialog.Title"),true);
		lblEnterText	= new Label(Messages.getString("CustomFormulaDialog.EnterNameLabel"),Label.CENTER);
		tfFormulaName	= new TextField();
		btnOk 			= new Button(Messages.getString("CustomFormulaDialog.OkButton"));
		lblName			= new Label(Messages.getString("CustomFormulaDialog.NameLabel"),Label.RIGHT);
		borderedPanel	= new Panel(new FlowLayout(FlowLayout.CENTER,5,5));
		
		borderedPanel.setBackground(Color.WHITE);
		setLayout(gbl);
		setSize(Formula.FORMULAWIDTH*2,180);
		
				
		btnOk.addActionListener(this);
		addWindowListener(this);

		FOMToolkit.addComponent(this,gbl,lblEnterText,0,0,2,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,lblName,0,1,1,1,0.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,tfFormulaName,1,1,1,1,1.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,borderedPanel,0,2,2,1,0.0,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,btnOk,0,3,2,1,0.0,0.0,GridBagConstraints.NONE);
	}

	
	/**
	 * Shows the Dialog for a certain CustomFormula
	 * @param form a custom formula
	 * @return returns the name of the formula
	 */
	//	TODO Formula in CustomFormula ändern
	public String showDialog(Formula form) {
		if (currentFormula != null) {
			borderedPanel.removeAll();
		}
		currentFormula = form;
		borderedPanel.add(form);
		borderedPanel.validate();
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

}
