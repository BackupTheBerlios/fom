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
			varName[i].setBackground(SystemColor.text);
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
				varValueNumber[i].setBackground(SystemColor.text);
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
	/* HEIKO: Wenn du meinst, der Code hier ist mir sowieso zu unsauber, um mir den genauer anzuschaun.
	 * Aber imho solltest du dafür keine Arrays sondern Hashtabellen verwenden, bzw.
	 * so Zeug der Klasse ConstVarFormula überlassen. Also wenn du irgendeine Variable änderst, dann übergibst du
	 * das einer Methode mit VarName und neuem Wert in ConstVarFormula und machst das nicht hier irgendwie umständlich.
	 * Also ich hab mir das so gedacht (hast du teilweise auch so gemacht):
	 * - TypeConstVar:
	 *     	- enthält Liste der Formula-Objekte, die mit der Variablen belegt sind.
	 * 		- enthält Wert und Name der Variablen.
	 * 		- außerdem Methoden, um diese Werte sinnvoll zu ändern.
	 * - ConstVarFormula:
	 * 		- enthält eine Hashtabelle mit den Namen der Variablen als Key
	 * 		  (falls der Name geändert wird, wird die TypeConstVar in der Hashtabelle gelöscht und neu eingetragen)
	 * 		- Methoden zum Löschen/Hinzufügen/Ändern einer Variablen.
	 * 		- Methode zur Ausgabe des Wertes einer Variablen, diese wird dann von den VariableBoolean und VariableNumber verwendet, wenn getResult aufgerufen wird.
	 * 		- ODER: in TypeConstVar eine Methode, die ihre Formula-Elemente updatet (wäre wahrscheinlich einfacher).
	 *  - DialogVariables:
	 * 		- gibt alles an ConstVarFormula weiter, d.h. hier sollten keine 100 Zeilen code stehen.
	 * 		- Variablennamen und Werte über Methoden in ConstVarFormula abfragen
	 * 		  (die z.B. die Hashtabelle als String[] (Name) und String[] (Wert) zurückgeben).
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
		if (isValid.matches(" *")) {
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
				ConstVarFormula.setVarNameAll(oldVarName[index], "$$InternTempCounter" + tempCounter.toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + tempCounter.toString() + "$$";
				varName[index].setBackground(Color.RED);
				//MAURICE Dies gefällt mir nicht. Gibts da nicht was schöneres?
				// HEIKO: siehe getArrayPosition
				//MAURICE: Nein, ich meine die Erhöhung des tempCounters.
				tempCounter = new Integer(tempCounter.intValue() + 1);
			}
		} else {
			if (isValidName(varName[index].getText())) {
				//Replaces value, if variable name is valid.
				newInput = varValueNumber[index].getText();
				if (newInput.length() == 0) {
					newInput = "0";
				}
				if (newInput.matches("-?[0-9]+[.,]?[0-9]*")) {
					ConstVarFormula.setVarValueAll(varName[index].getText(), new Double(newInput.replace(',','.')));
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

	// Quick-Fix, damit der WindowListener funktioner. Um die Variablen x-en zu können.


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
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
		dispose();
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
