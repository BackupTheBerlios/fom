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
	private Button btnClearFormulas;
	private Button btnAddFormula;
	private ElementPanelListener epListener;
	
	private GridLayout elementPanelLayout;
	private DragnDropListener dnd;
	private AppletPanel aPanel;
	
	/**
	 * Creates the panel for categories and element list.
	 */
	public ElementPanel(AppletPanel parent) {
		this.aPanel = (AppletPanel)parent;
		//create objects for element panel:
		Panel topPanel		= new Panel(new GridLayout(3,1));
		chCategoryList		= new Choice();
		scpElementList		= new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		elementPanel		= new Panel();
		dnd					= new DragnDropListener(this.aPanel);
		btnClearFormulas	= new Button(Messages.getString("ElementPanel.BtnClearFormulas"));
		btnAddFormula		= new Button(Messages.getString("ElementPanel.BtnAddFormula"));
		epListener			= new ElementPanelListener(aPanel,this);
		elementPanelLayout	= new GridLayout();
		//adding categories:
		String[] categories=Categories.getCategories();
		for(int i=0;i<categories.length;i++) {
			chCategoryList.add(categories[i]);
		}
		//adding listeners:
		chCategoryList.addItemListener(epListener);
		btnAddFormula.addActionListener(epListener);
		btnClearFormulas.addActionListener(epListener);
		elementPanel.addMouseListener(dnd);
		elementPanel.addMouseMotionListener(dnd);
		//visible settings:
		scpElementList.setBackground(SystemColor.text);
		elementPanel.setBackground(SystemColor.text);
		setBackground(SystemColor.activeCaptionBorder);
		//layouts:
		setLayout(new BorderLayout());
		elementPanel.setLayout(elementPanelLayout);
		//adding all together:
		scpElementList.add(elementPanel);
		topPanel.add(btnClearFormulas);
		topPanel.add(btnAddFormula);
		topPanel.add(chCategoryList);
		add(topPanel,BorderLayout.NORTH);
		add(scpElementList,BorderLayout.CENTER);
		//hardcoded size, because the default-size sux (it did, now it doesn't seem to matter) :)		
		int epWidth = Formula.FORMULAWIDHT+20;
		setSize(epWidth,getSize().height);
		chCategoryList.setSize(epWidth,chCategoryList.getSize().height);
		scpElementList.setSize(epWidth,scpElementList.getSize().height);
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
	}

	/**
	 * Updates the list of formula element if the category changes.
	 * @param form The list of formula-elements to display.
	 */
	public void updateElementList(Formula[] form) {
		elementPanel.removeAll();
		if(form == null) {						//no elements in category
			elementPanelLayout.setRows(1);
			elementPanelLayout.setColumns(1);
			elementPanel.add(new Label(""));	// don't remove this!
		} else {
			elementPanelLayout.setColumns(1);
			elementPanelLayout.setRows(form.length);
			for(int i=0;i<form.length;i++) {
				elementPanel.add(form[i]);
			}
		}
		scpElementList.validate();
		elementPanel.repaint();
	}
	
	
	/**
	 * Refreshs the formula elements of the active category.
	 */
	public void refreshElementList() {
		updateElementList(Categories.getCategoryElements(chCategoryList.getSelectedItem()));
	}
}
