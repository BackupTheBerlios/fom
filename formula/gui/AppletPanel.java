/*
 * Created on 22.04.2004
 *
 */
package gui;

import java.applet.Applet;
import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 22.04.2004
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
		
		pnlElements=new ElementPanel();
		pnlControls=new ControlPanel();
		pnlFormula=new FormulaPanel();
		
		add(pnlElements,BorderLayout.WEST);
		add(pnlControls,BorderLayout.SOUTH);
		add(pnlFormula,BorderLayout.CENTER);
	}

	public FormulaPanel getPnlFormula() {
		return pnlFormula;
	}

	public void setPnlFormula(FormulaPanel pnlFormula) {
		this.pnlFormula = pnlFormula;
	}

	public ElementPanel getPnlElements() {
		return pnlElements;
	}

	public void setPnlElements(ElementPanel pnlElements) {
		this.pnlElements = pnlElements;
	}

	public ControlPanel getPnlControls() {
		return pnlControls;
	}

	public void setPnlControls(ControlPanel pnlControls) {
		this.pnlControls = pnlControls;
	}

	public AppletPanel() {
		init();
	}

/*	public static void main(String[] args) {
		Frame f=new Frame("Test");
		AppletPanel ap=new AppletPanel();
		f.add(ap);
		f.setSize(800,600);
		f.setVisible(true);
	}*/

}
