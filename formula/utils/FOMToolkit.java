/*
 * Created on 16.05.2004
 *
 */
package utils;

import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 16.05.2004
 *
 */

import formula.Formula;
import gui.FormulaPanel;

public class FOMToolkit {

	private static float zoomFactor=1.0f;

	/**
	 * @return Returns the global zoom-factor for formula elements.
	 */
	public static float getZoomFactor() {
		return zoomFactor;
	}
	
	/**
	 * @param form The Formula object that need's a zoom-factor.
	 * @return Returns the object-dependand zoom-factor.
	 */
	public static float getZoomFactor(Formula form) {
		//no zooming for elements outside of the FormulaPanel!
		if(form.getParent() instanceof FormulaPanel)
			return zoomFactor;
		else
			return 1.0f;
	}
	
	/**
	 * Sets the global zoom-factor.
	 * @param zf zoom-factor (1.0 = 100%)
	 */
	public static void setZoomFactor(float zf) {
		zoomFactor=zf;
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
	
	
	public static boolean hasPartialMatches(Object[] array1, Object[] array2) {
		for (int a=0;a<array1.length;a++) {
			for (int b=0;b<array2.length;b++) {
				if (array1[a].equals(array2[b])) {
					return true;				 			
				}
			}
		}
		return false;
	}

}
