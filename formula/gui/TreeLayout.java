/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
//import java.util.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class TreeLayout implements LayoutManager {

	private static final int FORMULA_HGAP = 20;		// horizontal distance between 2 formula components 
	private static final int FORMULA_VGAP = 40;		// vertical distance between 2 formula components

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	public void removeLayoutComponent(Component component) {
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	public void layoutContainer(Container container) {
		Component component;
		Formula form;
		Rectangle bounds;
		int count = container.getComponentCount();
		for (int i=0;i<count;i++) {
			component = container.getComponent(i);
			bounds = component.getBounds();
			if (component instanceof Formula) {
				form = (Formula)component;
				if (form.getOutput() == null) {
					layoutTree(form);
				}
			}
			
			bounds.height = component.getSize().height;
			bounds.width = component.getSize().width;
			component.setBounds(bounds);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	public void addLayoutComponent(String str, Component component) {
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	public Dimension minimumLayoutSize(Container container) {
		return container.getSize();
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	public Dimension preferredLayoutSize(Container container) {
		return container.getSize();
	}


	private void layoutTree(Formula form) {
		int width = form.getWidthOfTree();		// recursive function within a recursive function (not really good)
		int width_px = width*form.getWidth()+(width+1)*FORMULA_HGAP;
		int in_width;
		int in_width_px;
		int xPos = form.getX() - width_px/2;
		int yPos = form.getY() + form.getHeight() + FORMULA_VGAP;
		System.out.println("[DEBUG] "+form+"("+form.getX()+", "+form.getY()+")\t "+width+"\t "+width_px);
		Formula inputForm;
		for (int i=0;i<form.getInputCount();i++) {
			inputForm = form.getInput(i);
			if (inputForm != null) {
				System.out.println ("xPos = "+xPos);
				in_width = inputForm.getWidthOfTree();	// if getWidthOfTree is too slow, alternative would be to return an array
				in_width_px = (in_width*form.getWidth())+(in_width)*FORMULA_HGAP;
				xPos+=in_width_px/2;
				inputForm.moveTo(xPos,yPos);
				System.out.println("[DEBUG]  "+inputForm+"\t "+ in_width+ "\t "+in_width_px);
				xPos+= in_width_px/2;
				layoutTree(inputForm);
			}
		}
		
	}
}
