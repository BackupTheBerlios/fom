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
	private Panel elementPanel;
	
	private GridLayout elementPanelLayout = new GridLayout();
	
	/**
	 * Creates the panel for categories and element list.
	 */
	public ElementPanel() {
		setLayout(new BorderLayout());
		
		chCategoryList	= new Choice();
		scpElementList	= new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		elementPanel	= new Panel();
		
		String[] categories=Categories.getCategories();
		for(int i=0;i<categories.length;i++) {
			chCategoryList.add(categories[i]);
		}
		
		chCategoryList.addItemListener(new CategoryListListener());
		scpElementList.setBackground(SystemColor.text);
		elementPanel.setLayout(elementPanelLayout);
		scpElementList.add(elementPanel);
		elementPanelLayout.setHgap(4);
		elementPanelLayout.setVgap(4);
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
		
		scpElementList.add(elementPanel);
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
		elementPanel.removeAll();
		if(form == null) {
			elementPanelLayout.setRows(1);
			elementPanel.add(new Label("ToDo: Kategorien :)"));
		} else {
			elementPanelLayout.setRows(form.length);
			for(int i=0;i<form.length;i++) {
				elementPanel.add(form[i]);
			}
		}
		scpElementList.validate();
	}
}
