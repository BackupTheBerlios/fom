/* $Id: HotkeyListener.java,v 1.10 2004/08/30 19:30:52 shadowice Exp $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.*;

import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.10 $
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
				aPanel.getSelection().copyToClipboard();
				break;
			case 'x':
				aPanel.getSelection().copyToClipboard();
				aPanel.getSelection().delete();
				break;
			case 'v':
				// doesn't paste the clipboard content, pastes a copy of it instead
				Formula pasteForm = (Formula)AppletPanel.getClipboard().clone();
				
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
