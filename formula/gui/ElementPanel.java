/*
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;
import formula.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class ElementPanel extends Panel {

	private Choice chCategoryList;
	private ScrollPane scpElementList;

	/**
	 * Creates the panel for categories and element list.
	 */
	public ElementPanel() {
		setLayout(new BorderLayout());
		chCategoryList=new Choice();
		scpElementList=new ScrollPane();
		String[] categories=Categories.getCategories();
		for(int i=0;i<categories.length;i++) {
			chCategoryList.add(categories[i]);
		}
		chCategoryList.addItemListener(new CategoryListListener());
		scpElementList.setBackground(SystemColor.text);
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
		add(chCategoryList,BorderLayout.NORTH);
		add(scpElementList,BorderLayout.CENTER);
		
		setBackground(SystemColor.activeCaptionBorder);
		DragnDropListener dnd=new DragnDropListener();
		scpElementList.addMouseListener(dnd);
		scpElementList.addMouseMotionListener(dnd);
	}

	/**
	 * Updates the list of formula element if the category changes.
	 * @param form The list of formula-elements to display.
	 */
	public void updateElementList(Formula[] form) {
		scpElementList.removeAll();
		if(form == null) {
			return;		// don't update if list is empty
		} else {
			Panel panel=new Panel();
			panel.setLayout(new GridLayout(1,form.length));
			for(int i=0;i<form.length;i++) {
				panel.add(form[i]);
			}
			scpElementList.add(panel);
		}
	}
}
