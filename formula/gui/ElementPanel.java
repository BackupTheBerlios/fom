/* $Id: ElementPanel.java,v 1.26 2004/09/06 13:08:01 br3001 Exp $
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;
import formula.*;
import utils.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.26 $
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
	
	private Categories categories;
	
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
		dnd						= aPanel.getDragnDropListener();
		btnClearFormulas	= new Button(Messages.getString("ElementPanel.BtnClearFormulas"));
		btnAddFormula		= new Button(Messages.getString("ElementPanel.BtnAddFormula"));
		epListener			= new ElementPanelListener(aPanel,this);
		elementPanelLayout	= new GridLayout();
		categories			= new Categories();
		GridBagLayout gbl	= new GridBagLayout();
		//adding categories:
		String[] strCategories=categories.getCategories();
		for(int i=0;i<strCategories.length;i++) {
			chCategoryList.add(strCategories[i]);
		}
		//adding listeners:
		chCategoryList.addItemListener(epListener);
		btnAddFormula.addActionListener(epListener);
		btnClearFormulas.addActionListener(epListener);
		elementPanel.addMouseListener(dnd);
		elementPanel.addMouseMotionListener(dnd);
		//visible settings:
		scpElementList.setBackground(Color.white);
		elementPanel.setBackground(Color.white);
		setBackground(SystemColor.activeCaptionBorder);
		//layouts:
		setLayout(gbl);
		elementPanel.setLayout(elementPanelLayout);
		//adding all together:
		scpElementList.add(elementPanel);
		FOMToolkit.addComponent(this,gbl,btnClearFormulas,0,0,1,1,1.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,btnAddFormula,0,1,1,1,1.0,0.0,GridBagConstraints.HORIZONTAL);
		FOMToolkit.addComponent(this,gbl,chCategoryList,0,2,1,1,0.5,0.0,GridBagConstraints.NONE);
		FOMToolkit.addComponent(this,gbl,scpElementList,0,3,1,1,1.0,1.0,GridBagConstraints.BOTH);

		updateElementList(categories.getCategoryElements(chCategoryList.getSelectedItem()));
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
		updateElementList(categories.getCategoryElements(chCategoryList.getSelectedItem()));
	}
	
	
	/**
	 * @return returns the categories
	 */
	public Categories getCategories() {
		return categories;
	}


	/**
	 * @return returns the category list of this ElementPanel
	 */
	public Choice getCategoryList() {
		return chCategoryList;
	}

}
