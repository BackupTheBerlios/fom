/* $Id: HotkeyListener.java,v 1.13 2004/09/02 13:49:08 shadowice Exp $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.*;

import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.13 $
 */
public class HotkeyListener implements KeyListener, ActionListener {

	private AppletPanel aPanel;
	
	private boolean ctrlDown = false;
	
	private static final int PASTE_XDISPLACEMENT = 15;
	private static final int PASTE_YDISPLACEMENT = 15;

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
				delete();
				break;
		}

		if (kEvent.isControlDown()) {
			char keyChar = kEvent.getKeyChar();
			switch (keyCode) {
				case KeyEvent.VK_C:
					copy();
					break;
				case KeyEvent.VK_X:
					cut();
					break;
				case KeyEvent.VK_V:
					paste();
					break;
			}
		}
	}


	public void actionPerformed(ActionEvent aEvent) {
		System.out.println(aEvent.getActionCommand());
	}


	private void cut() {
		aPanel.getSelection().copyToClipboard();
		aPanel.getSelection().delete();
		aPanel.getFormulaPanel().requestFocus();
	}


	private void copy() {
		aPanel.getSelection().copyToClipboard();
	}


	private void paste() {
		// doesn't paste the clipboard content, pastes a copy of it instead
		Formula pasteForm = AppletPanel.getClipboard();
		pasteForm.moveTo(pasteForm.getLocation().x+PASTE_XDISPLACEMENT,pasteForm.getLocation().y+PASTE_YDISPLACEMENT);
		pasteForm = (Formula)pasteForm.clone();
		aPanel.getFormulaPanel().addFormulaTree(pasteForm);
		aPanel.getFormulaPanel().requestFocus();
		aPanel.getFormulaPanel().doLayout();
		aPanel.getFormulaPanel().checkBounds();
		aPanel.getFormulaPanel().repaint();
	}


	private void delete() {
		aPanel.getSelection().delete();
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
		aPanel.getControlPanel().updateTfResult("");
	}

}
