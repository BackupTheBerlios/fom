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
	/* HEIKO: Das hier sieht mir sehr nach Design-Fehler/schlechter Programmierung aus :o
	 * Also wenn ich das beim kurz überfliegen richtig verstehe, dann willst du
	 * in textValueChanged rausfinden, welche Variable verändert wurde. 
	 * Das geht ganz einfach, gib den TextFields Namen (setName). */
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
			//Ersetzt den Variablennamen.
			index = getArrayPosition(txtEvent.getSource(), varName);
			if (varName[index].getText().length() == 0) {
				ConstVarFormula.setVarNameAll(oldVarName[index], "$$InternTempCounter" + tempCounter.toString() + "$$");
				oldVarName[index] = "$$InternTempCounter" + tempCounter.toString() + "$$";
				//MAURICE Dies gefällt mir nicht. Gibts da nicht was schöneres?
				// HEIKO: siehe getArrayPosition
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
	public void actionPerformed(ActionEvent txtEvent) {
		int index = getArrayPosition(txtEvent.getSource(), varValueBoolean);
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
			
			// HEIKO: Dialog schließen geht über hide() oder dispose(). Nimm dispose(), der Dialog
			// wird später nicht mehr benötigt, sondern neu erzeugt.
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
