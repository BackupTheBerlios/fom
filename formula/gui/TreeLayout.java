/*
 * Created on 05.05.2004
 *
 */
package gui;

import java.awt.*;
//import java.util.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class TreeLayout implements LayoutManager {


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
		Rectangle bounds;
		int count = container.getComponentCount();
		for (int i=0;i<count;i++) {
			component = container.getComponent(i);
			bounds = component.getBounds();
			bounds.x=0;
			bounds.y=0;
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

}
