/*
 * Created on 02.06.2004
 *
 */
package formula;

import java.util.*;
import utils.Messages;
/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class Categories {

	private String[] categories;

	private HashMap categoryElements;

	public Categories() {
		categories=new String[Integer.parseInt(Messages.getString("Elements.Categories"))];
		for(int i=0;i<categories.length;i++);
	}

	public final String[] getCategories() {
		return new String[0];
	}

	public final Formula[] getCategoryElements() {
		return new Formula[0];
	}

}
