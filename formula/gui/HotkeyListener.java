/* $Id: HotkeyListener.java,v 1.11 2004/08/31 12:38:19 shadowice Exp $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.*;

import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.11 $
 */
public class HotkeyListener implements KeyListener {

	private AppletPanel aPanel;
	
	private boolean ctrlDown = false;

	/**
	 * Creates a new HotkeyListener.
	 */
	public HotkeyListener(AppletPanel ap) {
		super();
		this.aPanel = ap;
	}


	public void keyTyped(KeyEvent kEvent) { }


	public void keyReleased(KeyEvent kEvent) { }


	public void keyPressed(KeyEvent kEvent) {
		int keyCode = kEvent.getKeyCode();
		switch (keyCode) {
			case KeyEvent.VK_DELETE:
				aPanel.getSelection().delete();
				aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
				aPanel.getControlPanel().updateTfResult("");
				break;
		}

		if (kEvent.isControlDown()) {
			char keyChar = kEvent.getKeyChar();
			switch (keyCode) {
				case KeyEvent.VK_C:
					aPanel.getSelection().copyToClipboard();
					System.out.println("copy");
					break;
				case KeyEvent.VK_X:
					aPanel.getSelection().copyToClipboard();
					aPanel.getSelection().delete();
					System.out.println("cut");
					break;
				case KeyEvent.VK_V:
					// doesn't paste the clipboard content, pastes a copy of it instead
					Formula pasteForm = (Formula)AppletPanel.getClipboard().clone();
					aPanel.getFormulaPanel().addFormulaTree(pasteForm);
					System.out.println("pasted: "+pasteForm);
					aPanel.getFormulaPanel().repaint();
					break;
			}
		}
	}

}
