/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;
import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class AppletPanel extends Applet {

	private FormulaPanel		pnlFormula;
	private ControlPanel		pnlControls;
	private ElementPanel		pnlElements;
	private DragnDropListener 	dnd;
	private HotkeyListener		hotkey;

	/**
	 * Initializes the applet. Adds all necessary components, etc.
	 */
	public void init() {
		debug();
		
		this.setLayout(new BorderLayout());

		dnd = new DragnDropListener(this);				// NOTE this must be initialized _before_ any other panel is created!
		
		this.pnlFormula=new FormulaPanel(this);
		this.pnlControls=new ControlPanel(this);
		this.pnlElements=new ElementPanel(this);
		
		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		pnlFormula.setLayout(new TreeLayout());

		hotkey = new HotkeyListener(this);
		pnlFormula.addMouseListener(dnd);
		pnlFormula.addMouseMotionListener(dnd);
		sPane.add(pnlFormula);
		
		this.add(pnlElements,BorderLayout.WEST);
		this.add(pnlControls,BorderLayout.SOUTH);
		this.add(sPane,BorderLayout.CENTER);
	}

	public FormulaPanel getFormulaPanel() {
		return pnlFormula;
	}

	public ElementPanel getElementPanel() {
		return pnlElements;
	}

	public ControlPanel getControlPanel() {
		return pnlControls;
	}

	public Selection getSelection() {
		return dnd.getSelection();
	}
	
	public HotkeyListener getHotkeyListener() {
		return hotkey;
	}
	
	public DragnDropListener getDragnDropListener() {
		return dnd;
	}

	private void debug() {
		System.out.println("DEBUG: Java Version: " + System.getProperty("java.version") + " from "+System.getProperty("java.vendor"));
	}

	// TODO: windowListener schreiben für Fenster schließen
	public static void main(String[] args) {
		AppletPanel ap=new AppletPanel();
		//ap.init();
		Frame f = new Frame(Messages.getString("AppletPanel.Title"));
		f.add(ap);
		f.setSize(650,500);
		f.setVisible(true);		
	}

}
