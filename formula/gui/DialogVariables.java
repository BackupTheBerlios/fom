/*
 * Created on 23.07.2004
 */
package gui;

import java.awt.*;
import java.awt.event.*;

import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */

public class DialogVariables extends Dialog implements TextListener, ActionListener, WindowListener {

	private Panel spaceForInputs;
	private ScrollPane scrollForInputs;
	private Button okButton;
	private TextField[] varName;
	private TextField[] varValueNumber;
	private Button[] varValueBoolean;
	private String[] oldVarName;

	//TODO Dialog öffnen

	/**
	 * Creates a window for setting and changing variables.
	 */
	public DialogVariables(AppletPanel parent) {
		super(new Frame(), "Variables' Dialog", true);
		TypeConstVar[] varArray = ConstVarFormula.getVarList();
		Object value;
		varName = new TextField[varArray.length];
		varValueNumber = new TextField[varArray.length];
		varValueBoolean = new Button[varArray.length];
		oldVarName = new String[varArray.length];
		spaceForInputs = new Panel(new GridLayout(varArray.length, 2,4,4));
		scrollForInputs = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		for (int i=0; i < varArray.length; i++) {
			//Reading all variables and creating their input fields.
			varName[i] = new TextField(varArray[i].getName());
			varName[i].setFont(new Font("Arial", Font.PLAIN, 11));
			varName[i].setBackground(SystemColor.text);
			varName[i].addTextListener(this);
			spaceForInputs.add(varName[i]);
			oldVarName[i] = varArray[i].getName();
			value = varArray[i].getValue();
			if (value instanceof Boolean) {
				//Boolean variable.
				varValueBoolean[i] = new Button(((Boolean)value).toString());
				varValueBoolean[i].setFont(new Font("Arial", Font.PLAIN, 11));
				varValueBoolean[i].addActionListener(this);
				spaceForInputs.add(varValueBoolean[i]);
			} else if (value instanceof Number) {
				//Number variable.
				varValueNumber[i] = new TextField(((Number)value).toString());
				varValueNumber[i].setFont(new Font("Arial", Font.PLAIN, 11));
				varValueNumber[i].setBackground(SystemColor.text);
				varValueNumber[i].addTextListener(this);
				spaceForInputs.add(varValueNumber[i]);
			}
		}
		//Closing button
		okButton = new Button("OK");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.SOUTH);
		scrollForInputs.add(spaceForInputs);
		add(scrollForInputs, BorderLayout.CENTER);
		pack();  //Needed for calculating proper height of dialog.
		if (varArray.length == 0) {
			setSize(300, okButton.getHeight() + getInsets().top+getInsets().bottom);
		} else if (varArray.length > 10) {
			setSize(300, 10*varName[0].getPreferredSize().height + okButton.getHeight() + scrollForInputs.getInsets().top+scrollForInputs.getInsets().bottom + getInsets().top+getInsets().bottom);
		} else {
			setSize(300, varArray.length*varName[0].getPreferredSize().height + okButton.getHeight() + scrollForInputs.getInsets().top+scrollForInputs.getInsets().bottom + getInsets().top+getInsets().bottom);
		}
		addWindowListener(this);
		show();
	}

	/**
	 * Searches in an array for a specific object. 
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
	 * @param isValid To be checked.
	 * @return Returns, if variable name is valid.
	 */
	private final boolean isValidName(String isValid) {
		boolean valid = true;
		int count = 0;
		//name must have at least one non-space character.
		/*if (isValid.matches(" *")) {
			valid = false;
		}*/
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

	/**
	 * Ersetzt alle Variablennamen durch neue, oder den Number-Wert von den Variablen
	 */
	public void textValueChanged(TextEvent txtEvent) {
		int index = getArrayPosition(txtEvent.getSource(), varValueNumber);
		String newInput;
		if (index == -1) {
			//Replaces Variablename.
			index = getArrayPosition(txtEvent.getSource(), varName);
			if (isValidName(varName[index].getText())) {
				ConstVarFormula.setVarNameAll(oldVarName[index], varName[index].getText());
				oldVarName[index] = varName[index].getText();
				varName[index].setBackground(SystemColor.text);
			} else {
				//Leere oder doppelete Variablennamen müssen verhindert werden.
				ConstVarFormula.setVarNameAll(oldVarName[index], "$$InternTempCounter" + new Integer(index).toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + new Integer(index).toString() + "$$";
				varName[index].setBackground(Color.RED);
			}
		} else {
			if (isValidName(varName[index].getText())) {
				//Replaces value, if variable name is valid.
				newInput = varValueNumber[index].getText();
				if (newInput.length() == 0) {
					newInput = "0";
				}
				/*if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
					varValueNumber[index].setBackground(SystemColor.text);
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
				} else {
					varValueNumber[index].setBackground(Color.RED);
				}*/
				try {
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
					varValueNumber[index].setBackground(SystemColor.text);
				} catch (NumberFormatException nfe) {
					varValueNumber[index].setBackground(Color.RED);
				} finally {
					repaint();
				}
			}
		}
	}

	/**
	 * Ersetzt den Boolean-Wert der Variablen oder schliesst den Dialog.
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
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Boolean(true));
					varValueBoolean[index].setLabel("true");
				} else {
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Boolean(false));
					varValueBoolean[index].setLabel("false");				
				}
			}
		}
	}

	/* MAURICE: Wenn das Variablenfenster von einem anderen Fenster überdeckt wird,
	 * und man dann dem Formula-O-Matic-Fenster über die Taskleiste den Fokus gibt,
	 * wird das Variablenfenster nicht neu dargestellt.
	 */
	 // HEIKO: Behoben mit requestFocus?
	 // Wenn nicht müsste ich einen WindowListener in AppletPanel einbauen

	// Quick-Fix, damit der WindowListener funktioner. Um die Variablen x-en zu können.


	public void windowClosing(WindowEvent arg0) {
		dispose();
	}

	public void windowDeactivated(WindowEvent arg0) {
		this.requestFocus();		
	}

	public void windowDeiconified(WindowEvent arg0) {  }
	public void windowIconified(WindowEvent arg0) {	 }
	public void windowOpened(WindowEvent arg0) {  }
	public void windowActivated(WindowEvent arg0) {  }
	public void windowClosed(WindowEvent arg0) {  }

}
