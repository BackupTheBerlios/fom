/* $Id: AppletPanel.java,v 1.28 2004/09/09 15:09:17 shadowice Exp $
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
 * @version $Revision: 1.28 $
 */
public class AppletPanel extends Applet implements WindowListener {

	private DragnDropListener	dnd;
	private HotkeyListener		hotkey;
	private ControlPanel			pnlControls;
	private ElementPanel		pnlElements;

	private FormulaPanel		pnlFormula;

	private FormulaList			treeList;
	private VariableList			varList;

	private static Formula clipboard;	// root formula of a copied (sub)tree
	
	private PopupMenu 			popupMenu;

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

		this.treeList = new FormulaList();
		this.varList = new VariableList();

		this.setLayout(new BorderLayout());

		// NOTE this must be initialized _before_ any other panel is created!
		this.dnd = new DragnDropListener(this);

		this.pnlFormula = new FormulaPanel(this);
		this.pnlControls = new ControlPanel(this);
		this.pnlElements = new ElementPanel(this);

		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		sPane.getHAdjustable().setUnitIncrement(32);
		sPane.getVAdjustable().setUnitIncrement(32);
		pnlFormula.setLayout(new TreeLayout());

		hotkey = new HotkeyListener(this);
		pnlFormula.addMouseListener(dnd);
		pnlFormula.addMouseMotionListener(dnd);
		pnlFormula.addKeyListener(hotkey);
		this.addKeyListener(hotkey);
		sPane.add(pnlFormula);

		popupMenu = new PopupMenu();
		MenuItem miCut = new MenuItem(Messages.getString("PopupMenu.Cut"),new MenuShortcut(KeyEvent.VK_X));
		MenuItem miCopy = new MenuItem(Messages.getString("PopupMenu.Copy"),new MenuShortcut(KeyEvent.VK_C));
		MenuItem miPaste = new MenuItem(Messages.getString("PopupMenu.Paste"),new MenuShortcut(KeyEvent.VK_V));
		MenuItem miDelete = new MenuItem(Messages.getString("PopupMenu.Delete"));
		miCut.addActionListener(hotkey);
		miCopy.addActionListener(hotkey);
		miPaste.addActionListener(hotkey);
		miDelete.addActionListener(hotkey);
		popupMenu.add(miCut);
		popupMenu.add(miCopy);
		popupMenu.add(miPaste);
		popupMenu.addSeparator();
		popupMenu.add(miDelete);

		pnlFormula.add(popupMenu);

		this.add(pnlElements, BorderLayout.WEST);
		this.add(pnlControls, BorderLayout.SOUTH);
		this.add(sPane, BorderLayout.CENTER);
		
	}


	/**
	 * Sets the clipboard content.
	 * Note: No real clipboard, just a fake implementation.
	 * @param form root formula element of a tree
	 */
	public static void setClipboard(Formula form) {
		clipboard = form;
	}


	/**
	 * Note: No real clipboard, just a fake implementation.
	 * @return returns the content of the clipboard
	 */
	public static Formula getClipboard() {
		return clipboard;
	}


	public PopupMenu getPopupMenu() {
		return popupMenu;
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

	public void windowDeiconified(WindowEvent wEvent) { }
	public void windowIconified(WindowEvent wEvent) { }
	public void windowOpened(WindowEvent wEvent) { }
	public void windowActivated(WindowEvent wEvent) { }
	public void windowClosed(WindowEvent wEvent) { }
	public void windowDeactivated(WindowEvent wEvent) { }

}
