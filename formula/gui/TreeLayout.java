/* $Id: TreeLayout.java,v 1.19 2004/10/19 21:26:05 shadowice Exp $
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;

import formula.*;

/**
 * LayoutManager for the workspace (FormulaPanel).
 * It arranges all formula components so that they form a tree.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.19 $
 */
public class TreeLayout implements LayoutManager {

	private static final int FORMULA_HGAP = 5;		// horizontal distance between 2 formula components 
	private static final int FORMULA_VGAP = 20;		// vertical distance between 2 formula components

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
			if (component instanceof Formula) {
				form = (Formula)component;
				if (form.getOutput() == null) {
					layoutTree(form);
				}
			}
		}
		checkBounds(container);
	}


	public void addLayoutComponent(String str, Component component) {
	}


	public Dimension minimumLayoutSize(Container container) {
		Dimension size = new Dimension(container.getSize());
		size.width += container.getInsets().left + container.getInsets().right;
		size.height += container.getInsets().bottom + container.getInsets().top;
		return size;
	}


	public Dimension preferredLayoutSize(Container container) {
		return minimumLayoutSize(container);
	}


	private void layoutTree(Formula form) {
		int width = form.getWidthOfTree();		// recursive function within a recursive function (not really good)
		int width_px = width*form.getSize().width+(width-1)*FORMULA_HGAP;
		int in_width;
		int in_width_px;
		int xPos = form.getLocation().x - width_px/2;
		int yPos = form.getLocation().y + form.getSize().height + FORMULA_VGAP;
		Formula inputForm;
		for (int i=0;i<form.getInputCount();i++) {
			inputForm = form.getInput(i);
			if (inputForm != null) {
				in_width = inputForm.getWidthOfTree();	// if getWidthOfTree is too slow, alternative would be to return an array
				in_width_px = (in_width*form.getSize().width)+(in_width-1)*FORMULA_HGAP;
				xPos += in_width_px/2;
				inputForm.moveTo(xPos,yPos);
				xPos += in_width_px/2 + FORMULA_HGAP;
				layoutTree(inputForm);
			} else {
				xPos += form.getSize().width + FORMULA_HGAP; 
			}
		}		
	}


	/**
	 * Checks the bounds of a container if all components are inside of it.
	 * If not it will be resized and all components will be moved if some are outside
	 * the left or upper borders.
	 * 
	 * @param container container to check
	 */
	private void checkBounds(Container container) {
		int max_x = 0, max_y = 0;
		int min_x = 0, min_y = 0;
		Rectangle oldBounds = container.getBounds();
		
		Component comp;
		// get the min/max needed coordinates for all elements
		for (int i=0;i<container.getComponentCount();i++) {
			comp = container.getComponent(i);
			if (comp.getLocation().x < min_x) {
				min_x = comp.getLocation().x;
			}
			if (comp.getLocation().y < min_y) {
				min_y = comp.getLocation().y;
			}
			if ((comp.getSize().width + comp.getLocation().x) > max_x) {
				max_x = comp.getSize().width + comp.getLocation().x;
			}
			if ((comp.getSize().height + comp.getLocation().y) > max_y) {
				max_y = comp.getSize().height + comp.getLocation().y;
			}
		}

		// move elements if min-size is below 0
		if (min_x < 0) {
			max_x = max_x - min_x;
			for (int i=0;i<container.getComponentCount();i++) {
				comp = container.getComponent(i);
				((Formula)comp).moveTo(comp.getLocation().x-min_x,comp.getLocation().y);
			}
		}
		if (min_y < 0) {
			max_y = max_y - min_y;
			for (int i=0;i<container.getComponentCount();i++) {
				comp = container.getComponent(i);
				((Formula)comp).moveTo(comp.getLocation().x,comp.getLocation().y-min_y);
			}
		}

		// set new container size if it's bigger:
		ScrollPane sp = (ScrollPane)container.getParent();
		if ((container.getSize().width < max_x) && (max_x != 0)) {
			container.setSize(max_x,container.getSize().height);
		}
		if ((container.getSize().height < max_y) && (max_y != 0)) {
			container.setSize(container.getSize().width,max_y);
		}
	}
}
