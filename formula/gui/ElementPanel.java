/*
 * Created on 26.04.2004
 *
 */
package gui;

import java.awt.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @since 26.04.2004
 *
 */
public class ElementPanel extends Panel {

	private Choice chCategoryList;
	private ScrollPane scpElementList;

	public ElementPanel() {
		setLayout(new BorderLayout());
		chCategoryList=new Choice();
		scpElementList=new ScrollPane();
		// TODO: get elements and categories from formula.Formula
		chCategoryList.add("Default");
		chCategoryList.add("Boolean");
		chCategoryList.addItemListener(new CategoryListListener());
		scpElementList.setBackground(SystemColor.text);
		add(chCategoryList,BorderLayout.NORTH);
		add(scpElementList,BorderLayout.CENTER);
		
		setBackground(SystemColor.activeCaptionBorder);
		DragnDropListener dnd=new DragnDropListener();
		scpElementList.addMouseListener(dnd);
		scpElementList.addMouseMotionListener(dnd);
		
	}

	public ScrollPane getScpElementList() {
		return null;
	}

}
