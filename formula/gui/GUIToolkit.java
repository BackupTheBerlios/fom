/*
 * Created on 16.05.2004
 *
 */
package gui;

import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 16.05.2004
 *
 */

public class GUIToolkit {

	private static float ZoomFactor=1.0f;

	/**
	 * @return Returns the global zoom-factor for formula elements.
	 */
	public static float getZoomFactor() {
		return ZoomFactor;
	}
	
	/**
	 * Sets the global zoom-factor.
	 * @param zf zoom-factor (1.0 = 100%)
	 */
	public static void setZoomFactor(float zf) {
		ZoomFactor=zf;
	}
	
	/**
	 * Helper-method for adding components to the container.
	 * Note: This should be somewhere else then in this class.
	 * 
	 * @param cont The container, where the component c should be added.
	 * @param gbl The LayoutManager of "cont".
	 * @param c The component that should be added to "cont".
	 * @param x gridx of the GridBagConstraints
	 * @param y gridy of the GridBagConstraints
	 * @param width gridwidth of the GridBagConstraints
	 * @param height gridheight of the GridBagConstraints
	 * @param weightx weightx of the GridBagConstraints
	 * @param weighty weighty of the GridBagConstraints
	 */
	public static void addComponent(Container cont,GridBagLayout gbl,Component c,int x,int y,int width,int height,double weightx,double weighty,int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbc.insets = new Insets(2,2,2,2);
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}

}
