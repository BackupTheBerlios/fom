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
	
	private GridLayout elementPanelLayout=new GridLayout();

	/**
	 * Creates the panel for categories and element list.
	 */
	public ElementPanel() {
		setLayout(new BorderLayout());
		chCategoryList=new Choice();
		scpElementList=new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		elementPanel=new Panel();
		
		String[] categories=Categories.getCategories();
		for(int i=0;i<categories.length;i++) {
			chCategoryList.add(categories[i]);
		}
		
		chCategoryList.addItemListener(new CategoryListListener());
		scpElementList.setBackground(SystemColor.text);
		elementPanelLayout.setHgap(8);
		elementPanelLayout.setVgap(8);
		elementPanel.setLayout(elementPanelLayout);
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
		
		scpElementList.add(elementPanel);
		add(chCategoryList,BorderLayout.NORTH);
		add(scpElementList,BorderLayout.CENTER);
		
		//scpElementList.setSize(getWidth()-20,200);
		//elementPanel.setSize(getWidth()-20,200);
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
		Panel newPanel=new Panel();
		if(form == null) {
			System.out.println("DEBUG: adding nothing!");
			newPanel.setLayout(new GridLayout(1,1));
			newPanel.add(new Label("testing.."));
		} else {
			newPanel.setLayout(new GridLayout(form.length,1));
			for(int i=0;i<form.length;i++) {
				System.out.println("DEBUG: adding "+form[i].getFormulaName());
				newPanel.add(form[i]);
			}
		}
		scpElementList.remove(elementPanel);
		scpElementList.add(newPanel);
		elementPanel=newPanel;
	}
}
