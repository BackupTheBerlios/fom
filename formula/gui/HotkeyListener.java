/* $Id: HotkeyListener.java,v 1.9 2004/08/29 15:15:54 shadowice Exp $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.9 $
 */
public class HotkeyListener implements KeyListener {

	private AppletPanel aPanel;

	/**
	 * Creates a new HotkeyListener.
	 */
	public HotkeyListener(AppletPanel ap) {
		super();
		this.aPanel = ap;
	}


	public void keyTyped(KeyEvent kEvent) {
		char keyChar = kEvent.getKeyChar();
		switch (keyChar) {
			case 'c':
				// TODO Copy
				break;
			case 'x':
				// TODO Cut
				break;
			case 'v':
				// TODO Paste
				break;
		}
	}


	public void keyReleased(KeyEvent kEvent) {
		int keyCode = kEvent.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_DELETE:
				aPanel.getSelection().delete();
				aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
				aPanel.getControlPanel().updateTfResult("");
				break;
		}
		
	}


	public void keyPressed(KeyEvent e) {  }

}
