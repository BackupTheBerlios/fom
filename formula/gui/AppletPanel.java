/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;
import java.util.*;

import utils.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class AppletPanel extends Applet {

	private DragnDropListener 	dnd;
	private HotkeyListener			hotkey;
	private ControlPanel				pnlControls;
	private ElementPanel			pnlElements;

	private FormulaPanel			pnlFormula;
	
	private FormulaList				treeList;

	
	// TODO: windowListener schreiben für Fenster schließen
	public static void main(String[] args) {
		AppletPanel ap=new AppletPanel();
		//ap.init();
		Frame f = new Frame(Messages.getString("AppletPanel.Title"));
		f.add(ap);
		f.setSize(650,500);
		f.setVisible(true);		
	}
	
	
	public ControlPanel getControlPanel() {
		return pnlControls;
	}
	
	public DragnDropListener getDragnDropListener() {
		return dnd;
	}

	public ElementPanel getElementPanel() {
		return pnlElements;
	}

	public FormulaPanel getFormulaPanel() {
		return pnlFormula;
	}
	
	public HotkeyListener getHotkeyListener() {
		return hotkey;
	}

	public Selection getSelection() {
		return dnd.getSelection();
	}

	/**
	 * @return Returns an array that contains all trees in the formula panel. A tree in this case is a formula element with
	 * no connection to it's output.
	 */
	public final FormulaList getTreeList() {
		return treeList;
	}

	/**
	 * Initializes the applet. Adds all necessary components, etc.
	 */
	public void init() {
		debug();
		
		treeList = new FormulaList();
		
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
	

	private void debug() {
		System.out.println("DEBUG: Java Version: " + System.getProperty("java.version") + " from "+System.getProperty("java.vendor"));
	}

}
