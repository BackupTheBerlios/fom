/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;

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
		this.setLayout(new BorderLayout());
		
		pnlFormula=new FormulaPanel(this);
		pnlControls=new ControlPanel(this);
		pnlElements=new ElementPanel(this);
		
		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		pnlFormula.setLayout(new TreeLayout());
		dnd = new DragnDropListener(this);
		hotkey = new HotkeyListener(this);
		this.addKeyListener(hotkey);
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

/*	public static void main(String[] args) {
		Frame f=new Frame("Test");
		AppletPanel ap=new AppletPanel();
		//ap.init();
		f.add(ap);
		f.setSize(800,600);
		f.setVisible(true);
	}*/

}
