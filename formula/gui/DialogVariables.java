/* $Id: DialogVariables.java,v 1.21 2004/09/10 11:09:24 shadowice Exp $
 * Created on 23.07.2004
 */
package gui;

import java.awt.*;
import java.awt.event.*;

import utils.FOMToolkit;
import utils.Messages;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.21 $
 */

public class DialogVariables extends Dialog implements TextListener, ActionListener, WindowListener {

	private GridBagLayout 	gblVariablePanel;
	private ScrollPane 		scrollForVariables;
	private Button 			okButton;
	private TextField[] 		varName;
	private TextField[] 		varValueNumber;
	private Button[] 			varValueBoolean;
	private String[] 			oldVarName;
	private Panel				variablesPanel;
	private Label				lblNoVariables;
	
	private AppletPanel 		aPanel;
	
	private static final int MAX_HEIGHT = 400;
	
	/**
	 * Creates a window for setting and changing variables.
	 */
	public DialogVariables(AppletPanel parent) {
		super((Frame)parent.getParent().getParent(), Messages.getString("DialogVariables.Title"), true);

		setLocation(300,100);

		Object value;		
		this.aPanel = parent;

		gblVariablePanel 	= new GridBagLayout();
		variablesPanel 		= new Panel(gblVariablePanel);
		scrollForVariables 	= new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		lblNoVariables		= new Label(Messages.getString("DialogVariables.NoVariablesLabel"),Label.CENTER);

		//Closing button
		okButton = new Button("OK");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.SOUTH);

		scrollForVariables.getHAdjustable().setUnitIncrement(16);
		scrollForVariables.getVAdjustable().setUnitIncrement(16);
		scrollForVariables.add(variablesPanel);
		add(scrollForVariables, BorderLayout.CENTER);
		
		addWindowListener(this);
	}


	public void show() {
		variablesPanel.removeAll();

		TypeConstVar[] varArray = aPanel.getVariableList().toVarArray();

		varName 				= new TextField[varArray.length];
		varValueNumber 	= new TextField[varArray.length];
		varValueBoolean 	= new Button[varArray.length];
		oldVarName 			= new String[varArray.length];

		Object value;
		for (int i=0; i < varArray.length; i++) {
			//Reading all variables and creating their input fields.
			varName[i] = new TextField(varArray[i].getName(),10);
			varName[i].setBackground(Color.white);
			varName[i].addTextListener(this);
			FOMToolkit.addComponent(variablesPanel,gblVariablePanel,varName[i],0,i,1,1,0.5,0.0,GridBagConstraints.HORIZONTAL);
			oldVarName[i] = varArray[i].getName();
			value = varArray[i].getValue();
			if (value instanceof Boolean) {
				//Boolean variable.
				varValueBoolean[i] = new Button(((Boolean)value).toString());
				varValueBoolean[i].addActionListener(this);
				FOMToolkit.addComponent(variablesPanel,gblVariablePanel,varValueBoolean[i],1,i,1,1,0.5,0.0,GridBagConstraints.HORIZONTAL);
			} else if (value instanceof Number) {
				//Number variable.
				varValueNumber[i] = new TextField(((Number)value).toString(),5);
				varValueNumber[i].setBackground(Color.white);
				varValueNumber[i].addTextListener(this);
				FOMToolkit.addComponent(variablesPanel,gblVariablePanel,varValueNumber[i],1,i,1,1,0.0,0.0,GridBagConstraints.HORIZONTAL);
			}
		}

		if (varArray.length == 0) {
			FOMToolkit.addComponent(variablesPanel,gblVariablePanel,lblNoVariables,0,0,1,1,1.0,0.0,GridBagConstraints.HORIZONTAL);
		}

		variablesPanel.validate();
		pack();
		Dimension size = new Dimension(variablesPanel.getPreferredSize().width+scrollForVariables.getInsets().left + scrollForVariables.getInsets().right,
											variablesPanel.getPreferredSize().height + scrollForVariables.getInsets().top + scrollForVariables.getInsets().bottom);
		if (size.height > MAX_HEIGHT) {
			size.height = MAX_HEIGHT;
		}
		scrollForVariables.setSize(size);
		pack();
		super.show();
	}


	/**
	 * Searches in an array for a specific object. 
	 * 
	 * @param findWhat What to find.
	 * @param findWhere Where to find.
	 * @return Returns index of object, or -1 if not found.
	 */
	private final int getArrayPosition(Object findWhat, Object[] findWhere) {
		for (int i=0; i < findWhere.length; i++) {
			if (findWhat == findWhere[i]) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * Validates a variable name. Valid names have at least one non-space character and
	 * don't use an already existing name.
	 * 
	 * @param isValid To be checked.
	 * @return Returns, if variable name is valid.
	 */
	private final boolean isValidName(String isValid) {
		boolean valid = true;
		int count = 0;
		//name must have at least one non-whitespace character.
		if (isValid.trim().equals("")) {
			valid = false;
		}
		
		if (valid) {
			//Double names are not valid.
			for (int i=0; i < varName.length; i++) {
				if (isValid.equals(varName[i].getText())) {
					count += 1;
				}
			}
			if (count > 1) {
				valid = false;
			}
		}
		return valid;
	}

	
	/*
	 * Method implemented from TestListener:
	 * Used to change a variable name or it's value if it's a number.
	 */
	public void textValueChanged(TextEvent txtEvent) {
		int index = getArrayPosition(txtEvent.getSource(), varValueNumber);
		String newInput;
		if (index == -1) {
			//Replaces Variablename.
			index = getArrayPosition(txtEvent.getSource(), varName);
			if (isValidName(varName[index].getText())) {
				aPanel.getVariableList().setVarNameAll(oldVarName[index], varName[index].getText());
				oldVarName[index] = varName[index].getText();
				varName[index].setBackground(Color.white);
			} else {
				//Leere oder doppelete Variablennamen müssen verhindert werden.
				aPanel.getVariableList().setVarNameAll(oldVarName[index], "$$InternTempCounter" + new Integer(index).toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + new Integer(index).toString() + "$$";
				varName[index].setBackground(Color.red);
			}
		} else {
			if (isValidName(varName[index].getText())) {
				//Replaces value, if variable name is valid.
				newInput = varValueNumber[index].getText();
				if (newInput.length() == 0) {
					newInput = "0";
				}
				/*if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
					varValueNumber[index].setBackground(Color.white);
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
				} else {
					varValueNumber[index].setBackground(Color.red);
				}*/
				try {
					aPanel.getVariableList().setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
					varValueNumber[index].setBackground(Color.white);
				} catch (NumberFormatException nfe) {
					varValueNumber[index].setBackground(Color.red);
				} finally {
					repaint();
				}
			}
		}
	}
	

	/*
	 * Method implemented from ActionListener for buttons:
	 * Used to change the state of boolean variables or to clode the dialog.
	 */
	public void actionPerformed(ActionEvent txtEvent) {
		int index = getArrayPosition(txtEvent.getSource(), varValueBoolean);
		if (index == -1) {		// OK button pressed:
			hide();
		} else {					// boolean variable button pressed:
			if (isValidName(varName[index].getText())) {
				//Replaces value, if variable name is valid.
				if (varValueBoolean[index].getLabel() == "false") {
					aPanel.getVariableList().setVarValueAll(varName[index].getText(), new Boolean(true));
					varValueBoolean[index].setLabel("true");
				} else {
					aPanel.getVariableList().setVarValueAll(varName[index].getText(), new Boolean(false));
					varValueBoolean[index].setLabel("false");				
				}
			}
		}
	}


	// Methods from WindowListener:
	public void windowClosing(WindowEvent wEvent) {
		hide();
	}


	public void windowDeactivated(WindowEvent wEvent) {
		this.requestFocus();
	}


	public void windowDeiconified(WindowEvent wEvent) {	}
	public void windowIconified(WindowEvent wEvent) {	 }
	public void windowOpened(WindowEvent wEvent) {	}
	public void windowActivated(WindowEvent wEvent) {	}
	public void windowClosed(WindowEvent wEvent) {	}

}
