/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;
//import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class AppletPanel extends Applet {

	private FormulaPanel pnlFormula;
	private ControlPanel pnlControls;
	private ElementPanel pnlElements;

	/**
	 * Initializes the applet. Adds all necessary components, etc.
	 */
	public void init() {
		setLayout(new BorderLayout());
		
		pnlFormula=new FormulaPanel(this);
		pnlControls=new ControlPanel(this);
		pnlElements=new ElementPanel(this);
		
		ScrollPane sPane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
		pnlFormula.setLayout(new TreeLayout());
		DragnDropListener dnd = new DragnDropListener(this);
		pnlFormula.addMouseListener(dnd);
		pnlFormula.addMouseMotionListener(dnd);
		sPane.add(pnlFormula);
		
		add(pnlElements,BorderLayout.WEST);
		add(pnlControls,BorderLayout.SOUTH);
		add(sPane,BorderLayout.CENTER);
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

/*	public static void main(String[] args) {
		Frame f=new Frame("Test");
		AppletPanel ap=new AppletPanel();
		//ap.init();
		f.add(ap);
		f.setSize(800,600);
		f.setVisible(true);
	}*/

}
