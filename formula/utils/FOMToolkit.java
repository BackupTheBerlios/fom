/*
 * Created on 16.05.2004
 *
 */
package utils;

import java.awt.*;
import java.text.*;

import formula.*;
import gui.*;

/**
 * Tool class for Formula-o-Matic.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 16.05.2004
 *
 */


public final class FOMToolkit {

	private static float zoomFactor = 1.0f;


	/**
	 * Hidden constructor for tool class.
	 */
	private FOMToolkit() { }


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
		if (form.getParent() instanceof FormulaPanel) {
			return zoomFactor;
		} else {
			return 1.0f;
		}
	}


	/**
	 * Sets the global zoom-factor.
	 * @param zf zoom-factor (1.0 = 100%)
	 */
	public static void setZoomFactor(float zf) {
		zoomFactor = zf;
	}


	/**
	 * Helper-method for adding components to the container.
	 *
	 * @see java.awt.GridBagLayout
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
	 * @param fill fill of the GridBagConstrains
	 */
	public static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y,
	 								int width, int height, double weightx, double weighty, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbl.setConstraints(c, gbc);
		cont.add(c);
	}


	/**
	 * Tests whether there is an object in the first array that's equal to one in the second array.
	 *
	 * @param array1 first array
	 * @param array2 second array
	 * @return true if one object is in both arrays
	 */
	public static boolean hasPartialMatches(Object[] array1, Object[] array2) {
		for (int a = 0; a < array1.length; a++) {
			for (int b = 0; b < array2.length; b++) {
				if (array1[a].equals(array2[b])) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * getFormatedString returns a string representing a double value.
	 * If the string is longer then the maxStrLength value, then it'll
	 * return it in scientific notation.
	 * 
	 * @param value double value to convert
	 * @param maxStrLength maximal string length
	 * @return a formated string of the double value
	 * @see java.text.DecimalFormat
	 */
	public static String getFormatedString(double value,int maxStrLength) {
		DecimalFormat dFormat = new DecimalFormat();
		dFormat.applyPattern("0.0");
		String resultStr = dFormat.format(value);
		String formatStr = "0.";
		if (resultStr.length() > maxStrLength) {
			int eSpace;										// how long will the E.. number be			
			if ((value < 1.0) && (value > -1.0)) {
				eSpace = (int)(-(Math.log(Math.log(value)/Math.log(10))/Math.log(10)))+2;
			} else {
				eSpace = (int)(Math.log(Math.log(value)/Math.log(10))/Math.log(10))+1;
			}

			int signSpace = (value < 0.0) ? 1 : 0;			// Space for - sign
			formatStr = "0.#";
			for (int i=0;i<maxStrLength-3-eSpace-signSpace;i++) {
				formatStr += '#';
			}
			formatStr += "E0";
			dFormat.applyPattern(formatStr);
			resultStr = dFormat.format(value);
		}
		return resultStr;
	}

}
