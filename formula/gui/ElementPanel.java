/*
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;
import formula.*;
import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class ElementPanel extends Panel {

	private Choice chCategoryList;
	private ScrollPane scpElementList;
	private Panel elementPanel;
	private Button btnAddFormula;
	private CategoryListListener clListener;
	
	private GridLayout elementPanelLayout;
	private DragnDropListener dnd;
	private AppletPanel parent;
	
	/**
	 * Creates the panel for categories and element list.
	 */
	public ElementPanel(AppletPanel parent) {
		this.parent = (AppletPanel)parent;
		//create objects for element panel:
		Panel topPanel			= new Panel(new GridLayout(2,1));
		chCategoryList		= new Choice();
		scpElementList		= new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		elementPanel			= new Panel();
		dnd							= new DragnDropListener(this.parent);
		btnAddFormula		= new Button(Messages.getString("ElementPanel.BtnAddFormula"));
		clListener					= new CategoryListListener(this);
		elementPanelLayout = new GridLayout();
		//adding categories:
		String[] categories=Categories.getCategories();
		for(int i=0;i<categories.length;i++) {
			chCategoryList.add(categories[i]);
		}
		//adding listeners:
		chCategoryList.addItemListener(clListener);
		btnAddFormula.addActionListener(clListener);
		//visible settings:
		scpElementList.setBackground(SystemColor.text);
		elementPanel.setBackground(SystemColor.text);
		setBackground(SystemColor.activeCaptionBorder);
		//layouts:
		setLayout(new BorderLayout());
		elementPanel.setLayout(elementPanelLayout);
		//adding all together:
		scpElementList.add(elementPanel);
		topPanel.add(btnAddFormula);
		topPanel.add(chCategoryList);
		add(topPanel,BorderLayout.NORTH);
		add(scpElementList,BorderLayout.CENTER);
		//hardcoded size, because the default-size sux :)		
		setSize(150,getSize().height);
		chCategoryList.setSize(150,chCategoryList.getSize().height);
		scpElementList.setSize(150,scpElementList.getSize().height);		
		Categories.initMouseListener(this.parent);
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
	}

	/**
	 * Updates the list of formula element if the category changes.
	 * @param form The list of formula-elements to display.
	 */
	public void updateElementList(Formula[] form) {
		elementPanel.removeAll();
		if(form == null) {					//no elements in category
			elementPanelLayout.setRows(1);
			elementPanelLayout.setColumns(1);
			elementPanel.add(new Label("ToDo: Kategorien :)"));
		} else {
			elementPanelLayout.setColumns(1);
			elementPanelLayout.setRows(form.length);
			for(int i=0;i<form.length;i++) {
				elementPanel.add(form[i]);
			}
		}
		scpElementList.validate();
	}
	
	
	/**
	 * Refreshs the formula elements of the active category.
	 */
	public void refreshElementList() {
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
		
	}
}
