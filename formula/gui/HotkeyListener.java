/* $Id: HotkeyListener.java,v 1.14 2004/09/02 14:35:42 shadowice Exp $
 * Created on 13.08.2004
 *
 */
package gui;

import java.awt.event.*;

import formula.*;
import utils.*;

/**
 * Handles shortcuts and popup menus.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.14 $
 */
public class HotkeyListener implements KeyListener, ActionListener {

	private AppletPanel aPanel;
	
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
		if (aEvent.getActionCommand().equals(Messages.getString("PopupMenu.Cut"))) {
			cut();
		} else if (aEvent.getActionCommand().equals(Messages.getString("PopupMenu.Copy"))) {
			copy();
		} else if (aEvent.getActionCommand().equals(Messages.getString("PopupMenu.Paste"))) {
			paste();
		} else if (aEvent.getActionCommand().equals(Messages.getString("PopupMenu.Delete"))) {
			delete();
		}
	}


	/**
	 * Puts a copy of the selected elements into the virtual clipboard and removes the selected elements.
	 */
	private void cut() {
		aPanel.getSelection().copyToClipboard();
		aPanel.getSelection().delete();
		aPanel.getFormulaPanel().requestFocus();
	}


	/**
	 * Puts a copy of the selected elements into the virtual clipboard.
	 */
	private void copy() {
		aPanel.getSelection().copyToClipboard();
	}


	/**
	 * Pastes something from the virtual clipboard to the FormulaPanel.
	 */
	private void paste() {
		// doesn't paste the clipboard content, pastes a copy of it instead
		Formula pasteForm = AppletPanel.getClipboard();
		if (pasteForm != null) {
			pasteForm.moveTo(pasteForm.getLocation().x+PASTE_XDISPLACEMENT,pasteForm.getLocation().y+PASTE_YDISPLACEMENT);
			pasteForm = (Formula)pasteForm.clone();
			aPanel.getFormulaPanel().addFormulaTree(pasteForm);
			aPanel.getFormulaPanel().requestFocus();
			aPanel.getFormulaPanel().doLayout();
			aPanel.getFormulaPanel().checkBounds();
			aPanel.getFormulaPanel().repaint();
		}
	}


	/**
	 * Deletes the selected elements.
	 */
	private void delete() {
		aPanel.getSelection().delete();
		aPanel.getControlPanel().getFormulaTextField().updateControlPanelText();
		aPanel.getControlPanel().updateTfResult("");
	}

}
