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
	//private ScrollPane scrollForInputs;
	//private AppletPanel parent;
	private Button okButton;
	private TextField[] varName;
	private TextField[] varValueNumber;
	private Button[] varValueBoolean;
	private String[] oldVarName;
	private Integer tempCounter = new Integer(0);

	//TODO Dialog öffnen

	/* HEIKO:
	 * Also 1. verschieb die Klasse ins Package gui (Refactor->Move), die hat 
	 * nichts in formula zu suchen. (VariableDialog wär auch ein schönerer Name,
	 * aber mir egal :))
	 * 2. muss sie von Frame, Dialog oder Window (Dialog ist wahrscheinlich am besten)
	 * abgeleitet werden und nicht von Container.
	 * Den Constructor dementsprechend anpassen, ich ruf's mit
	 * DialogVariables(AppletPanel); auf.
	 * 3. da du mehrere Variablen darstellen willst, nimm BorderLayout und mach 
	 * eine Tabelle draus. Zum Scrollen gibts ScrollPane in das du aber noch ein
	 * zusätzliches Panel einfügen musst, um in dem Panel dann den BorderLayout
	 * zu verwenden.
	 * 4. super nicht vergessen (und zwar super(AppletPanel,true) damit's modal ist).
	 */
	public DialogVariables(AppletPanel parent) {
		super(new Frame(), "Variables' Dialog", true);
		//this.parent = parent;
		TypeConstVar[] varArray = ConstVarFormula.getVarList();
		this.setBounds(50, 50, 300, 300);
		Object value;
		//setLayout(new BorderLayout());
		varName = new TextField[varArray.length];
		varValueNumber = new TextField[varArray.length];
		varValueBoolean = new Button[varArray.length];
		oldVarName = new String[varArray.length];
		spaceForInputs = new Panel(new GridLayout(varArray.length, 2));
		//scrollForInputs = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		//scrollForInputs.setLayout(new GridLayout(varArray.length, 2));
		for (int i=0; i < varArray.length; i++) {
			//Reading all variables and creating their input fields.
			varName[i] = new TextField(varArray[i].getName());
			varName[i].addTextListener(this);
			spaceForInputs.add(varName[i]);
			oldVarName[i] = varArray[i].getName();
			value = varArray[i].getValue();
			if (value instanceof Boolean) {
				//Boolean variable.
				varValueBoolean[i] = new Button(((Boolean)value).toString());
				varValueBoolean[i].addActionListener(this);
				spaceForInputs.add(varValueBoolean[i]);
			} else if (value instanceof Number) {
				//Number variable.
				varValueNumber[i] = new TextField(((Number)value).toString());
				varValueNumber[i].addTextListener(this);
				spaceForInputs.add(varValueNumber[i]);
			}
		}
		//Closing button
		okButton = new Button("OK");
		okButton.addActionListener(this);
		add(okButton, BorderLayout.SOUTH);
		add(spaceForInputs, BorderLayout.NORTH);
//		scrollForInputs.add(spaceForInputs);
//		add(scrollForInputs, BorderLayout.NORTH);
//		scrollForInputs.setSize(150,scrollForInputs.getSize().height);
//		scrollForInputs.setBackground(SystemColor.text);
//		scrollForInputs.validate();
//		scrollForInputs.doLayout();
//		scrollForInputs.repaint();
		this.addWindowListener(this);
		show();
	}

	/**
	 * Searches in an array for a specific object. 
	 * @param findWhat What to find.
	 * @param findWhere Where to find.
	 * @return Returns index of object, or -1 if not found.
	 */
	/* HEIKO: Das hier sieht mir sehr nach Design-Fehler/schlechter Programmierung aus :o
	 * Also wenn ich das beim kurz überfliegen richtig verstehe, dann willst du
	 * in textValueChanged rausfinden, welche Variable verändert wurde. 
	 * Das geht ganz einfach, gib den TextFields Namen (setName). */
	/* MAURICE: Ich muss den Index des geänderten Feldes rausfind, da ich die Arrays
	 * immer gepaart habe. Es reicht also nicht nur das geänderte Array zu haben, sondern
	 * ich muss auch noch das dazu passende Array rausfinden. Zugegeben, ist vieleicht ein
	 * bisschen unsauber, aber es funktioniert. Wenn ich nämlich denen Namen geben würde,
	 * müsste ich dort auch den Index reincoden, was das auslesen dann auch unschön macht. */ 
	private final int getArrayPosition(Object findWhat, Object[] findWhere) {
		for (int i=0; i < findWhere.length; i++) {
			if (findWhat == findWhere[i]) {
				return i;
			}
		}
		return -1;
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
			if (varName[index].getText().length() == 0) {
				ConstVarFormula.setVarNameAll(oldVarName[index], "$$InternTempCounter" + tempCounter.toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + tempCounter.toString() + "$$";
				//MAURICE Dies gefällt mir nicht. Gibts da nicht was schöneres?
				// HEIKO: siehe getArrayPosition
				//MAURICE: Nein, ich meine die Erhöhung des tempCounters.
				tempCounter = new Integer(tempCounter.intValue() + 1);
			} else {
				ConstVarFormula.setVarNameAll(oldVarName[index], varName[index].getText());
				oldVarName[index] = varName[index].getText();
			}
		} else {
			//Replaces value.
			newInput = varValueNumber[index].getText();
			if (newInput.length() == 0) {
				newInput = "0";
			}
			if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
				ConstVarFormula.setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
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
			closingDialog();
		} else {
			//Replaces value.
			if (varValueBoolean[index].getLabel() == "false") {
				ConstVarFormula.setVarValueAll(varName[index].getText(), new Boolean(true));
				varValueBoolean[index].setLabel("true");
			} else {
				ConstVarFormula.setVarValueAll(varName[index].getText(), new Boolean(false));
				varValueBoolean[index].setLabel("false");				
			}
		}
	}

	private void closingDialog() {
		for (int i=0; i < varName.length; i++) {
			//All empty Variable names are getting a standard value.
			if (varName[i].getText().length() == 0) {
				if (varValueBoolean[i] != null) {
					ConstVarFormula.setVarValueAll(oldVarName[i], new Boolean(false));
				} else {
					ConstVarFormula.setVarValueAll(oldVarName[i], new Double(0));
				}
				ConstVarFormula.deleteVarBranch(oldVarName[i]);
			}
		}
		dispose();
		//parent.repaint();
	}

	/* MAURICE: Wenn das Variablenfenster von einem anderen Fenster überdeckt wird,
	 * und man dann dem Formula-O-Matic-Fenster über die Taskleiste den Fokus gibt,
	 * wird das Variablenfenster nicht neu dargestellt.
	 */

	// Quick-Fix, damit der WindowListener funktioner. Um die Variablen x-en zu können.


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
		repaint();
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		closingDialog();
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
