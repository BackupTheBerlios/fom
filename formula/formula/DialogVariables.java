/*
 * Created on 23.07.2004
 */
package formula;

import java.awt.*;
import java.awt.event.*;
import gui.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class DialogVariables extends Container implements TextListener, ActionListener {

	Dialog dialogVar;
	Button okButton;
	TextField[] varName;
	TextField[] varValueNumber;
	Button[] varValueBoolean;
	String[] oldVarName;
	Integer tempCounter = new Integer(0);

	//TODO Dialog öffnen

	//MAURICE Ich muss bei diesem Fehler einen Dialog/Frame angeben. Bin mir nicht
	//sicher, ob es AppletPanel ist oder nicht, Eclipse erkennt aber AppletPanel
	//noch nicht einmal, obwohl ich das so wie du gemacht habe (mit "import gui.*;")
	//Was gehört dort hin?
	public DialogVariables() {
		TypeConstVar[] varArray = ConstVarFormula.getVarList();
		Object value;
		setLayout(new BorderLayout());
		dialogVar = new Dialog(AppletPanel, "Variables' Dialog");
		varName = new TextField[varArray.length];
		varValueNumber = new TextField[varArray.length];
		varValueBoolean = new Button[varArray.length];
		String[] oldVarName = new String[varArray.length];
		for (int i=0; i < varArray.length; i++) {
			varName[i].setText(varArray[i].getName());
			add(varName[i], BorderLayout.WEST);
			oldVarName[i] = varArray[i].getName();
			value = varArray[i].getValue();
			if (value instanceof Boolean) {
				varValueBoolean[i].setLabel(((Boolean)value).toString());
				varValueBoolean[i].addActionListener(this);
				add(varValueBoolean[i], BorderLayout.EAST);
				varValueNumber[i].enable(false);
			} else if (value instanceof Number) {
				varValueNumber[i].setText(((Number)value).toString());
				varValueNumber[i].addTextListener(this);
				add(varValueNumber[i], BorderLayout.EAST);
				varValueBoolean[i].enable(false);
			}
		}
		okButton = new Button("OK");
		add(okButton, BorderLayout.SOUTH);
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
	 * Ersetzt alle Variablennamen durch neue, oder den Number-Wert von den Variablen
	 */
	public void textValueChanged(TextEvent arg0) {
		int index = getArrayPosition(arg0.getSource(), varValueNumber);
		String newInput;
		if (index == -1) {
			//Ersetzt den Variablennamen.
			index = getArrayPosition(arg0.getSource(), varName);
			if (varName[index].getText().length() == 0) {
				ConstVarFormula.setVarNameAll(oldVarName[index], "$$InternTempCounter" + tempCounter.toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + tempCounter.toString() + "$$";
				//MAURICE Dies gefällt mir nicht. Gibts da nicht was schöneres?
				tempCounter = new Integer(tempCounter.intValue() + 1);
			} else {
				ConstVarFormula.setVarNameAll(oldVarName[index], varName[index].getText());
				oldVarName[index] = varName[index].getText();
			}
		} else {
			//Ersetzt den Wert.
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
	public void actionPerformed(ActionEvent arg0) {
		int index = getArrayPosition(arg0.getSource(), varValueBoolean);
		if (index == -1) {
			//Schliesst den Dialog.
			for (int i=0; i < varName.length; i++) {
				if (varName[i].getText().length() == 0) {
					if (varValueBoolean[i].isEnabled()) {
						ConstVarFormula.setVarValueAll(oldVarName[i], new Boolean(false));
					} else {
						ConstVarFormula.setVarValueAll(oldVarName[i], new Double(0));
					}
					ConstVarFormula.deleteVarBranch(oldVarName[i]);
				}
			}
			//MAURICE Bin zu blöd. Finde einfach nicht heraus, wie ich meinen Dialog
			//wieder schliessen kann.
			//TODO Dialog schliessen.
		} else {
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
