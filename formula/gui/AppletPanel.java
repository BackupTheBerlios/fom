/* $Id: AppletPanel.java,v 1.23 2004/08/25 18:21:54 shadowice Exp $
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.event.*;
import java.awt.*;

import utils.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.23 $
 */
public class AppletPanel extends Applet implements WindowListener {

	private DragnDropListener	dnd;
	private HotkeyListener		hotkey;
	private ControlPanel		pnlControls;
	private ElementPanel		pnlElements;

	private FormulaPanel		pnlFormula;

	private FormulaList			treeList;
	private VariableList		varList;


	/**
	 * Main method to run this program as application.
	 *
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		AppletPanel aPanel = new AppletPanel();
		//ap.init();
		Frame frame = new Frame(Messages.getString("AppletPanel.Title"));
		frame.add(aPanel);
		frame.setSize(650,500);
		frame.setVisible(true);
	}


	/**
	 * @return returns the ControlPanel of this AppletPanel
	 */
	public ControlPanel getControlPanel() {
		return pnlControls;
	}


	/**
	 * @return returns a DragnDropListener that should be used throughout this programs instance.
	 */
	public DragnDropListener getDragnDropListener() {
		return dnd;
	}


	/**
	 * @return returns the ElementPanel of this AppletPanel
	 */
	public ElementPanel getElementPanel() {
		return pnlElements;
	}


	/**
	 * @return returns the FormulaPanel of this AppletPanel
	 */
	public FormulaPanel getFormulaPanel() {
		return pnlFormula;
	}


	/**
	 * @return returns the HotkeyListener that should be used throughout this programs instance
	 */
	public HotkeyListener getHotkeyListener() {
		return hotkey;
	}


	/**
	 * @return returns the currently selected elements as Selection object
	 */
	public Selection getSelection() {
		return dnd.getSelection();
	}


	/**
	 * @return Returns an array that contains all trees in the formula panel.
	 * A tree in this case is a formula element with no connection to it's output.
	 */
	public final FormulaList getTreeList() {
		return treeList;
	}


	/**
	 * @return returns a list with all variables of this instance
	 */
	public final VariableList getVariableList() {
		return varList;
	}


	/**
	 * Initializes the applet. Adds all necessary components, etc.
	 */
	public void init() {
		debug();

		treeList = new FormulaList();
		varList = new VariableList();

		this.setLayout(new BorderLayout());

		// NOTE this must be initialized _before_ any other panel is created!
		dnd = new DragnDropListener(this);

		this.pnlFormula = new FormulaPanel(this);
		this.pnlControls = new ControlPanel(this);
		this.pnlElements = new ElementPanel(this);

		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		pnlFormula.setLayout(new TreeLayout());

		hotkey = new HotkeyListener(this);
		pnlFormula.addMouseListener(dnd);
		pnlFormula.addMouseMotionListener(dnd);
		sPane.add(pnlFormula);

		this.add(pnlElements, BorderLayout.WEST);
		this.add(pnlControls, BorderLayout.SOUTH);
		this.add(sPane, BorderLayout.CENTER);
	}


	/**
	 * Debug output.
	 */
	private void debug() {
		System.out.println("DEBUG: Java Version: " + System.getProperty("java.version")
							+ " from " + System.getProperty("java.vendor"));
	}


	// methods implemented from WindowListener:
	public void windowClosing(WindowEvent wEvent) {
		System.exit(0);
	}

	public void windowActivated(WindowEvent wEvent) { }
	public void windowClosed(WindowEvent wEvent) { }
	public void windowDeactivated(WindowEvent wEvent) { }
	public void windowDeiconified(WindowEvent wEvent) { }
	public void windowIconified(WindowEvent wEvent) { }
	public void windowOpened(WindowEvent wEvent) { }

}
