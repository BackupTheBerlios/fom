/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;
//import java.util.*;

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
		
		this.pnlFormula=new FormulaPanel(this);
		this.pnlControls=new ControlPanel(this);
		this.pnlElements=new ElementPanel(this);
		
		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		pnlFormula.setLayout(new TreeLayout());
		dnd = new DragnDropListener(this);
		hotkey = new HotkeyListener(this);
		//this.addKeyListener(hotkey);
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

	private void debug() {
		/*Properties prop = System.getProperties();
		Enumeration enum = prop.propertyNames();
		String str;
		System.out.println("\nDEBUG INFO Formula-o-Matic:");
		System.out.println("------------------------------------------------------");
		while (enum.hasMoreElements()) {
			str = (String)enum.nextElement(); 
			System.out.println(str + " \t" + prop.getProperty(str));
		}
		System.out.println("------------------------------------------------------");*/
		System.out.println("DEBUG: Java Version: " + System.getProperty("java.version") + " from "+System.getProperty("java.vendor"));
	}

/*	public static void main(String[] args) {
		Frame f=new Frame("Test");
		AppletPanel ap=new AppletPanel();
		//ap.init();
		f.add(ap);
		f.setSize(800,600);
		f.setVisible(true);
	}*/

}
