/* $Revision: 1.6 $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.6 $
 */
public class HotkeyListener implements KeyListener {

	private AppletPanel aPanel;

	/**
	 * 
	 */
	public HotkeyListener(AppletPanel ap) {
		super();
		aPanel = ap;
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
