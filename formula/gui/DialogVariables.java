/* $Id: DialogVariables.java,v 1.19 2004/09/06 13:08:01 br3001 Exp $
 * Created on 23.07.2004
 */
package gui;

import java.awt.*;
import java.awt.event.*;

import utils.Messages;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.19 $
 */

public class DialogVariables extends Dialog implements TextListener, ActionListener, WindowListener {

	//private Panel spaceForInputs;
	private GridBagLayout spaceForInputs;
	private ScrollPane scrollForInputs;
	private Button okButton;
	private TextField[] varName;
	private TextField[] varValueNumber;
	private Button[] varValueBoolean;
	private String[] oldVarName;
	
	private AppletPanel aPanel;
	
	
	/**
	 * Creates a window for setting and changing variables.
	 */
	public DialogVariables(AppletPanel parent) {
		super((Frame)parent.getParent().getParent(), Messages.getString("DialogVariables.Title"), true);
		setLocation(300,100);
		Object value;		
		this.aPanel = parent;
		TypeConstVar[] varArray = aPanel.getVariableList().toVarArray();
		Panel dummyPanel;
		GridBagConstraints spaceForInputsNameField = new GridBagConstraints();
		GridBagConstraints spaceForInputsValueField = new GridBagConstraints();		
		varName = new TextField[varArray.length];
		varValueNumber = new TextField[varArray.length];
		varValueBoolean = new Button[varArray.length];
		oldVarName = new String[varArray.length];
		spaceForInputs = new GridBagLayout();
		dummyPanel = new Panel(spaceForInputs);
		scrollForInputs = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollForInputs.add(dummyPanel);
		spaceForInputsNameField.fill = GridBagConstraints.BOTH;
		spaceForInputsNameField.weightx = 1.0;
		spaceForInputsValueField.fill = GridBagConstraints.BOTH;
		spaceForInputsValueField.weightx = 1.0;
		spaceForInputsValueField.gridwidth = GridBagConstraints.REMAINDER;

		for (int i=0; i < varArray.length; i++) {
			//Reading all variables and creating their input fields.
			varName[i] = new TextField(varArray[i].getName());
			varName[i].setFont(new Font("Arial", Font.PLAIN, 11));
			varName[i].setBackground(Color.white);
			varName[i].addTextListener(this);
			spaceForInputs.setConstraints(varName[i], spaceForInputsNameField);
			dummyPanel.add(varName[i]);
			oldVarName[i] = varArray[i].getName();
			value = varArray[i].getValue();
			if (value instanceof Boolean) {
				//Boolean variable.
				varValueBoolean[i] = new Button(((Boolean)value).toString());
				varValueBoolean[i].setFont(new Font("Arial", Font.PLAIN, 11));
				varValueBoolean[i].addActionListener(this);
				spaceForInputs.setConstraints(varValueBoolean[i], spaceForInputsValueField);
				dummyPanel.add(varValueBoolean[i]);
			} else if (value instanceof Number) {
				//Number variable.
				varValueNumber[i] = new TextField(((Number)value).toString());
				varValueNumber[i].setFont(new Font("Arial", Font.PLAIN, 11));
				varValueNumber[i].setBackground(Color.white);
				varValueNumber[i].addTextListener(this);
				spaceForInputs.setConstraints(varValueNumber[i], spaceForInputsValueField);
				dummyPanel.add(varValueNumber[i]);
			}
		}
		//Closing button
		okButton = new Button("OK");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.SOUTH);

		add(scrollForInputs, BorderLayout.CENTER);
		pack();  //Needed for calculating proper height of dialog.
		scrollForInputs.setSize(dummyPanel.getPreferredSize().width + scrollForInputs.getInsets().left + scrollForInputs.getInsets().right, dummyPanel.getPreferredSize().height + 5);
		this.setSize(this.getPreferredSize());
		addWindowListener(this);
		show();
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
				//Leere oder doppelete Variablennamen m�ssen verhindert werden.
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
		if (index == -1) {
			//Closes dialog (OK button pressed).
			dispose();
		} else {
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
		dispose();
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
